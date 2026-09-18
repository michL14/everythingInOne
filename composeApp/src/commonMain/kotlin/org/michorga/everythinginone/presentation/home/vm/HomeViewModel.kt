package org.michorga.everythinginone.presentation.home.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import org.koin.core.component.KoinComponent
import org.michorga.everythinginone.domain.model.Transaction
import org.michorga.everythinginone.domain.usecase.salary.SalaryUseCases
import org.michorga.everythinginone.domain.usecase.subscription.SubscriptionUseCases
import org.michorga.everythinginone.domain.usecase.transaction.TransactionUseCases
import org.michorga.everythinginone.presentation.home.model.RecentTransactionUI
import org.michorga.everythinginone.presentation.home.model.UpcomingPaymentUI
import org.michorga.everythinginone.presentation.home.navigation.AppState
import org.michorga.everythinginone.presentation.home.navigation.Screen
import org.michorga.everythinginone.presentation.home.navigation.SubScreen
import org.michorga.everythinginone.presentation.subscription.vm.UpcomingPaymentViewModel
import kotlin.math.roundToLong
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class HomeViewModel(
    private val salaryUseCases: SalaryUseCases,
    private val subscriptionUseCases: SubscriptionUseCases,
    private val transactionUseCases: TransactionUseCases,
) : ViewModel(), KoinComponent {

    private val scope = CoroutineScope(Dispatchers.Default)

    /** Current Balance affiché sur l'écran d'accueil (somme des salaires ajoutés). */
    val currentBalance: StateFlow<Double> = salaryUseCases.getCurrentBalance()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 0.0
        )

    /**
     * Estimation "réelle" du Current Balance : on soustrait au solde actuel le total
     * des abonnements actifs encore non payés pour le mois en cours (ceux qui n'ont pas
     * encore été débités). Donne une vision de ce qu'il restera une fois tous les
     * abonnements du mois prélevés.
     */
    val realEstimation: StateFlow<Double> = combine(
        salaryUseCases.getCurrentBalance(),
        subscriptionUseCases.getSubscriptions(),
    ) { balance, subscriptions ->
        val ym = currentYearMonth()
        val remainingUnpaid = subscriptions
            .filter { it.isActive && it.lastAppliedYearMonth != ym }
            .sumOf { it.amount }
        balance - remainingUnpaid
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = 0.0
    )

    /**
     * Liste unifiée des transactions récentes : salaires ajoutés (entrées d'argent),
     * dépenses, abonnements et remboursements, ainsi que les débits d'abonnement
     * récurrents. Les entrées sont affichées en vert, les sorties en rouge.
     */
    val recentTransactions: StateFlow<List<RecentTransactionUI>> = combine(
        salaryUseCases.getSalaries(),
        transactionUseCases.getTransactions(),
        subscriptionUseCases.getSubscriptionCharges(),
    ) { salaries, payments, charges ->
        val items = mutableListOf<RecentTransactionUI>()

        salaries.forEach { salary ->
            items += RecentTransactionUI(
                id = "salary-${salary.id}",
                label = "Update current balance",
                amount = salary.amount.toAmountString(),
                isIncome = true,
                month = salary.yearMonth.toMonthNumber(),
            )
        }

        payments.forEach { payment ->
            items += when (payment) {
                is Transaction.Refund -> RecentTransactionUI(
                    id = "payment-${payment.id}",
                    label = payment.description ?: "Refund",
                    amount = payment.amount.toAmountString(),
                    isIncome = true,
                    month = payment.month.toInt(),
                )
                is Transaction.Expense -> RecentTransactionUI(
                    id = "payment-${payment.id}",
                    label = payment.description ?: "Expense",
                    amount = payment.amount.toAmountString(),
                    isIncome = false,
                    month = payment.month.toInt(),
                )
                is Transaction.Subscription -> RecentTransactionUI(
                    id = "payment-${payment.id}",
                    label = payment.description ?: "Subscription",
                    amount = payment.amount.toAmountString(),
                    isIncome = false,
                    month = payment.month.toInt(),
                )
            }
        }

        charges.forEach { charge ->
            items += RecentTransactionUI(
                id = "charge-${charge.id}",
                label = charge.subscriptionName ?: "Subscription",
                amount = charge.amount.toAmountString(),
                isIncome = false,
                month = charge.yearMonth.toMonthNumber(),
            )
        }

        // Les plus récentes (mois le plus élevé) en premier.
        items.sortedByDescending { it.month }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    /**
     * Les 3 prochains abonnements à payer : abonnements actifs qui n'ont pas encore
     * été débités pour le mois en cours. Utilisé par le carrousel horizontal de l'accueil.
     */
    val upcomingPayments: StateFlow<List<UpcomingPaymentUI>> =
        subscriptionUseCases.getSubscriptions()
            .map { subscriptions ->
                val ym = currentYearMonth()
                val (year, month, day) = currentYearMonthDayTriple()
                subscriptions
                    .filter { it.isActive && it.lastAppliedYearMonth != ym }
                    .take(3)
                    .map { sub ->
                        UpcomingPaymentUI(
                            id = sub.id,
                            name = sub.name,
                            amount = sub.amount.toAmountString(),
                            nextPaymentDate = UpcomingPaymentViewModel.computeNextPaymentDate(
                                dayOfMonth = sub.dayOfMonth,
                                alreadyAppliedThisMonth = false,
                                todayYearMonth = "$year-${month.toString().padStart(2, '0')}",
                                todayDay = day
                            )
                        )
                    }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )
    private val _isAddSalaryDialogVisible = MutableStateFlow(false)
    val isAddSalaryDialogVisible: StateFlow<Boolean> = _isAddSalaryDialogVisible

    init {
        // Applique automatiquement le salaire récurrent pour le mois en cours.
        viewModelScope.launch {
            salaryUseCases.applyRecurringSalary(currentYearMonth())
        }
        // Débite les abonnements actifs dus pour le mois en cours (idempotent).
        viewModelScope.launch {
            subscriptionUseCases.applyDueSubscriptions(currentYearMonth(), currentDay())
        }
    }

    fun showAddSalaryDialog() {
        _isAddSalaryDialogVisible.value = true
    }

    fun hideAddSalaryDialog() {
        _isAddSalaryDialogVisible.value = false
    }

    /**
     * Ajoute le salaire du mois au Current Balance.
     * @param amount montant saisi par l'utilisateur.
     * @param isRecurring si vrai, ce montant sera ajouté automatiquement chaque mois.
     */
    fun addSalary(amount: Double, isRecurring: Boolean) {
        viewModelScope.launch {
            salaryUseCases.addSalary(
                amount = amount,
                yearMonth = currentYearMonth(),
                isRecurring = isRecurring
            )
            hideAddSalaryDialog()
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun currentYearMonth(): String {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val month = now.month.number.toString().padStart(2, '0')
        return "${now.year}-$month"
    }

    @OptIn(ExperimentalTime::class)
    private fun currentDay(): Int {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        return now.day
    }

    @OptIn(ExperimentalTime::class)
    private fun currentYearMonthDayTriple(): Triple<Int, Int, Int> {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        return Triple(now.year, now.month.number, now.day)
    }

    /** Navigue vers l’écran "Add Payment" dans la section Payment */
    fun navigateToAddPayment() {
        AppState.currentScreen = Screen.PAYMENT
        AppState.currentSubScreen = SubScreen.ADD_PAYMENT
    }

    /** Navigue vers la section Payment (Recent Transactions par défaut) */
    fun navigateToPayment() {
        AppState.currentScreen = Screen.PAYMENT
        AppState.currentSubScreen = SubScreen.PAYMENT
    }

    /** Navigue vers Home */
    fun navigateHome() {
        AppState.currentScreen = Screen.HOME
        AppState.currentSubScreen = null
    }
}

/** Formate un montant en chaîne avec deux décimales (ex : 1250.5 -> "1250.50"). */
private fun Double.toAmountString(): String {
    val cents = (this * 100).roundToLong()
    val intPart = cents / 100
    val decPart = (cents % 100).toString().padStart(2, '0')
    return "$intPart.$decPart"
}

/** Extrait le numéro de mois (1..12) d'un identifiant de mois "YYYY-MM". */
private fun String.toMonthNumber(): Int =
    substringAfter('-', "").take(2).toIntOrNull() ?: 0

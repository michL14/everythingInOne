package org.michorga.everythinginone.presentation.subscription.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import org.michorga.everythinginone.domain.model.Subscription
import org.michorga.everythinginone.domain.usecase.subscription.SubscriptionUseCases
import org.michorga.everythinginone.presentation.subscription.model.SubscriptionFilter
import org.michorga.everythinginone.presentation.subscription.model.SubscriptionUI
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class UpcomingPaymentViewModel(
    private val subscriptionUseCases: SubscriptionUseCases,
) : ViewModel() {

    /** Filtre sélectionné (All / To pay / Payed). */
    private val _filter = MutableStateFlow(SubscriptionFilter.ALL)
    val filter: StateFlow<SubscriptionFilter> = _filter

    val subscriptions: StateFlow<List<SubscriptionUI>> = combine(
        subscriptionUseCases.getSubscriptions(),
        _filter
    ) { list, filter ->
        val ui = list.map { it.toUI() }
        when (filter) {
            SubscriptionFilter.ALL -> ui
            // Abonnements du mois en cours restant à payer (actifs et non encore débités).
            SubscriptionFilter.TO_PAY -> ui.filter { it.isActive && !it.isPaidThisMonth }
            // Abonnements du mois en cours déjà payés (débités).
            SubscriptionFilter.PAYED -> ui.filter { it.isPaidThisMonth }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    /** Change le filtre affiché. */
    fun onFilterChange(filter: SubscriptionFilter) {
        _filter.value = filter
    }

    init {
        // Débite immédiatement les abonnements dus lorsqu'on ouvre l'écran.
        applyDueSubscriptions()
    }

    private fun applyDueSubscriptions() {
        viewModelScope.launch {
            val (ym, day) = currentYearMonthAndDay()
            subscriptionUseCases.applyDueSubscriptions(ym, day)
        }
    }

    /** Ajoute un abonnement. [dayOfMonth] vaut 1 par défaut si l'utilisateur ne saisit rien. */
    fun addSubscription(name: String, amount: Double, dayOfMonth: Int) {
        viewModelScope.launch {
            subscriptionUseCases.addSubscription(name.trim(), amount, dayOfMonth.coerceIn(1, 31))
            applyDueSubscriptions()
        }
    }

    /** Modifie un abonnement existant (nom, montant et date de débit). */
    fun updateSubscription(id: Long, name: String, amount: Double, dayOfMonth: Int) {
        viewModelScope.launch {
            subscriptionUseCases.updateSubscription(id, name.trim(), amount, dayOfMonth.coerceIn(1, 31))
        }
    }

    /** Active / désactive un abonnement. */
    fun setActive(id: Long, isActive: Boolean) {
        viewModelScope.launch {
            subscriptionUseCases.setActive(id, isActive)
            if (isActive) applyDueSubscriptions()
        }
    }

    /** Supprime un abonnement. */
    fun deleteSubscription(id: Long) {
        viewModelScope.launch {
            subscriptionUseCases.deleteSubscription(id)
        }
    }

    /**
     * Déclare manuellement un abonnement comme payé pour le mois en cours.
     * Effectue la même action que lorsque la date de débit est atteinte.
     */
    fun markAsPaid(id: Long) {
        viewModelScope.launch {
            val (ym, _) = currentYearMonthAndDay()
            subscriptionUseCases.markAsPaid(id, ym)
        }
    }

    /**
     * Annule le paiement d'un abonnement pour le mois en cours (inverse de [markAsPaid]).
     */
    fun markAsUnpaid(id: Long) {
        viewModelScope.launch {
            val (ym, _) = currentYearMonthAndDay()
            subscriptionUseCases.markAsUnpaid(id, ym)
        }
    }

    private fun Subscription.toUI(): SubscriptionUI {
        val (ym, today) = currentYearMonthAndDay()
        val paidThisMonth = lastAppliedYearMonth == ym
        return SubscriptionUI(
            id = id,
            name = name,
            amount = amount,
            dayOfMonth = dayOfMonth,
            isActive = isActive,
            isPaidThisMonth = paidThisMonth,
            nextPaymentDate = computeNextPaymentDate(
                dayOfMonth = dayOfMonth,
                alreadyAppliedThisMonth = paidThisMonth,
                todayYearMonth = ym,
                todayDay = today
            )
        )
    }

    @OptIn(ExperimentalTime::class)
    private fun currentYearMonthAndDay(): Pair<String, Int> {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val month = now.month.number.toString().padStart(2, '0')
        return "${now.year}-$month" to now.day
    }

    companion object {
        /**
         * Calcule la prochaine date de débit au format "dd/MM/yyyy".
         * Si le débit du mois courant est déjà passé ou déjà appliqué, on passe au mois suivant.
         */
        fun computeNextPaymentDate(
            dayOfMonth: Int,
            alreadyAppliedThisMonth: Boolean,
            todayYearMonth: String,
            todayDay: Int,
        ): String {
            val year = todayYearMonth.substringBefore('-').toInt()
            val month = todayYearMonth.substringAfter('-').toInt()

            val dueThisMonth = !alreadyAppliedThisMonth && dayOfMonth >= todayDay
            var targetYear = year
            var targetMonth = month
            if (!dueThisMonth) {
                targetMonth += 1
                if (targetMonth > 12) {
                    targetMonth = 1
                    targetYear += 1
                }
            }
            val day = dayOfMonth.coerceAtMost(daysInMonth(targetYear, targetMonth))
            val dd = day.toString().padStart(2, '0')
            val mm = targetMonth.toString().padStart(2, '0')
            return "$dd/$mm/$targetYear"
        }

        private fun daysInMonth(year: Int, month: Int): Int = when (month) {
            1, 3, 5, 7, 8, 10, 12 -> 31
            4, 6, 9, 11 -> 30
            2 -> if (isLeapYear(year)) 29 else 28
            else -> 30
        }

        private fun isLeapYear(year: Int): Boolean =
            (year % 4 == 0 && year % 100 != 0) || year % 400 == 0
    }
}
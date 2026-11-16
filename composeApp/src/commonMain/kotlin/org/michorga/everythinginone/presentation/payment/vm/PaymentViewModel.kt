package org.michorga.everythinginone.presentation.payment.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import org.koin.core.component.KoinComponent
import org.michorga.everythinginone.domain.model.Salary
import org.michorga.everythinginone.domain.usecase.category.GetAllCategoriesUseCase
import org.michorga.everythinginone.domain.usecase.salary.SalaryUseCases
import org.michorga.everythinginone.domain.usecase.transaction.TransactionUseCases
import org.michorga.everythinginone.presentation.payment.mapper.toDomain
import org.michorga.everythinginone.presentation.payment.mapper.toUIList
import org.michorga.everythinginone.presentation.payment.mapper.toUIModel
import org.michorga.everythinginone.presentation.payment.model.AmountError
import org.michorga.everythinginone.presentation.payment.model.CategoryModelUI
import org.michorga.everythinginone.presentation.payment.model.CategoryUI
import org.michorga.everythinginone.presentation.payment.model.Month
import org.michorga.everythinginone.presentation.payment.model.TransactionModel
import org.michorga.everythinginone.presentation.payment.model.TransactionUI
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

data class PaymentUiState(
    val transaction: TransactionModel = TransactionModel(
        amount = "",
        isPaid = true,
        categoryName = 0L,
        month = 1
    ),
    val amountError: AmountError? = null,
    val isBottomSheetVisible: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false
)

class PaymentViewModel(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val transactionUseCases: TransactionUseCases,
    private val salaryUseCases: SalaryUseCases,
) : ViewModel(), KoinComponent {

    private val _uiState = MutableStateFlow(PaymentUiState())
    val uiState: StateFlow<PaymentUiState> = _uiState

    private val _categories = MutableStateFlow<List<CategoryModelUI>>(emptyList())
    val categories: StateFlow<List<CategoryModelUI>> = _categories

    private val _listOfTransactions = MutableStateFlow<List<TransactionUI>>(emptyList())
    val listOfTransactions: StateFlow<List<TransactionUI>> = _listOfTransactions

    var selectedCategoryId by mutableStateOf<Long>(2)
        private set

    @OptIn(ExperimentalTime::class)
    var selectedMonth by mutableStateOf(
        Month.fromNumber(
            Clock.System
                .now()
                .toLocalDateTime(TimeZone.currentSystemDefault())
                .month.number
        )
    )
        private set


    init {
        getCategoryList()
        getAllTransactions()
    }

    fun getCategoryList() {
        viewModelScope.launch {
            getAllCategoriesUseCase.invoke().collect { list ->
                _categories.value = list.toUIList()
                if (list.isNotEmpty()) {
                    val firstCategoryId = list.first().id
                    _uiState.update {
                        it.copy(transaction = it.transaction.copy(categoryName = firstCategoryId))
                    }
                    selectedCategoryId = firstCategoryId
                }
            }
        }
    }

    /**
     * Combine les transactions et les salaires ajoutés ("Update current balance")
     * pour afficher les deux dans la liste des transactions récentes. Les salaires
     * sont présentés comme des entrées d'argent (catégorie [CategoryUI.SALARY]).
     */
    fun getAllTransactions() {
        viewModelScope.launch {
            combine(
                transactionUseCases.getTransactions.invoke(),
                salaryUseCases.getSalaries.invoke(),
            ) { transactions, salaries ->
                val items = transactions.map { it.toUIModel() }.toMutableList()
                salaries.forEach { salary ->
                    items += salary.toTransactionUI()
                }
                items.sortedByDescending { it.month }
            }.collect { list ->
                _listOfTransactions.value = list
            }
        }
    }

    /** Convertit un salaire ajouté en [TransactionUI] affichable dans la liste. */
    private fun Salary.toTransactionUI(): TransactionUI = TransactionUI(
        // Identifiant négatif dédié pour éviter toute collision avec les transactions
        // et empêcher une suppression accidentelle via le swipe.
        id = -(this.id + 1),
        amount = this.amount.toAmountString(),
        description = "Update current balance",
        categoryName = CategoryUI.SALARY.value,
        isPaid = true,
        month = this.yearMonth.toMonthNumber(),
    )

    /**
     * Supprime une transaction ou un salaire. Les salaires ("Update current balance")
     * sont encodés avec un id négatif ([toTransactionUI]) : on restaure alors l'id
     * réel du salaire pour le supprimer. La liste se met à jour automatiquement car
     * [getAllTransactions] collecte des Flow depuis les repositories.
     */
    fun deleteTransaction(id: Long) {
        viewModelScope.launch {
            if (id < 0) {
                val salaryId = -id - 1
                salaryUseCases.deleteSalary.execute(salaryId)
            } else {
                transactionUseCases.deleteTransaction.execute(id)
            }
        }
    }

    fun onCategorySelectedChanged(categoryId: Long) {
        _uiState.update {
            it.copy(transaction = it.transaction.copy(categoryName = categoryId))
        }
        selectedCategoryId = categoryId
    }

    fun onAmountChange(amount: String) {
        val error = validateAmount(amount)
        _uiState.update {
            it.copy(
                transaction = it.transaction.copy(amount = amount),
                amountError = error
            )
        }
    }

    fun onDescriptionChange(description: String) {
        _uiState.update {
            it.copy(transaction = it.transaction.copy(description = description))
        }
    }

    private fun validateAmount(amount: String): AmountError? {
        val value = amount.replace(',', '.').toDoubleOrNull()
        return when {
            amount.isBlank() -> AmountError.Mandatory
            value == null -> AmountError.Invalid
            value <= 0.0 -> AmountError.Negative
            else -> null
        }
    }


    fun showBottomSheet() {
        _uiState.update { it.copy(isBottomSheetVisible = true) }
    }

    fun hideBottomSheet() {
        _uiState.update { it.copy(isBottomSheetVisible = false) }
    }


    fun confirmTransaction() {
        val transactionModel = _uiState.value.transaction
        val amountError = validateAmount(transactionModel.amount)
        if (amountError != null) {
            _uiState.update { it.copy(amountError = amountError) }
            return
        }

        val transactionDomain = transactionModel.toDomain()
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            transactionUseCases.addTransaction.execute(transactionDomain)
            // Réinitialise les champs de saisie afin de pouvoir enregistrer
            // une nouvelle transaction juste après.
            _uiState.update {
                it.copy(
                    isLoading = false,
                    isSuccess = true,
                    transaction = it.transaction.copy(amount = "", description = null),
                    amountError = null
                )
            }
        }
    }

    fun resetSuccess() {
        _uiState.update { it.copy(isSuccess = false) }
    }
}

/** Formate un montant en chaîne avec deux décimales (ex : 1250.5 -> "1250.50"). */
private fun Double.toAmountString(): String {
    val cents = kotlin.math.round(this * 100).toLong()
    val intPart = cents / 100
    val decPart = (cents % 100).toString().padStart(2, '0')
    return "$intPart.$decPart"
}

/** Extrait le numéro de mois (1..12) d'un identifiant de mois "YYYY-MM". */
private fun String.toMonthNumber(): Int =
    substringAfter('-', "").take(2).toIntOrNull() ?: 0


package org.michorga.everythinginone.presentation.payment.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.michorga.everythinginone.domain.usecase.category.GetAllCategoriesUseCase
import org.michorga.everythinginone.domain.usecase.transaction.TransactionUseCases
import org.michorga.everythinginone.presentation.payment.mapper.toDomain
import org.michorga.everythinginone.presentation.payment.mapper.toUIList
import org.michorga.everythinginone.presentation.payment.mapper.toUIModel
import org.michorga.everythinginone.presentation.payment.model.AmountError
import org.michorga.everythinginone.presentation.payment.model.CategoryModelUI
import org.michorga.everythinginone.presentation.payment.model.TransactionModel
import org.michorga.everythinginone.presentation.payment.model.TransactionUI

data class PaymentUiState(
    val transaction: TransactionModel = TransactionModel(
        amount = "",
        isPaid = true,
        categoryName = 0L,
    ),
    val amountError: AmountError? = null,
    val isBottomSheetVisible: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false
)

class PaymentViewModel(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val transactionUseCases: TransactionUseCases,
) : ViewModel(), KoinComponent {

    private val _uiState = MutableStateFlow(PaymentUiState())
    val uiState: StateFlow<PaymentUiState> = _uiState

    private val _categories = MutableStateFlow<List<CategoryModelUI>>(emptyList())
    val categories: StateFlow<List<CategoryModelUI>> = _categories

    private val _listOfTransactions = MutableStateFlow<List<TransactionUI>>(emptyList())
    val listOfTransactions: StateFlow<List<TransactionUI>> = _listOfTransactions

    var selectedCategoryId by mutableStateOf<Long>(2)
        private set


    init {
        getCategoryList()
        getAllTransactions()
    }

    fun getCategoryList(){
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

    fun getAllTransactions(){
        viewModelScope.launch {
            transactionUseCases.getTransactions.invoke().collect { list ->
                _listOfTransactions.value = list.map { it.toUIModel() }
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
            _uiState.update {
                it.copy(
                    isLoading = false,
                    isSuccess = true,
                )
            }
        }
    }

    fun resetSuccess() {
        _uiState.update { it.copy(isSuccess = false) }
    }
}


package org.michorga.everythinginone.domain.usecase.transaction

class TransactionUseCases(
    val getTransactions: GetTransactionsUseCase,
    val addTransaction: AddTransactionUseCase,
    val deleteTransaction: DeleteTransactionUseCase,
)
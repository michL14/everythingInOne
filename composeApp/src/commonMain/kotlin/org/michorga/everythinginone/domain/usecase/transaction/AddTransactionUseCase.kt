package org.michorga.everythinginone.domain.usecase.transaction

import org.michorga.everythinginone.domain.model.Transaction
import org.michorga.everythinginone.domain.repository.PaymentRepository

class AddTransactionUseCase(private val repository: PaymentRepository) {
    suspend fun execute(transaction: Transaction) {
        repository.addPayment(transaction = transaction)
    }
}
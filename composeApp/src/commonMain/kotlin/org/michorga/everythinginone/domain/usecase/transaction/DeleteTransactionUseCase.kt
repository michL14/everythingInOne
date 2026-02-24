package org.michorga.everythinginone.domain.usecase.transaction

import org.michorga.everythinginone.domain.repository.PaymentRepository

class DeleteTransactionUseCase(
    private val repository: PaymentRepository
) {
    suspend fun execute(id: Long) {
        repository.deletePayment(id)
    }
}
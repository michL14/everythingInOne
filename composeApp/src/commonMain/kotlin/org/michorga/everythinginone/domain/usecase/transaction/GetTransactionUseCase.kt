package org.michorga.everythinginone.domain.usecase.transaction

import org.michorga.everythinginone.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.Flow
import org.michorga.everythinginone.domain.model.Transaction

class GetTransactionsUseCase(private val repository: PaymentRepository) {
    operator fun invoke(): Flow<List<Transaction>> = repository.getAllPayments()
}
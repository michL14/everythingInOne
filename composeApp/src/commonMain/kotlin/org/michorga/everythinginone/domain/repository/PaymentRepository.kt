package org.michorga.everythinginone.domain.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.michorga.everythinginone.data.local.database.AppQueries
import org.michorga.everythinginone.data.mapper.toDomain
import org.michorga.everythinginone.domain.model.CategoryEnum
import org.michorga.everythinginone.domain.model.Transaction

interface PaymentRepository {
    fun getAllPayments(): Flow<List<Transaction>>
    suspend fun addPayment(transaction: Transaction)
    suspend fun deletePayment(id: Long)
}

class PaymentRepositoryImpl(
    private val queries: AppQueries
) : PaymentRepository {

    override fun getAllPayments(): Flow<List<Transaction>> {
        return queries.selectAllPayments()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { list ->
                list.map { it.toDomain() }
            }
    }

    override suspend fun addPayment(transaction: Transaction) {
        withContext(Dispatchers.IO) {
            when (transaction) {
                is Transaction.Subscription -> queries.insertPayment(
                    amount = transaction.amount,
                    categoryId = CategoryEnum.SUBSCRIPTION.id,
                    isPaid = if (transaction.isPaid) 1L else 0L,
                    description = transaction.description
                )

                is Transaction.Expense -> queries.insertPayment(
                    amount = transaction.amount,
                    categoryId = CategoryEnum.EXPENSE.id,
                    isPaid = 1L,
                    description = transaction.description
                )

                is Transaction.Refund -> queries.insertPayment(
                    amount = transaction.amount,
                    categoryId = CategoryEnum.REFUND.id,
                    isPaid = 1L,
                    description = transaction.description
                )
            }
        }
    }

    override suspend fun deletePayment(id: Long) {
        withContext(Dispatchers.IO) {
            queries.deletePayment(id)
        }
    }
}
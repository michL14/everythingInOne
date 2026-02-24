package org.michorga.everythinginone.data.mapper

import app.cash.sqldelight.Transacter
import org.michorga.everythinginone.domain.model.Transaction
import org.michorga.everythinginone.data.local.database.SelectAllPayments
import org.michorga.everythinginone.domain.model.CategoryEnum
import org.michorga.everythinginone.presentation.payment.model.TransactionModel

fun SelectAllPayments.toDomain(): Transaction {
    val isPaidConverted = this.isPaid == 1L
    return when (categoryName) {
        "Subscription" -> Transaction.Subscription(
            id = id,
            amount = amount,
            isPaid = isPaidConverted
        )
        "Refund" -> Transaction.Refund(
            id = id,
            amount = amount
        )
        "Expense" -> Transaction.Expense(
            id = id,
            amount = amount
        )
        else -> Transaction.Expense(
            id = id,
            amount = amount
        )
    }
}
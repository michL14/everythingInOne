package org.michorga.everythinginone.data.mapper

import org.michorga.everythinginone.data.local.database.SelectAllPayments
import org.michorga.everythinginone.domain.model.Transaction

fun SelectAllPayments.toDomain(): Transaction {
    val isPaidConverted = this.isPaid == 1L
    return when (categoryName) {
        "Subscription" -> Transaction.Subscription(
            id = id,
            amount = amount,
            isPaid = isPaidConverted,
            description = description,
            month = month
        )
        "Refund" -> Transaction.Refund(
            id = id,
            amount = amount,
            description = description,
            month = month
        )
        "Expense" -> Transaction.Expense(
            id = id,
            amount = amount,
            description = description,
            month = month
        )
        else -> Transaction.Expense(
            id = id,
            amount = amount,
            description = description,
            month = month
        )
    }
}
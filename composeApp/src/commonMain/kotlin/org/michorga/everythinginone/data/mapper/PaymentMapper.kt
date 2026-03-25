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
        )
        "Refund" -> Transaction.Refund(
            id = id,
            amount = amount,
            description = description,
        )
        "Expense" -> Transaction.Expense(
            id = id,
            amount = amount,
            description = description
        )
        else -> Transaction.Expense(
            id = id,
            amount = amount,
            description = description,
        )
    }
}
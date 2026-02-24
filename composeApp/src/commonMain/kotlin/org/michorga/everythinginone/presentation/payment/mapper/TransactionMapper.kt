package org.michorga.everythinginone.presentation.payment.mapper

import org.michorga.everythinginone.data.mapper.toCategoryEnum
import org.michorga.everythinginone.domain.model.CategoryEnum
import org.michorga.everythinginone.domain.model.Transaction
import org.michorga.everythinginone.presentation.payment.model.TransactionModel

fun TransactionModel.toDomain(): Transaction {
    return when (this.categoryName.toCategoryEnum()) {
        CategoryEnum.SUBSCRIPTION -> Transaction.Subscription(
            id = 0L,
            amount = this.amount.replace(',', '.').toDouble(),
            isPaid = this.isPaid
        )
        CategoryEnum.EXPENSE -> Transaction.Expense(
            id = 0L,
            amount = this.amount.replace(',', '.').toDouble()
        )
        CategoryEnum.REFUND -> Transaction.Refund(
            id = 0L,
            amount = this.amount.replace(',', '.').toDouble()
        )
    }
}
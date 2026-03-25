package org.michorga.everythinginone.presentation.payment.mapper


import org.michorga.everythinginone.data.mapper.toCategoryEnum
import org.michorga.everythinginone.domain.model.CategoryEnum
import org.michorga.everythinginone.domain.model.Transaction
import org.michorga.everythinginone.domain.model.Transaction.*
import org.michorga.everythinginone.presentation.payment.model.CategoryUI
import org.michorga.everythinginone.presentation.payment.model.TransactionModel
import org.michorga.everythinginone.presentation.payment.model.TransactionUI
import org.michorga.everythinginone.theme.AppTheme

fun TransactionModel.toDomain(): Transaction {
    return when (this.categoryName.toCategoryEnum()) {
        CategoryEnum.SUBSCRIPTION -> Subscription(
            id = 0L,
            amount = this.amount.replace(',', '.').toDouble(),
            isPaid = this.isPaid,
            description = this.description
        )
        CategoryEnum.EXPENSE -> Expense(
            id = 0L,
            amount = this.amount.replace(',', '.').toDouble(),
            description = this.description
        )
        CategoryEnum.REFUND -> Refund(
            id = 0L,
            amount = this.amount.replace(',', '.').toDouble(),
            description = this.description
        )

        CategoryEnum.ALL -> throw IllegalArgumentException("Category 'ALL' cannot be used for transactions")
    }
}

fun Transaction.toUIModel(): TransactionUI {
    return when (this) {
        is Subscription -> TransactionUI(
            id = this.id,
            amount = this.amount.toString(),
            description = this.description,
            categoryName = CategoryUI.SUBSCRIPTION.value,
            isPaid = this.isPaid,
        )
        is Expense -> TransactionUI(
            id = this.id,
            amount = this.amount.toString(),
            description = this.description,
            categoryName = CategoryUI.EXPENSE.value,
            isPaid = true
        )
        is Refund -> TransactionUI(
            id = this.id,
            amount = this.amount.toString(),
            description = this.description,
            categoryName = CategoryUI.REFUND.value,
            isPaid = true
        )
    }
}
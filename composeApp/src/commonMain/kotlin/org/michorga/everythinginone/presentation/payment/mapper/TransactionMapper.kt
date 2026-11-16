package org.michorga.everythinginone.presentation.payment.mapper


import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import org.michorga.everythinginone.data.mapper.toCategoryEnum
import org.michorga.everythinginone.domain.model.CategoryEnum
import org.michorga.everythinginone.domain.model.Transaction
import org.michorga.everythinginone.domain.model.Transaction.Expense
import org.michorga.everythinginone.domain.model.Transaction.Refund
import org.michorga.everythinginone.domain.model.Transaction.Subscription
import org.michorga.everythinginone.presentation.payment.model.CategoryUI
import org.michorga.everythinginone.presentation.payment.model.TransactionModel
import org.michorga.everythinginone.presentation.payment.model.TransactionUI
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun TransactionModel.toDomain(): Transaction {
    val currentMonthNumber = Clock.System
        .now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .month.number
    return when (this.categoryName.toCategoryEnum()) {
        CategoryEnum.SUBSCRIPTION -> Subscription(
            id = 0L,
            amount = this.amount.replace(',', '.').toDouble(),
            isPaid = this.isPaid,
            description = this.description,
            month = currentMonthNumber.toLong(),
        )
        CategoryEnum.EXPENSE -> Expense(
            id = 0L,
            amount = this.amount.replace(',', '.').toDouble(),
            description = this.description,
            month = currentMonthNumber.toLong(),
        )
        CategoryEnum.REFUND -> Refund(
            id = 0L,
            amount = this.amount.replace(',', '.').toDouble(),
            description = this.description,
            month = currentMonthNumber.toLong(),
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
            month = this.month.toInt()
        )
        is Expense -> TransactionUI(
            id = this.id,
            amount = this.amount.toString(),
            description = this.description,
            categoryName = CategoryUI.EXPENSE.value,
            isPaid = true,
            month = this.month.toInt()
        )
        is Refund -> TransactionUI(
            id = this.id,
            amount = this.amount.toString(),
            description = this.description,
            categoryName = CategoryUI.REFUND.value,
            isPaid = true,
            month = this.month.toInt()
        )
    }
}
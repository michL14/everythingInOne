package org.michorga.everythinginone.presentation.payment.model

import androidx.compose.ui.graphics.Color

data class TransactionUI(
    val id: Long,
    val amount: String,
    val description: String? = null,
    val categoryName: String,
    val isPaid: Boolean
)

enum class CategoryUI(val value: String) {
    ALL("All"),
    SUBSCRIPTION("Subscription"),
    EXPENSE("Expense"),
    REFUND("Refund")
}
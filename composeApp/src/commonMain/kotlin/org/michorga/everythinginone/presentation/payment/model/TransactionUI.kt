package org.michorga.everythinginone.presentation.payment.model

data class TransactionUI(
    val id: Long,
    val amount: String,
    val description: String? = null,
    val categoryName: String,
    val isPaid: Boolean,
    val month: Int,
)

enum class CategoryUI(val value: String) {
    ALL("All"),
    SUBSCRIPTION("Subscription"),
    EXPENSE("Expense"),
    REFUND("Refund")
}
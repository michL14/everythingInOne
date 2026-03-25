package org.michorga.everythinginone.domain.model

sealed class Transaction {

    data class Subscription(
        val id: Long,
        val amount: Double,
        val isPaid: Boolean,
        val description: String? = null
    ) : Transaction()

    data class Expense(
        val id: Long,
        val amount: Double,
        val description: String? = null
    ) : Transaction()

    data class Refund(
        val id: Long,
        val amount: Double,
        val description: String? = null,
    ) : Transaction()
}

enum class CategoryEnum(val id: Long) {
    ALL(1),
    SUBSCRIPTION(2),
    EXPENSE(3),
    REFUND(4)
}
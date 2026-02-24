package org.michorga.everythinginone.domain.model

sealed class Transaction {

    data class Subscription(
        val id: Long,
        val amount: Double,
        val isPaid: Boolean
    ) : Transaction()

    data class Expense(
        val id: Long,
        val amount: Double
    ) : Transaction()

    data class Refund(
        val id: Long,
        val amount: Double
    ) : Transaction()
}

enum class CategoryEnum(val id: Long) {
    SUBSCRIPTION(1),
    EXPENSE(2),
    REFUND(3)
}
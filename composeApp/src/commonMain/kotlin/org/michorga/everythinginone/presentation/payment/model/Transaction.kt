package org.michorga.everythinginone.presentation.payment.model


data class TransactionModel(
    val amount: String = "",
    val isPaid: Boolean,
    val categoryName: Long
)

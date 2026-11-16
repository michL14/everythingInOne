package org.michorga.everythinginone.domain.model

data class Salary(
    val id: Long = 0,
    val amount: Double,
    val yearMonth: String,
    val isRecurring: Boolean
)


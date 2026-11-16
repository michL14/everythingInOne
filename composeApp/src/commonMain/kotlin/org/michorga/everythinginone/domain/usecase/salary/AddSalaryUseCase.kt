package org.michorga.everythinginone.domain.usecase.salary

import org.michorga.everythinginone.domain.repository.SalaryRepository

class AddSalaryUseCase(private val repository: SalaryRepository) {
    suspend operator fun invoke(amount: Double, yearMonth: String, isRecurring: Boolean) {
        repository.addSalary(amount = amount, yearMonth = yearMonth, isRecurring = isRecurring)
    }
}


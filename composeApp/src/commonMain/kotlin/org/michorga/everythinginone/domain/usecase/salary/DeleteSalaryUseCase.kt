package org.michorga.everythinginone.domain.usecase.salary

import org.michorga.everythinginone.domain.repository.SalaryRepository

/** Supprime un salaire ajouté (entrée "Update current balance"). */
class DeleteSalaryUseCase(private val repository: SalaryRepository) {
    suspend fun execute(id: Long) {
        repository.deleteSalary(id)
    }
}


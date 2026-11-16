package org.michorga.everythinginone.domain.usecase.salary

import kotlinx.coroutines.flow.Flow
import org.michorga.everythinginone.domain.model.Salary
import org.michorga.everythinginone.domain.repository.SalaryRepository

/** Liste réactive de tous les salaires ajoutés (entrées d'argent). */
class GetSalariesUseCase(private val repository: SalaryRepository) {
    operator fun invoke(): Flow<List<Salary>> = repository.getAllSalaries()
}


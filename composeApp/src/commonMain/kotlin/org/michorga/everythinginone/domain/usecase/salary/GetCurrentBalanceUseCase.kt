package org.michorga.everythinginone.domain.usecase.salary

import kotlinx.coroutines.flow.Flow
import org.michorga.everythinginone.domain.repository.SalaryRepository

/** Retourne le Current Balance (somme des salaires ajoutés). */
class GetCurrentBalanceUseCase(private val repository: SalaryRepository) {
    operator fun invoke(): Flow<Double> = repository.getTotalSalary()
}


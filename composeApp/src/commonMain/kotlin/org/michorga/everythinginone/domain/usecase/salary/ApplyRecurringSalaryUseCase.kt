package org.michorga.everythinginone.domain.usecase.salary

import org.michorga.everythinginone.domain.repository.SalaryRepository

/** Applique automatiquement le salaire récurrent pour le mois en cours. */
class ApplyRecurringSalaryUseCase(private val repository: SalaryRepository) {
    suspend operator fun invoke(currentYearMonth: String) {
        repository.applyRecurringSalaryIfNeeded(currentYearMonth)
    }
}


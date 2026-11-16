package org.michorga.everythinginone.domain.usecase.salary

class SalaryUseCases(
    val getCurrentBalance: GetCurrentBalanceUseCase,
    val addSalary: AddSalaryUseCase,
    val applyRecurringSalary: ApplyRecurringSalaryUseCase,
    val getSalaries: GetSalariesUseCase,
    val deleteSalary: DeleteSalaryUseCase,
)


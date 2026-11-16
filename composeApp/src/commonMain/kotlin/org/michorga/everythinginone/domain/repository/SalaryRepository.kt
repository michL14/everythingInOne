package org.michorga.everythinginone.domain.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.michorga.everythinginone.data.local.database.AppQueries
import org.michorga.everythinginone.domain.model.Salary

interface SalaryRepository {
    /** Somme totale des salaires ajoutés, utilisée pour le Current Balance. */
    fun getTotalSalary(): Flow<Double>

    /** Liste réactive de tous les salaires ajoutés (entrées d'argent). */
    fun getAllSalaries(): Flow<List<Salary>>

    /** Ajoute le salaire du mois. Si [isRecurring] est vrai, il sera aussi
     *  réappliqué automatiquement chaque mois. */
    suspend fun addSalary(amount: Double, yearMonth: String, isRecurring: Boolean)

    /** Applique le salaire récurrent pour [currentYearMonth] s'il ne l'a pas
     *  déjà été (idempotent). */
    suspend fun applyRecurringSalaryIfNeeded(currentYearMonth: String)

    /** Supprime un salaire ajouté (ex : depuis la liste des transactions récentes). */
    suspend fun deleteSalary(id: Long)
}

class SalaryRepositoryImpl(
    private val queries: AppQueries
) : SalaryRepository {

    override fun getTotalSalary(): Flow<Double> {
        // Current Balance = salaires ajoutés - débits d'abonnements appliqués.
        return queries.selectCurrentBalance()
            .asFlow()
            .mapToOne(Dispatchers.IO)
            .map { it ?: 0.0 }
    }

    override fun getAllSalaries(): Flow<List<Salary>> {
        return queries.selectAllSalaries()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { list ->
                list.map { entity ->
                    Salary(
                        id = entity.id,
                        amount = entity.amount,
                        yearMonth = entity.yearMonth,
                        isRecurring = entity.isRecurring == 1L
                    )
                }
            }
    }

    override suspend fun addSalary(amount: Double, yearMonth: String, isRecurring: Boolean) {
        withContext(Dispatchers.IO) {
            queries.transaction {
                queries.insertSalary(
                    amount = amount,
                    yearMonth = yearMonth,
                    isRecurring = if (isRecurring) 1L else 0L
                )
                if (isRecurring) {
                    // Ce salaire devient le salaire récurrent, déjà appliqué pour ce mois.
                    queries.updateRecurringAmount(amount)
                    queries.updateLastAppliedYearMonth(yearMonth)
                }
            }
        }
    }

    override suspend fun applyRecurringSalaryIfNeeded(currentYearMonth: String) {
        withContext(Dispatchers.IO) {
            queries.transaction {
                val config = queries.selectSalaryConfig().executeAsOneOrNull() ?: return@transaction
                val recurringAmount = config.recurringAmount
                val lastApplied = config.lastAppliedYearMonth
                if (recurringAmount > 0.0 && lastApplied != currentYearMonth) {
                    queries.insertSalary(
                        amount = recurringAmount,
                        yearMonth = currentYearMonth,
                        isRecurring = 1L
                    )
                    queries.updateLastAppliedYearMonth(currentYearMonth)
                }
            }
        }
    }

    override suspend fun deleteSalary(id: Long) {
        withContext(Dispatchers.IO) {
            queries.deleteSalary(id)
        }
    }
}


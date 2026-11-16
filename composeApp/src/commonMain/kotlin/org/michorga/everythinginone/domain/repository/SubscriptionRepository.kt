package org.michorga.everythinginone.domain.repository

import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.async.coroutines.awaitAsOneOrNull
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import org.michorga.everythinginone.data.local.database.ioDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.michorga.everythinginone.data.local.database.AppQueries
import org.michorga.everythinginone.domain.model.Subscription
import org.michorga.everythinginone.domain.model.SubscriptionCharge

interface SubscriptionRepository {
    /** Liste réactive de tous les abonnements (actifs et désactivés). */
    fun getAllSubscriptions(): Flow<List<Subscription>>

    /** Historique réactif de tous les débits d'abonnement appliqués (sorties d'argent). */
    fun getAllSubscriptionCharges(): Flow<List<SubscriptionCharge>>

    /** Crée un abonnement. [dayOfMonth] est le jour de débit (défaut 1). */
    suspend fun addSubscription(name: String, amount: Double, dayOfMonth: Int)

    /** Modifie le nom, le montant et la date de débit d'un abonnement. */
    suspend fun updateSubscription(id: Long, name: String, amount: Double, dayOfMonth: Int)

    /** Active ou désactive un abonnement (un abonnement désactivé n'est plus débité). */
    suspend fun setActive(id: Long, isActive: Boolean)

    /** Supprime un abonnement (les débits déjà appliqués restent conservés). */
    suspend fun deleteSubscription(id: Long)

    /**
     * Débite le Current Balance pour chaque abonnement actif dont le jour de débit
     * est atteint pour le mois en cours et qui n'a pas encore été débité ce mois-ci.
     * Idempotent : peut être appelé à chaque ouverture de l'app.
     */
    suspend fun applyDueSubscriptions(currentYearMonth: String, currentDay: Int)

    /**
     * Déclare manuellement un abonnement comme payé pour le mois en cours, sans
     * attendre que le jour de débit soit atteint. Effectue la même action que
     * [applyDueSubscriptions] (débit + mémorisation du mois) pour cet abonnement.
     * Idempotent : ne fait rien si l'abonnement a déjà été débité ce mois-ci.
     */
    suspend fun markSubscriptionAsPaid(id: Long, currentYearMonth: String)

    /**
     * Annule le paiement d'un abonnement pour le mois en cours : supprime le débit
     * correspondant du ledger et réinitialise le dernier mois appliqué. Inverse de
     * [markSubscriptionAsPaid].
     */
    suspend fun markSubscriptionAsUnpaid(id: Long, currentYearMonth: String)
}

class SubscriptionRepositoryImpl(
    private val queries: AppQueries
) : SubscriptionRepository {

    override fun getAllSubscriptions(): Flow<List<Subscription>> {
        return queries.selectAllSubscriptions()
            .asFlow()
            .mapToList(ioDispatcher)
            .map { list ->
                list.map { entity ->
                    Subscription(
                        id = entity.id,
                        name = entity.name,
                        amount = entity.amount,
                        dayOfMonth = entity.dayOfMonth.toInt(),
                        isActive = entity.isActive == 1L,
                        lastAppliedYearMonth = entity.lastAppliedYearMonth
                    )
                }
            }
    }

    override fun getAllSubscriptionCharges(): Flow<List<SubscriptionCharge>> {
        return queries.selectAllSubscriptionCharges()
            .asFlow()
            .mapToList(ioDispatcher)
            .map { list ->
                list.map { entity ->
                    SubscriptionCharge(
                        id = entity.id,
                        amount = entity.amount,
                        yearMonth = entity.yearMonth,
                        subscriptionName = entity.subscriptionName
                    )
                }
            }
    }

    override suspend fun addSubscription(name: String, amount: Double, dayOfMonth: Int) {
        withContext(ioDispatcher) {
            queries.insertSubscription(
                name = name,
                amount = amount,
                dayOfMonth = dayOfMonth.toLong(),
                isActive = 1L,
                lastAppliedYearMonth = null
            )
        }
    }

    override suspend fun updateSubscription(id: Long, name: String, amount: Double, dayOfMonth: Int) {
        withContext(ioDispatcher) {
            queries.updateSubscription(
                name = name,
                amount = amount,
                dayOfMonth = dayOfMonth.toLong(),
                id = id
            )
        }
    }

    override suspend fun setActive(id: Long, isActive: Boolean) {
        withContext(ioDispatcher) {
            queries.setSubscriptionActive(
                isActive = if (isActive) 1L else 0L,
                id = id
            )
        }
    }

    override suspend fun deleteSubscription(id: Long) {
        withContext(ioDispatcher) {
            queries.deleteSubscription(id)
        }
    }

    override suspend fun applyDueSubscriptions(currentYearMonth: String, currentDay: Int) {
        withContext(ioDispatcher) {
            queries.transaction {
                val subscriptions = queries.selectActiveSubscriptions().awaitAsList()
                subscriptions.forEach { sub ->
                    val alreadyAppliedThisMonth = sub.lastAppliedYearMonth == currentYearMonth
                    val debitDayReached = currentDay >= sub.dayOfMonth.toInt()
                    if (!alreadyAppliedThisMonth && debitDayReached) {
                        queries.insertSubscriptionCharge(
                            subscriptionId = sub.id,
                            amount = sub.amount,
                            yearMonth = currentYearMonth
                        )
                        queries.updateSubscriptionLastApplied(
                            lastAppliedYearMonth = currentYearMonth,
                            id = sub.id
                        )
                    }
                }
            }
        }
    }

    override suspend fun markSubscriptionAsPaid(id: Long, currentYearMonth: String) {
        withContext(ioDispatcher) {
            queries.transaction {
                val sub = queries.selectSubscriptionById(id).awaitAsOneOrNull()
                    ?: return@transaction
                val alreadyAppliedThisMonth = sub.lastAppliedYearMonth == currentYearMonth
                if (!alreadyAppliedThisMonth) {
                    queries.insertSubscriptionCharge(
                        subscriptionId = sub.id,
                        amount = sub.amount,
                        yearMonth = currentYearMonth
                    )
                    queries.updateSubscriptionLastApplied(
                        lastAppliedYearMonth = currentYearMonth,
                        id = sub.id
                    )
                }
            }
        }
    }

    override suspend fun markSubscriptionAsUnpaid(id: Long, currentYearMonth: String) {
        withContext(ioDispatcher) {
            queries.transaction {
                queries.deleteSubscriptionChargeForMonth(
                    subscriptionId = id,
                    yearMonth = currentYearMonth
                )
                queries.updateSubscriptionLastApplied(
                    lastAppliedYearMonth = null,
                    id = id
                )
            }
        }
    }
}


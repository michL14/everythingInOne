package org.michorga.everythinginone.domain.usecase.subscription

import kotlinx.coroutines.flow.Flow
import org.michorga.everythinginone.domain.model.Subscription
import org.michorga.everythinginone.domain.model.SubscriptionCharge
import org.michorga.everythinginone.domain.repository.SubscriptionRepository

/** Liste réactive de tous les abonnements. */
class GetSubscriptionsUseCase(private val repository: SubscriptionRepository) {
    operator fun invoke(): Flow<List<Subscription>> = repository.getAllSubscriptions()
}

/** Historique réactif des débits d'abonnement appliqués (sorties d'argent). */
class GetSubscriptionChargesUseCase(private val repository: SubscriptionRepository) {
    operator fun invoke(): Flow<List<SubscriptionCharge>> = repository.getAllSubscriptionCharges()
}

/** Crée un abonnement (débité chaque mois jusqu'à désactivation/suppression). */
class AddSubscriptionUseCase(private val repository: SubscriptionRepository) {
    suspend operator fun invoke(name: String, amount: Double, dayOfMonth: Int) =
        repository.addSubscription(name, amount, dayOfMonth)
}

/** Modifie un abonnement existant (nom, montant, date de débit). */
class UpdateSubscriptionUseCase(private val repository: SubscriptionRepository) {
    suspend operator fun invoke(id: Long, name: String, amount: Double, dayOfMonth: Int) =
        repository.updateSubscription(id, name, amount, dayOfMonth)
}

/** Active / désactive un abonnement. */
class SetSubscriptionActiveUseCase(private val repository: SubscriptionRepository) {
    suspend operator fun invoke(id: Long, isActive: Boolean) =
        repository.setActive(id, isActive)
}

/** Supprime un abonnement. */
class DeleteSubscriptionUseCase(private val repository: SubscriptionRepository) {
    suspend operator fun invoke(id: Long) = repository.deleteSubscription(id)
}

/** Débite le Current Balance pour les abonnements dus (idempotent). */
class ApplyDueSubscriptionsUseCase(private val repository: SubscriptionRepository) {
    suspend operator fun invoke(currentYearMonth: String, currentDay: Int) =
        repository.applyDueSubscriptions(currentYearMonth, currentDay)
}

/** Déclare un abonnement comme payé pour le mois en cours (débit anticipé). */
class MarkSubscriptionPaidUseCase(private val repository: SubscriptionRepository) {
    suspend operator fun invoke(id: Long, currentYearMonth: String) =
        repository.markSubscriptionAsPaid(id, currentYearMonth)
}

/** Annule le paiement d'un abonnement pour le mois en cours. */
class MarkSubscriptionUnpaidUseCase(private val repository: SubscriptionRepository) {
    suspend operator fun invoke(id: Long, currentYearMonth: String) =
        repository.markSubscriptionAsUnpaid(id, currentYearMonth)
}

class SubscriptionUseCases(
    val getSubscriptions: GetSubscriptionsUseCase,
    val addSubscription: AddSubscriptionUseCase,
    val updateSubscription: UpdateSubscriptionUseCase,
    val setActive: SetSubscriptionActiveUseCase,
    val deleteSubscription: DeleteSubscriptionUseCase,
    val applyDueSubscriptions: ApplyDueSubscriptionsUseCase,
    val markAsPaid: MarkSubscriptionPaidUseCase,
    val markAsUnpaid: MarkSubscriptionUnpaidUseCase,
    val getSubscriptionCharges: GetSubscriptionChargesUseCase,
)


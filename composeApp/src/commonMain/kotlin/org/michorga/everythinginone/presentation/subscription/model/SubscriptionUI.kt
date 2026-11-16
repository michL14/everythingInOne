package org.michorga.everythinginone.presentation.subscription.model

/**
 * Représentation UI d'un abonnement, avec la prochaine date de débit déjà calculée
 * et formatée pour l'affichage dans l'écran "Upcoming Payment".
 */
data class SubscriptionUI(
    val id: Long,
    val name: String,
    val amount: Double,
    val dayOfMonth: Int,
    val isActive: Boolean,
    /** Prochaine date de débit formatée, ex: "05/10/2026". */
    val nextPaymentDate: String,
    /** true si l'abonnement a déjà été débité (payé) pour le mois en cours. */
    val isPaidThisMonth: Boolean = false,
)


package org.michorga.everythinginone.domain.model

/**
 * Débit mensuel appliqué automatiquement pour un abonnement.
 * [subscriptionName] peut être null si l'abonnement associé a été supprimé.
 */
data class SubscriptionCharge(
    val id: Long,
    val amount: Double,
    val yearMonth: String,
    val subscriptionName: String?,
)


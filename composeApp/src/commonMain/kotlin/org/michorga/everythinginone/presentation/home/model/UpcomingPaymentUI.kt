package org.michorga.everythinginone.presentation.home.model

/**
 * Élément affiché dans le carrousel horizontal "Upcoming Payments" de l'écran
 * d'accueil. Représente un abonnement actif qui n'a pas encore été payé pour le
 * mois en cours.
 *
 * @param amount montant déjà formaté (ex : "13.49"), sans symbole.
 * @param nextPaymentDate prochaine date de débit formatée (ex : "05/10/2026").
 */
data class UpcomingPaymentUI(
    val id: Long,
    val name: String,
    val amount: String,
    val nextPaymentDate: String,
)


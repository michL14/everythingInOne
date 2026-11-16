package org.michorga.everythinginone.presentation.subscription.model

/**
 * Filtres disponibles sur l'écran des abonnements.
 *
 * - [ALL]    : tous les abonnements.
 * - [TO_PAY] : abonnements du mois en cours qui restent à payer.
 * - [PAYED]  : abonnements du mois en cours déjà payés (débités).
 */
enum class SubscriptionFilter {
    ALL,
    TO_PAY,
    PAYED,
}


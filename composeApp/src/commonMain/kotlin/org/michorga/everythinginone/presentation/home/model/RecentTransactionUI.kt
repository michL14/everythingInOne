package org.michorga.everythinginone.presentation.home.model

/**
 * Élément affiché dans la liste "Recent Transactions" de l'écran d'accueil.
 * Regroupe tous les mouvements d'argent : salaires ajoutés, dépenses,
 * abonnements, remboursements et débits d'abonnement récurrents.
 *
 * @param isIncome vrai s'il s'agit d'une entrée d'argent (flèche verte),
 *                 faux s'il s'agit d'une sortie d'argent (flèche rouge).
 * @param amount montant déjà formaté (ex : "1250.00"), sans signe ni symbole.
 */
data class RecentTransactionUI(
    val id: String,
    val label: String,
    val amount: String,
    val isIncome: Boolean,
    val month: Int,
)


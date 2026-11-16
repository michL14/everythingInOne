package org.michorga.everythinginone.domain.model

/**
 * Un abonnement qui débite le Current Balance chaque mois tant qu'il est actif.
 *
 * @param dayOfMonth jour du mois où le débit est effectué (1..31). Par défaut le 1er.
 * @param isActive quand false, l'abonnement n'est plus débité (désactivé).
 * @param lastAppliedYearMonth dernier mois "yyyy-MM" déjà débité (idempotence).
 */
data class Subscription(
    val id: Long = 0,
    val name: String,
    val amount: Double,
    val dayOfMonth: Int = 1,
    val isActive: Boolean = true,
    val lastAppliedYearMonth: String? = null
)


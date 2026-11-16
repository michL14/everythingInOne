package org.michorga.everythinginone.presentation.subscription.components

import androidx.compose.ui.graphics.Color
import kotlin.math.absoluteValue

/**
 * Palette de couleurs saturées et suffisamment sombres pour rester lisibles
 * avec du texte blanc par-dessus. Chaque abonnement se voit attribuer une
 * couleur stable (déterministe) en fonction de son identifiant, ce qui donne
 * un rendu varié mais qui ne "clignote" pas entre les recompositions.
 */
private val SubscriptionPalette = listOf(
    Color(0xFF6C5CE7), // violet
    Color(0xFF0984E3), // bleu
    Color(0xFF00B894), // vert
    Color(0xFFE17055), // orange brûlé
    Color(0xFFD63031), // rouge
    Color(0xFFE84393), // rose
    Color(0xFF00838F), // teal
    Color(0xFF8E44AD), // pourpre
    Color(0xFF2D3436), // ardoise
    Color(0xFF1289A7), // cyan profond
    Color(0xFFC0392B), // brique
    Color(0xFF16A085), // émeraude
)

/**
 * Renvoie une couleur stable et lisible pour un abonnement donné.
 *
 * @param seed identifiant (ou toute clé stable) de l'abonnement.
 * @param isActive quand false, la couleur est atténuée pour signaler l'état désactivé.
 */
fun subscriptionColorFor(seed: Long, isActive: Boolean = true): Color {
    val index = (seed.hashCode().absoluteValue) % SubscriptionPalette.size
    val base = SubscriptionPalette[index]
    return if (isActive) base else base.copy(alpha = 0.35f)
}

/**
 * Couleurs douces indiquant l'état de paiement du mois en cours :
 * vert atténué quand l'abonnement est déjà payé, rouge atténué sinon.
 * Les teintes restent lisibles avec du texte blanc sans être trop vives.
 */
private val SubscriptionPaidColor = Color(0xFF5B9279)   // vert doux
private val SubscriptionUnpaidColor = Color(0xFFBE6B72) // rouge doux

/**
 * Renvoie une couleur en fonction du statut de paiement du mois en cours.
 *
 * @param isPaidThisMonth true si l'abonnement est payé pour le mois courant.
 * @param isActive quand false, la couleur est atténuée pour signaler l'état désactivé.
 */
fun subscriptionStatusColor(isPaidThisMonth: Boolean, isActive: Boolean = true): Color {
    val base = if (isPaidThisMonth) SubscriptionPaidColor else SubscriptionUnpaidColor
    return if (isActive) base else base.copy(alpha = 0.35f)
}


package org.michorga.everythinginone.presentation.home.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object AppState {
    var currentScreen by mutableStateOf(Screen.HOME)
    var currentSubScreen by mutableStateOf<SubScreen?>(null)

    /** true si l'écran Add Payment a été ouvert depuis le "+" de la home. */
    var addPaymentFromHome by mutableStateOf(false)

    /** Gère le back navigation. Retourne true si back géré, false si l'app doit quitter */
    fun handleBack(): Boolean {
        return when (currentScreen) {
            Screen.HOME -> false

            Screen.PAYMENT -> {
                when (currentSubScreen) {
                    SubScreen.ADD_PAYMENT -> {
                        if (addPaymentFromHome) {
                            currentScreen = Screen.HOME
                            currentSubScreen = null
                            addPaymentFromHome = false
                        } else {
                            currentSubScreen = SubScreen.RECENT_TRANSACTIONS
                        }
                        true
                    }
                    SubScreen.UPCOMING_PAYMENT,
                    SubScreen.RECENT_TRANSACTIONS,
                    SubScreen.PAYMENT,
                    null -> {
                        currentScreen = Screen.HOME
                        currentSubScreen = null
                        true
                    }
                }
            }

            Screen.SETTINGS -> {
                currentScreen = Screen.HOME
                true
            }
        }
    }
}
package org.michorga.everythinginone.presentation.home.vm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.component.KoinComponent
import org.michorga.everythinginone.presentation.home.navigation.AppState
import org.michorga.everythinginone.presentation.home.navigation.Screen
import org.michorga.everythinginone.presentation.home.navigation.SubScreen

class HomeViewModel : ViewModel(), KoinComponent {

    private val scope = CoroutineScope(Dispatchers.Default)

    /** Navigue vers l’écran "Add Payment" dans la section Payment */
    fun navigateToAddPayment() {
        AppState.currentScreen = Screen.PAYMENT
        AppState.currentSubScreen = SubScreen.ADD_PAYMENT
    }

    /** Navigue vers la section Payment (Recent Transactions par défaut) */
    fun navigateToPayment() {
        AppState.currentScreen = Screen.PAYMENT
        AppState.currentSubScreen = SubScreen.PAYMENT
    }

    /** Navigue vers Home */
    fun navigateHome() {
        AppState.currentScreen = Screen.HOME
        AppState.currentSubScreen = null
    }
}
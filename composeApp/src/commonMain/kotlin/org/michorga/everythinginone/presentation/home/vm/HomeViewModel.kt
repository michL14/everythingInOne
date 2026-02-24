package org.michorga.everythinginone.presentation.home.vm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.component.KoinComponent
import org.michorga.everythinginone.presentation.home.navigation.AppState
import org.michorga.everythinginone.presentation.home.navigation.Screen

class HomeViewModel(

) : ViewModel(), KoinComponent {

    private val scope = CoroutineScope(Dispatchers.Default)

    fun navigateToAddPayment(){
        AppState.currentScreen = Screen.ADD_PAYMENT
    }

}

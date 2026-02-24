package org.michorga.everythinginone.presentation.home.navigation

// Shared module ou Android/iOS
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

object AppState {
    var currentScreen by mutableStateOf(Screen.HOME)
}

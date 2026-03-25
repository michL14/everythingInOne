package org.michorga.everythinginone.presentation.home.navigation
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.michorga.everythinginone.presentation.payment.screen.PaymentScreen
import org.michorga.everythinginone.presentation.payment.vm.PaymentViewModel
import org.michorga.everythinginone.presentation.home.screen.HomeScreen
import org.michorga.everythinginone.presentation.home.vm.HomeViewModel
import org.michorga.everythinginone.presentation.payment.screen.AddPaymentScreen
import org.michorga.everythinginone.theme.AppTheme.colors

enum class Screen(val label: String, val subScreens: List<SubScreen> = emptyList()) {
    HOME("Home"),
    PAYMENT("Payment", subScreens = listOf(
        SubScreen.PAYMENT,
        SubScreen.RECENT_TRANSACTIONS,
        SubScreen.UPCOMING_PAYMENT,
        SubScreen.ADD_PAYMENT

    )),
    SETTINGS("Settings")
}

enum class SubScreen(val label: String) {
    PAYMENT("Payment"),
    RECENT_TRANSACTIONS("Recent Transactions"),
    UPCOMING_PAYMENT("Upcoming Payment"),
    ADD_PAYMENT("Add Payment")
}

@Composable
fun AppContent(
    paymentViewModel: PaymentViewModel,
    homeViewModel: HomeViewModel,
) {
    when (AppState.currentScreen) {
        Screen.HOME -> {
            HomeScreen(
                viewModel = homeViewModel,
                modifier = Modifier,
                onNavigateToPayment = {
                    AppState.currentScreen = Screen.PAYMENT
                    AppState.currentSubScreen = SubScreen.RECENT_TRANSACTIONS
                }
            )
        }

        Screen.PAYMENT -> {
            when (AppState.currentSubScreen) {
                SubScreen.PAYMENT -> PaymentScreen(
                    viewModel = paymentViewModel,
                    modifier = Modifier,
                    onAddPaymentClick = { AppState.currentSubScreen = SubScreen.ADD_PAYMENT }
                )
                SubScreen.RECENT_TRANSACTIONS -> PaymentScreen(
                    viewModel = paymentViewModel,
                    modifier = Modifier,
                    onAddPaymentClick = { AppState.currentSubScreen = SubScreen.ADD_PAYMENT }
                )
                SubScreen.UPCOMING_PAYMENT -> PaymentScreen(
                    viewModel = paymentViewModel,
                    modifier = Modifier,
                    onAddPaymentClick = { AppState.currentSubScreen = SubScreen.ADD_PAYMENT }
                )
                SubScreen.ADD_PAYMENT -> AddPaymentScreen(
                    viewModel = paymentViewModel,
                    modifier = Modifier.background(colors.background),
                    onBackClick = { AppState.currentSubScreen = SubScreen.RECENT_TRANSACTIONS },
                    navigateHome = { AppState.currentScreen = Screen.HOME }
                )
                null -> {
                    AppState.currentSubScreen = SubScreen.RECENT_TRANSACTIONS
                }
            }
        }

        Screen.SETTINGS -> {
            // SettingsScreen(viewModel = settingsViewModel)
        }
    }
}
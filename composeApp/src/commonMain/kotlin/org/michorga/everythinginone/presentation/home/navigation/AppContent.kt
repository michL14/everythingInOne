package org.michorga.everythinginone.presentation.home.navigation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.michorga.everythinginone.presentation.payment.screen.PaymentScreen
import org.michorga.everythinginone.presentation.payment.vm.PaymentViewModel
import org.michorga.everythinginone.presentation.home.screen.HomeScreen
import org.michorga.everythinginone.presentation.home.vm.HomeViewModel
import org.michorga.everythinginone.presentation.payment.screen.AddPaymentScreen

enum class Screen(val label: String) {
    HOME("Home"),
    PAYMENT("Payment"),
    ADD_PAYMENT("Add Payment")
}

@Composable
fun AppContent(
    paymentViewModel: PaymentViewModel,
    homeViewModel: HomeViewModel,
) {
    when (AppState.currentScreen) {
        Screen.HOME -> HomeScreen(
            viewModel = homeViewModel,
            modifier = Modifier
        )
        Screen.PAYMENT -> PaymentScreen(
            viewModel = paymentViewModel,
            modifier = Modifier
        )
        Screen.ADD_PAYMENT -> AddPaymentScreen(
            viewModel = paymentViewModel,
            modifier = Modifier,
            onBackClick = {
                AppState.currentScreen = Screen.HOME
            },
            navigateHome = {
                AppState.currentScreen = Screen.HOME
            }
        )
    }
}
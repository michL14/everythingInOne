package org.michorga.everythinginone

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.michorga.everythinginone.presentation.payment.vm.PaymentViewModel
import org.michorga.everythinginone.presentation.home.navigation.AppContent
import org.michorga.everythinginone.presentation.home.vm.HomeViewModel
import org.michorga.everythinginone.theme.AppTheme

@Composable
fun App(
    paymentViewModel: PaymentViewModel,
    homeViewModel: HomeViewModel,
) {
    AppTheme {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppContent(
                paymentViewModel = paymentViewModel,
                homeViewModel = homeViewModel
            )
        }
    }
}
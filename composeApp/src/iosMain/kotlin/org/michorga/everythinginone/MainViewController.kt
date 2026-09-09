package org.michorga.everythinginone

import androidx.compose.ui.window.ComposeUIViewController
import org.koin.mp.KoinPlatform.getKoin
import org.michorga.everythinginone.data.iosModule
import org.michorga.everythinginone.di.initKoin
import org.michorga.everythinginone.presentation.home.vm.HomeViewModel
import org.michorga.everythinginone.presentation.payment.vm.PaymentViewModel
import org.michorga.everythinginone.presentation.subscription.vm.UpcomingPaymentViewModel

fun MainViewController() = ComposeUIViewController {

    initKoin(iosModule)

    val paymentViewModel: PaymentViewModel = getKoin().get()
    val homeViewModel: HomeViewModel = getKoin().get()
    val upcomingPaymentViewModel: UpcomingPaymentViewModel = getKoin().get()

    App(
        paymentViewModel = paymentViewModel,
        homeViewModel = homeViewModel,
        upcomingPaymentViewModel = upcomingPaymentViewModel,
    )
}

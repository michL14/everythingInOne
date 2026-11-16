package org.michorga.everythinginone

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import org.koin.mp.KoinPlatform.getKoin
import org.michorga.everythinginone.data.iosModule
import org.michorga.everythinginone.di.initKoin
import org.michorga.everythinginone.presentation.home.vm.HomeViewModel
import org.michorga.everythinginone.presentation.payment.vm.PaymentViewModel
import org.michorga.everythinginone.presentation.subscription.vm.UpcomingPaymentViewModel

/**
 * Démarre Koin une seule fois au lancement de l'app iOS.
 * Appelé depuis Swift (iOSApp.swift), comme MainApplication.onCreate() côté Android.
 */
fun startKoinIos() {
    initKoin(iosModule)
}

fun MainViewController() = ComposeUIViewController {

    // ViewModels conservés entre recompositions (équivalent du scope Android)
    val paymentViewModel: PaymentViewModel = remember { getKoin().get() }
    val homeViewModel: HomeViewModel = remember { getKoin().get() }
    val upcomingPaymentViewModel: UpcomingPaymentViewModel = remember { getKoin().get() }

    App(
        paymentViewModel = paymentViewModel,
        homeViewModel = homeViewModel,
        upcomingPaymentViewModel = upcomingPaymentViewModel,
    )
}

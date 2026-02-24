package org.michorga.everythinginone

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.michorga.everythinginone.DesktopKoin.getKoin
import org.michorga.everythinginone.data.local.database.DatabaseDriverFactory
import org.michorga.everythinginone.data.local.desktopModule
import org.michorga.everythinginone.di.initKoin
import org.michorga.everythinginone.di.sharedModule
import org.michorga.everythinginone.presentation.home.vm.HomeViewModel
import org.michorga.everythinginone.presentation.payment.vm.PaymentViewModel

object DesktopKoin : KoinComponent

fun main() = application {
    startKoin { modules(sharedModule, desktopModule) }

    val paymentViewModel: PaymentViewModel = DesktopKoin.get()
    val homeViewModel: HomeViewModel = DesktopKoin.get()

    Window(onCloseRequest = ::exitApplication, title = "EverythingInOne") {
        App(paymentViewModel = paymentViewModel, homeViewModel = homeViewModel)
    }
}

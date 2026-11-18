package org.michorga.everythinginone

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import app.cash.sqldelight.db.SqlDriver
import kotlinx.browser.document
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.mp.KoinPlatform
import org.michorga.everythinginone.data.local.database.DatabaseDriverFactory
import org.michorga.everythinginone.data.local.database.ensureSchema
import org.michorga.everythinginone.di.sharedModule
import org.michorga.everythinginone.presentation.home.vm.HomeViewModel
import org.michorga.everythinginone.presentation.payment.vm.PaymentViewModel
import org.michorga.everythinginone.presentation.subscription.vm.UpcomingPaymentViewModel

fun main() {
    MainScope().launch {
        try {
            startApp()
        } catch (e: Throwable) {
            document.body?.textContent = "Erreur de démarrage : ${e.message}"
            throw e
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
private suspend fun startApp() {
    run {
        val driver = DatabaseDriverFactory(Any()).createDriver()
        driver.ensureSchema()

        startKoin { modules(sharedModule, module { single<SqlDriver> { driver } }) }
        val koin = KoinPlatform.getKoin()

        ComposeViewport(document.body!!) {
            App(
                paymentViewModel = koin.get<PaymentViewModel>(),
                homeViewModel = koin.get<HomeViewModel>(),
                upcomingPaymentViewModel = koin.get<UpcomingPaymentViewModel>(),
            )
        }
    }
}

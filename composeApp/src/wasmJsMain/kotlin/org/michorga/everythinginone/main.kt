package org.michorga.everythinginone

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.ComposeViewport
import app.cash.sqldelight.db.SqlDriver
import kotlinx.browser.document
import kotlinx.browser.window
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
    if (window.location.search.contains("test")) {
        ComposeViewport(document.body!!) {
            Box(Modifier.fillMaxSize().background(Color.Red), contentAlignment = Alignment.Center) {
                Text("Compose OK", color = Color.White, fontSize = 32.sp)
            }
        }
    } else {
        startFullApp()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
private suspend fun startFullApp() {
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

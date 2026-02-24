package org.michorga.everythinginone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.michorga.everythinginone.presentation.home.vm.HomeViewModel
import org.michorga.everythinginone.presentation.payment.vm.PaymentViewModel

class MainActivity : ComponentActivity() {
    private val paymentViewModel: PaymentViewModel by viewModel()
    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App(paymentViewModel, homeViewModel)
        }
    }
}
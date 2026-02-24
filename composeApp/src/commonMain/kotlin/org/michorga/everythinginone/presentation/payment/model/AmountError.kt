package org.michorga.everythinginone.presentation.payment.model


sealed class AmountError {
    object Mandatory : AmountError()
    object Invalid : AmountError()
    object Negative : AmountError()
}
package org.michorga.everythinginone.presentation.payment.mapper

import androidx.compose.runtime.Composable
import everythinginone.composeapp.generated.resources.Res
import everythinginone.composeapp.generated.resources.invalid_amount_error
import everythinginone.composeapp.generated.resources.mandatory_amount_error
import everythinginone.composeapp.generated.resources.not_null_amount_error
import org.jetbrains.compose.resources.stringResource
import org.michorga.everythinginone.presentation.payment.model.AmountError

object AmountErrorMapper {
    @Composable
    fun toMessage(amountError: AmountError?): String? {
        return when (amountError) {
            AmountError.Mandatory -> stringResource(Res.string.mandatory_amount_error)
            AmountError.Invalid -> stringResource(Res.string.invalid_amount_error)
            AmountError.Negative -> stringResource(Res.string.not_null_amount_error)
            null -> null
        }
    }
}
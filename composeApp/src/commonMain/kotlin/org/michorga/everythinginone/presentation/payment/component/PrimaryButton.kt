package org.michorga.everythinginone.presentation.payment.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.michorga.everythinginone.theme.primaryButtonCustomColors

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    onClick : () -> Unit,
    primaryButtonLabel: String,
    enabled: Boolean = true,
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            enabled = enabled,
            modifier = Modifier,
            colors = primaryButtonCustomColors(),
            onClick = onClick,
        ) {
            Text(
                text = primaryButtonLabel
            )
        }
    }
}
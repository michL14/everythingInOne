package org.michorga.everythinginone.presentation.payment.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import everythinginone.composeapp.generated.resources.Res
import everythinginone.composeapp.generated.resources.add_an_amount_text_field
import org.jetbrains.compose.resources.stringResource
import org.michorga.everythinginone.presentation.payment.model.TransactionModel
import org.michorga.everythinginone.theme.AppTheme.colors
import org.michorga.everythinginone.theme.AppTheme.sizes
import org.michorga.everythinginone.theme.textFieldCustomColors

@Composable
fun InputTextField(
    title: String,
    modifier: Modifier = Modifier,
    text: String?,
    onTextChange: (String) -> Unit,
    error: String? = null,
    singleLine: Boolean = true
){
    Column(
        modifier = modifier,
    ) {
        Text(
            text = title,
            fontSize = sizes.fieldsSize,
            fontWeight = Bold,
            color = colors.title
        )
        OutlinedTextField(
            value = text ?: "",
            onValueChange = onTextChange,
            isError = error != null,
            supportingText = {
                error?.let {
                    Text(text = it, color = colors.error)
                }
            },
            modifier = modifier.fillMaxWidth(),
            colors = textFieldCustomColors(),
            shape = RoundedCornerShape(sizes.cornerRadius),
            textStyle = TextStyle(
                color = colors.onSurface,
                fontSize = sizes.bodySize
            ),
            singleLine = singleLine
        )
    }
}
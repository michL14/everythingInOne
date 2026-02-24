package org.michorga.everythinginone.theme

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable


@Composable
fun filterChipCustomColors() = FilterChipDefaults.filterChipColors(
    containerColor = AppTheme.colors.surface,
    labelColor = AppTheme.colors.onSurface,
    selectedContainerColor = AppTheme.colors.primary,
    selectedLabelColor = AppTheme.colors.onPrimary,
    selectedLeadingIconColor = AppTheme.colors.onPrimary,
    disabledContainerColor = AppTheme.colors.secondary.copy(alpha = 0.5f)
)

@Composable
fun primaryButtonCustomColors() = ButtonDefaults.buttonColors(
    containerColor = AppTheme.colors.primary,
    contentColor = AppTheme.colors.onPrimary,
    disabledContainerColor = AppTheme.colors.disabledContainer,
    disabledContentColor = AppTheme.colors.disabledContent
)

@Composable
fun textFieldCustomColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = AppTheme.colors.container,
    unfocusedBorderColor = AppTheme.colors.container,
    focusedLabelColor = AppTheme.colors.primary,
    unfocusedLabelColor = AppTheme.colors.subtitle,
    cursorColor = AppTheme.colors.primary,
    focusedContainerColor = AppTheme.colors.background,
    unfocusedContainerColor = AppTheme.colors.background
)
package org.michorga.everythinginone.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import everythinginone.composeapp.generated.resources.Res
import everythinginone.composeapp.generated.resources.home_salutation
import everythinginone.composeapp.generated.resources.profile_name
import org.jetbrains.compose.resources.stringResource
import org.michorga.everythinginone.theme.AppIcons
import org.michorga.everythinginone.theme.AppTheme.colors
import org.michorga.everythinginone.theme.AppTheme.sizes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = stringResource(Res.string.home_salutation),
                fontSize = sizes.subtitleSize,
                color = colors.description
            )
            Text(
                text = stringResource(Res.string.profile_name),
                fontSize = sizes.titleSize,
                fontWeight = Bold,
                color = colors.title
            )
        }
        Row {
            IconButton(onClick = {}) { AppIcons.IconHomePage() }
            IconButton(onClick = {}) { AppIcons.IconButtonNotification() }
        }
    }
}
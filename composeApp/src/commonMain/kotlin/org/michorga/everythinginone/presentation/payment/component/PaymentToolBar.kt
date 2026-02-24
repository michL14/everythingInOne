package org.michorga.everythinginone.presentation.payment.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.michorga.everythinginone.theme.AppIcons


@Composable
fun ExpenseToolBar(
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ){
        IconButton(
            onClick = {

            },
            modifier = Modifier
        ) { AppIcons.IconButtonAdd() }

        IconButton(
            onClick = {},
            modifier = Modifier
        ) { AppIcons.IconButtonDelete() }
    }
}
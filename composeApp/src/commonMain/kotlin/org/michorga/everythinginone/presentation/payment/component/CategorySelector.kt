package org.michorga.everythinginone.presentation.payment.component

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.michorga.everythinginone.presentation.payment.model.CategoryModelUI
import org.michorga.everythinginone.theme.filterChipCustomColors

@Composable
fun CategorySelector(
    modifier: Modifier = Modifier,
    categories: List<CategoryModelUI>,
    selectedCategory: Long,
    onCategoryClick: (Long) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.wrapContentWidth()
        ) {
            categories.forEach { category ->
                val isSelected = category.id == selectedCategory
                FilterChip(
                    colors = filterChipCustomColors(),
                    selected = isSelected,
                    onClick = { onCategoryClick(category.id) },
                    label = { Text(category.name) }
                )
            }
        }
    }
}
package org.michorga.everythinginone.presentation.payment.component

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.michorga.everythinginone.domain.model.Category
import org.michorga.everythinginone.theme.filterChipCustomColors

@Composable
fun CategorySelector(
    categories: List<Category>,
    selectedCategory: Long?,
    onCategoryClick: (Long) -> Unit
) {
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
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
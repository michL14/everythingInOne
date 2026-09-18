package org.michorga.everythinginone.presentation.payment.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import org.michorga.everythinginone.theme.AppTheme
import org.michorga.everythinginone.theme.AppTheme.colors
import org.michorga.everythinginone.theme.AppTheme.sizes

@Composable
fun MonthPicker(
    months: List<String>,
    selectedIndex: Int,
    onMonthSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    LaunchedEffect(selectedIndex) {
        val center = listState.layoutInfo.visibleItemsInfo.size / 2
        val scrollIndex = (selectedIndex - center).coerceAtLeast(0)
        listState.animateScrollToItem(scrollIndex)
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyRow(
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(sizes.paddingMedium),
            contentPadding = PaddingValues(horizontal = sizes.bigSize)
        ) {
            itemsIndexed(months) { index, month ->
                val isSelected = index == selectedIndex

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(sizes.cornerRadiusSmall))
                        .background(
                            if (isSelected) colors.primary
                            else colors.transparent
                        )
                        .clickable { onMonthSelected(index) }
                        .padding(horizontal = sizes.paddingSmall2, vertical = sizes.paddingSmall),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = month,
                        style = if (isSelected)
                            AppTheme.font.bodyLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = colors.title
                            )
                        else
                            AppTheme.font.bodyMedium.copy(
                                color = colors.subtitle
                            )
                    )
                }
            }
        }
    }
}
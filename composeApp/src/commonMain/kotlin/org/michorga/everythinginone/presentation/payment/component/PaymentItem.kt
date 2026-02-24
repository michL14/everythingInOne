package org.michorga.everythinginone.presentation.payment.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.stevdza_san.swipeable.Swipeable
import com.stevdza_san.swipeable.domain.ActionCustomization
import com.stevdza_san.swipeable.domain.HapticFeedbackConfig
import com.stevdza_san.swipeable.domain.SwipeAction
import com.stevdza_san.swipeable.domain.SwipeBackground
import com.stevdza_san.swipeable.domain.SwipeBehavior
import everythinginone.composeapp.generated.resources.Res
import everythinginone.composeapp.generated.resources.icon_button

@Composable
fun ExpenseItem(expenseCategory: String, expenseAmount: Double) {
    Swipeable(
        behavior = SwipeBehavior.REVEAL,
        rightRevealActions = listOf(
            SwipeAction(
                customization = ActionCustomization(
                    icon = Res.drawable.icon_button,
                    iconColor = Color.White,
                    containerColor = Color.Red
                ),
                onAction = { /* Delete item */ }
            )
        ),
        rightBackground = SwipeBackground.linearGradient(
            colors = listOf(Color.Red, Color.White)
        ),
        // Add haptic feedback
        hapticFeedbackConfig = HapticFeedbackConfig.Default
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(expenseCategory)
                Text("$expenseAmount")
            }
        }
    }
}
package org.michorga.everythinginone.theme

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.SelectableChipColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Colors
 */
data class AppColors(
    val primary: Color,
    val onPrimary: Color,
    val onPrimaryLight: Color,
    val secondary: Color,
    val onSecondary: Color,
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val title: Color,
    val subtitle: Color,
    val error: Color = Color(0xFFB00020),
    val onError: Color = Color.White,
    val refund: Color = Color(0xFFB1DA9E),
    val onRefund: Color = Color(0xFF2f6218),
    val expense: Color = Color(0xFFF4CCCC),
    val onExpense: Color = Color(0xFF8D001A),
    val description: Color = Color.Gray,
    val disabledContainer: Color,
    val disabledContent: Color,
    val container: Color,
    val containerCard: Color,
)

private val LightAppColors = AppColors(
    primary = Color(0xFFF88585),
    onPrimary = Color.DarkGray,
    onPrimaryLight = Color.White,
    secondary = Color(0xF6B3B3),
    onSecondary = Color.Gray,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    title = Color(0xFF1E1E1E),
    subtitle = Color.DarkGray,
    container = Color.LightGray,
    disabledContainer = Color.LightGray,
    disabledContent = Color.Black,
    containerCard = Color(0xFFF3F6F4)
)

private val DarkAppColors = AppColors(
    primary = Color(0x811717),
    onPrimary = Color.Black,
    onPrimaryLight = Color.White,
    secondary = Color(0xAE5454),
    onSecondary = Color.Black,
    background = Color(0xFF121212),
    onBackground = Color.White,
    surface = Color(0xFF1E1E1E),
    onSurface = Color.White,
    title = Color(0xFFFFFFFF),
    subtitle = Color(0xFFFFFFFF),
    disabledContainer = Color.LightGray,
    disabledContent = Color.Black,
    container = Color.LightGray,
    containerCard = Color(0xFFF3F6F4)
)



val LocalAppColors = staticCompositionLocalOf { LightAppColors }



@Composable
fun AppTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkAppColors else LightAppColors
    val sizes = AppSize()

    CompositionLocalProvider(
        LocalAppColors provides colors,
        LocalAppSize provides sizes
    ) {
        content()
    }
}

object AppTheme {
    val colors: AppColors
        @Composable
        get() = LocalAppColors.current

    val sizes: AppSize
        @Composable
        get() = LocalAppSize.current


}

/**
 * Sizes
 */

data class AppSize(
    val paddingSmall: Dp = 8.dp,
    val paddingMedium: Dp = 16.dp,
    val paddingLarge: Dp = 24.dp,

    val cornerRadius: Dp = 24.dp,

    val titleSize: TextUnit = 30.sp,
    val subtitleSize: TextUnit = 28.sp,
    val fieldsSize: TextUnit = 18.sp,
    val bodySize: TextUnit = 14.sp,
    val captionSize: TextUnit = 12.sp
)

// 2. Création du CompositionLocal
val LocalAppSize = staticCompositionLocalOf { AppSize() }

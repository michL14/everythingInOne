package org.michorga.everythinginone.theme

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import compose.icons.TablerIcons
import compose.icons.tablericons.ArrowBack
import compose.icons.tablericons.ArrowDownCircle
import compose.icons.tablericons.ArrowUpCircle
import compose.icons.tablericons.Bell
import compose.icons.tablericons.CircleCheck
import compose.icons.tablericons.CirclePlus
import compose.icons.tablericons.Home
import compose.icons.tablericons.Trash

object AppIcons {

    @Composable
    fun IconButtonAdd(
        tint: Color = LocalAppColors.current.primary
    ) {
        Icon(
            imageVector = TablerIcons.CirclePlus, contentDescription = "Add", tint = tint
        )
    }

    @Composable
    fun IconButtonDelete(
        tint: Color = LocalAppColors.current.primary
    ) {
        Icon(
            imageVector = TablerIcons.Trash, contentDescription = "Delete", tint = tint
        )
    }

    @Composable
    fun IconHomePage(
        tint: Color = LocalAppColors.current.primary
    ) {
        Icon(
            imageVector = TablerIcons.Home, contentDescription = "Home", tint = tint
        )
    }

    @Composable
    fun IconButtonNotification(
        tint: Color = LocalAppColors.current.primary
    ) {
        Icon(
            imageVector = TablerIcons.Bell, contentDescription = "Notification", tint = tint
        )
    }

    @Composable
    fun IconButtonBack(
        tint: Color = LocalAppColors.current.primary
    ) {
        Icon(
            imageVector = TablerIcons.ArrowBack, contentDescription = "Back", tint = tint
        )
    }

    @Composable
    fun IconValidate(
        tint: Color = LocalAppColors.current.primary
    ) {
        Icon(
            imageVector = TablerIcons.CircleCheck, contentDescription = "Validate", tint = tint
        )
    }

    @Composable
    fun IconArrowPayment(
        isRefund: Boolean,
    ) {
        if (isRefund) Icon(
            imageVector = TablerIcons.ArrowUpCircle,
            contentDescription = "Arrow up",
            tint = LocalAppColors.current.onRefund
        )
        else Icon(
            imageVector = TablerIcons.ArrowDownCircle,
            contentDescription = "Arrow down",
            tint = LocalAppColors.current.onExpense
        )
    }
}

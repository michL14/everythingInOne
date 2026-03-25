package org.michorga.everythinginone.presentation.payment.mapper

import org.michorga.everythinginone.domain.model.Category
import org.michorga.everythinginone.presentation.payment.model.CategoryModelUI

fun List<Category>.toUIList(): List<CategoryModelUI> =
    this.map {
        CategoryModelUI(
            id = it.id,
            name = it.name,
        )
    }

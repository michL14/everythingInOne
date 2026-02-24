package org.michorga.everythinginone.data.mapper

import org.michorga.everythinginone.domain.model.Category
import org.michorga.everythinginone.domain.model.CategoryEnum

fun CategoryEnum.toCategory() = Category(
    id = this.id,
    name = this.name
)

fun Long.toCategoryEnum(): CategoryEnum {
    return CategoryEnum.entries.find { it.id == this } ?: CategoryEnum.EXPENSE
}
package org.michorga.everythinginone.domain.usecase.category

import org.michorga.everythinginone.domain.repository.CategoryRepository

class AddCategoryUseCase(private val repository: CategoryRepository) {
    suspend operator fun invoke(name: String) {
        if (name.isBlank()) {
            throw IllegalArgumentException("Le nom de la catégorie ne peut pas être vide")
        }
        repository.insertCategory(name.trim())
    }
}
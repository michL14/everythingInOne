package org.michorga.everythinginone.domain.usecase.category

import org.michorga.everythinginone.domain.repository.CategoryRepository

class DeleteCategoryUseCase(private val repository: CategoryRepository) {
    suspend operator fun invoke(id: Long) {
        repository.deleteCategory(id)
    }
}
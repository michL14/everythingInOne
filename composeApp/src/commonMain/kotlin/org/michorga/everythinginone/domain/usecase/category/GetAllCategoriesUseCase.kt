package org.michorga.everythinginone.domain.usecase.category

import kotlinx.coroutines.flow.Flow
import org.michorga.everythinginone.domain.model.Category
import org.michorga.everythinginone.domain.repository.CategoryRepository

class GetAllCategoriesUseCase(private val repository: CategoryRepository) {
    operator fun invoke(): Flow<List<Category>> {
        return repository.getAllCategories()
    }
}

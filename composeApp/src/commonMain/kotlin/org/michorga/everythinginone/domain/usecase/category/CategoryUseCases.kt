package org.michorga.everythinginone.domain.usecase.category

class CategoryUseCases(
    val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    val addCategoryUseCase: AddCategoryUseCase,
    val deleteCategoryUseCase: DeleteCategoryUseCase
)
package org.michorga.everythinginone.domain.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.michorga.everythinginone.data.local.database.AppQueries
import org.michorga.everythinginone.domain.model.Category

interface CategoryRepository {
    fun getAllCategories(): Flow<List<Category>>
    suspend fun insertCategory(name: String)
    suspend fun deleteCategory(id: Long)
}

class CategoryRepositoryImpl(
    private val db: AppQueries
) : CategoryRepository {

    override fun getAllCategories(): Flow<List<Category>> {
        return db.selectAllCategories { id, name ->
            Category(id, name)
        }.asFlow().mapToList(Dispatchers.IO)
    }

    override suspend fun insertCategory(name: String) {
        withContext(Dispatchers.IO) {
            db.insertCategory(name)
        }
    }

    override suspend fun deleteCategory(id: Long) {
        withContext(Dispatchers.IO) {
            db.deleteCategory(id)
        }
    }
}
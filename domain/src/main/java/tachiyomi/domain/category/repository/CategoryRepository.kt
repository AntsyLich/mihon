package tachiyomi.domain.category.repository

import kotlinx.coroutines.flow.Flow
import tachiyomi.domain.category.model.Category
import tachiyomi.domain.category.model.CategoryUpdate

interface CategoryRepository {

    suspend fun get(id: Long): Category?

    suspend fun getAll(): List<Category>

    fun getAllAsFlow(): Flow<List<Category>>

    suspend fun getAllForManga(mangaId: Long): List<Category>

    fun getAllForMangaAsFlow(mangaId: Long): Flow<List<Category>>

    suspend fun insert(category: Category)

    suspend fun insert(categories: List<Category>)

    suspend fun updatePartial(update: CategoryUpdate)

    suspend fun updatePartial(updates: List<CategoryUpdate>)

    suspend fun updateFlagsForAll(flags: Long)

    suspend fun delete(id: Long)
}

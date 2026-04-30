package tachiyomi.data.category

import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.async.coroutines.awaitAsOneOrNull
import kotlinx.coroutines.flow.Flow
import tachiyomi.data.Database
import tachiyomi.data.subscribeToList
import tachiyomi.domain.category.model.Category
import tachiyomi.domain.category.model.CategoryUpdate
import tachiyomi.domain.category.repository.CategoryRepository

class CategoryRepositoryImpl(
    private val database: Database,
) : CategoryRepository {

    override suspend fun get(id: Long): Category? {
        return database.categoryQueries
            .get(id, ::mapCategory)
            .awaitAsOneOrNull()
    }

    override suspend fun getAll(): List<Category> {
        return database.categoryQueries
            .getAll(::mapCategory)
            .awaitAsList()
    }

    override fun getAllAsFlow(): Flow<List<Category>> {
        return database.categoryQueries
            .getAll(::mapCategory)
            .subscribeToList()
    }

    override suspend fun getAllForManga(mangaId: Long): List<Category> {
        return database.categoryQueries
            .getAllForManga(mangaId, ::mapCategory)
            .awaitAsList()
    }

    override fun getAllForMangaAsFlow(mangaId: Long): Flow<List<Category>> {
        return database.categoryQueries
            .getAllForManga(mangaId, ::mapCategory)
            .subscribeToList()
    }

    override suspend fun insert(category: Category) {
        database.categoryQueries.insert(
            name = category.name,
            order = category.order,
            flags = category.flags,
        )
    }

    override suspend fun insert(categories: List<Category>) {
        database.transaction {
            categories.forEach { insert(it) }
        }
    }

    override suspend fun updatePartial(update: CategoryUpdate) {
        database.categoryQueries.update(
            id = update.id,
            name = update.name,
            order = update.order,
            flags = update.flags,
        )
    }

    override suspend fun updatePartial(updates: List<CategoryUpdate>) {
        database.transaction {
            updates.forEach { updatePartial(it) }
        }
    }

    override suspend fun updateFlagsForAll(flags: Long) {
        database.categoryQueries.updateFlagsForAll(flags)
    }

    override suspend fun delete(id: Long) {
        database.categoryQueries.delete(id = id)
    }

    private fun mapCategory(
        id: Long,
        name: String,
        order: Long,
        flags: Long,
    ): Category {
        return Category(
            id = id,
            name = name,
            order = order,
            flags = flags,
        )
    }
}

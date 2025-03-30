package tachiyomi.data.source

import kotlinx.coroutines.flow.Flow
import tachiyomi.data.DatabaseHandler
import tachiyomi.domain.source.model.StubSource
import tachiyomi.domain.source.repository.StubSourceRepository

class StubSourceRepositoryImpl(
    private val handler: DatabaseHandler,
) : StubSourceRepository {

    override fun subscribeAll(): Flow<List<StubSource>> {
        return handler.subscribeToList {
            sourceQueries.getAll { id, name, language ->
                StubSource(id = id, lang = language, name = name)
            }
        }
    }

    override suspend fun upsert(id: Long, name: String, language: String) {
        handler.await { sourceQueries.upsert(id, name, name) }
    }
}

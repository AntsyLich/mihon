package tachiyomi.data.chapter

import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.async.coroutines.awaitAsOne
import app.cash.sqldelight.async.coroutines.awaitAsOneOrNull
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import logcat.LogPriority
import tachiyomi.core.common.util.lang.toLong
import tachiyomi.core.common.util.system.logcat
import tachiyomi.data.Database
import tachiyomi.data.subscribeToList
import tachiyomi.domain.chapter.model.Chapter
import tachiyomi.domain.chapter.model.ChapterUpdate
import tachiyomi.domain.chapter.repository.ChapterRepository

class ChapterRepositoryImpl(
    private val database: Database,
) : ChapterRepository {

    override suspend fun addAll(chapters: List<Chapter>): List<Chapter> {
        return try {
            database.transactionWithResult {
                chapters.map { chapter ->
                    val lastInsertId = database.chaptersQueries.insert(
                        chapter.mangaId,
                        chapter.url,
                        chapter.name,
                        chapter.scanlator,
                        chapter.read,
                        chapter.bookmark,
                        chapter.lastPageRead,
                        chapter.chapterNumber,
                        chapter.sourceOrder,
                        chapter.dateFetch,
                        chapter.dateUpload,
                        chapter.version,
                    ).awaitAsOne()
                    chapter.copy(id = lastInsertId)
                }
            }
        } catch (e: Exception) {
            logcat(LogPriority.ERROR, e)
            emptyList()
        }
    }

    override suspend fun update(chapterUpdate: ChapterUpdate) {
        partialUpdate(chapterUpdate)
    }

    override suspend fun updateAll(chapterUpdates: List<ChapterUpdate>) {
        partialUpdate(*chapterUpdates.toTypedArray())
    }

    private suspend fun partialUpdate(vararg chapterUpdates: ChapterUpdate) {
        database.transaction {
            chapterUpdates.forEach { chapterUpdate ->
                database.chapterQueries.partialUpdate(
                    id = chapterUpdate.id,
                    read = chapterUpdate.read,
                    bookmark = chapterUpdate.bookmark,
                    lastPageRead = chapterUpdate.lastPageRead,
                    dateFetch = chapterUpdate.dateFetch,
                )
            }
        }
    }

    override suspend fun removeChaptersWithIds(chapterIds: List<Long>) {
        try {
            database.chapterQueries.delete(chapterIds)
        } catch (e: Exception) {
            logcat(LogPriority.ERROR, e)
        }
    }

    override suspend fun getChapterByMangaId(mangaId: Long, applyScanlatorFilter: Boolean): List<Chapter> {
        return database.chapterQueries
            .getChaptersForManga(mangaId, applyScanlatorFilter.toLong(), ::mapChapter)
            .awaitAsList()
    }

    override suspend fun getScanlatorsByMangaId(mangaId: Long): List<String> {
        return database.chapterQueries
            .getScanlatorsForManga(mangaId) { it.orEmpty() }
            .awaitAsList()
            .filter(String::isNotEmpty)
    }

    override fun getScanlatorsByMangaIdAsFlow(mangaId: Long): Flow<List<String>> {
        return database.chapterQueries
            .getScanlatorsForManga(mangaId) { it.orEmpty() }
            .subscribeToList()
            .map { it.filter(String::isNotEmpty) }
    }

    override suspend fun getBookmarkedChaptersByMangaId(mangaId: Long): List<Chapter> {
        return database.chapterQueries
            .getBookmarkedChaptersForManga(mangaId, ::mapChapter)
            .awaitAsList()
    }

    override suspend fun getChapterById(id: Long): Chapter? {
        return database.chapterQueries
            .get(id, ::mapChapter)
            .awaitAsOneOrNull()
    }

    override suspend fun getChapterByMangaIdAsFlow(mangaId: Long, applyScanlatorFilter: Boolean): Flow<List<Chapter>> {
        return database.chapterQueries
            .getChaptersForManga(mangaId, applyScanlatorFilter.toLong(), ::mapChapter)
            .subscribeToList()
    }

    override suspend fun getChapterByUrlAndMangaId(url: String, mangaId: Long): Chapter? {
        return database.chapterQueries
            .getChapterForMangaByUrl(url, mangaId, ::mapChapter)
            .awaitAsOneOrNull()
    }

    @Suppress("UNUSED_PARAMETER")
    private fun mapChapter(
        id: Long,
        mangaId: Long,
        url: String,
        name: String,
        chapterNumber: Double,
        scanlator: String?,
        dateUpload: Long,
        updatedAt: Long,
        read: Boolean,
        bookmark: Boolean,
        lastPageRead: Long,
        sourceOrder: Long,
        dateFetch: Long,
    ): Chapter = Chapter(
        id = id,
        mangaId = mangaId,
        read = read,
        bookmark = bookmark,
        lastPageRead = lastPageRead,
        dateFetch = dateFetch,
        sourceOrder = sourceOrder,
        url = url,
        name = name,
        dateUpload = dateUpload,
        chapterNumber = chapterNumber,
        scanlator = scanlator,
        lastModifiedAt = 0,
        version = 0,
    )
}

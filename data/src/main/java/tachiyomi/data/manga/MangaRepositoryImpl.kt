package tachiyomi.data.manga

import kotlinx.coroutines.flow.Flow
import logcat.LogPriority
import tachiyomi.core.common.util.system.logcat
import tachiyomi.data.DatabaseHandler
import tachiyomi.data.StringListColumnAdapter
import tachiyomi.data.UpdateStrategyColumnAdapter
import tachiyomi.domain.library.model.LibraryManga
import tachiyomi.domain.manga.model.Manga
import tachiyomi.domain.manga.model.MangaUpdate
import tachiyomi.domain.manga.repository.MangaRepository
import java.time.LocalDate
import java.time.ZoneId

class MangaRepositoryImpl(
    private val handler: DatabaseHandler,
) : MangaRepository {

    override suspend fun getMangaById(id: Long): Manga {
        return handler.awaitOne { mangaQueries.get(id, MangaMapper::mapManga) }
    }

    override suspend fun getMangaByIdAsFlow(id: Long): Flow<Manga> {
        return handler.subscribeToOne { mangaQueries.get(id, MangaMapper::mapManga) }
    }

    override suspend fun getMangaByUrlAndSourceId(url: String, sourceId: Long): Manga? {
        return handler.awaitOneOrNull {
            mangaQueries.getForSourceAndUrl(
                sourceId,
                url,
                MangaMapper::mapManga,
            )
        }
    }

    override fun getMangaByUrlAndSourceIdAsFlow(url: String, sourceId: Long): Flow<Manga?> {
        return handler.subscribeToOneOrNull {
            mangaQueries.getForSourceAndUrl(
                sourceId,
                url,
                MangaMapper::mapManga,
            )
        }
    }

    override suspend fun getFavorites(): List<Manga> {
        return handler.awaitList { mangaQueries.getFavorites(MangaMapper::mapManga) }
    }

    override suspend fun getReadMangaNotInLibrary(): List<Manga> {
        return handler.awaitList { mangaQueries.getNonLibraryReadManga(MangaMapper::mapManga) }
    }

    override suspend fun getLibraryManga(): List<LibraryManga> {
        return handler.awaitList { libraryViewQueries.library(MangaMapper::mapLibraryManga) }
    }

    override fun getLibraryMangaAsFlow(): Flow<List<LibraryManga>> {
        return handler.subscribeToList { libraryViewQueries.library(MangaMapper::mapLibraryManga) }
    }

    override fun getFavoritesBySourceId(sourceId: Long): Flow<List<Manga>> {
        return handler.subscribeToList { mangaQueries.getLibraryMangaBySource(sourceId, MangaMapper::mapManga) }
    }

    override suspend fun getDuplicateLibraryManga(id: Long, title: String): List<Manga> {
        return handler.awaitList {
            mangaQueries.getDuplicateLibraryManga(title, id, MangaMapper::mapManga)
        }
    }

    override suspend fun getUpcomingManga(statuses: Set<Long>): Flow<List<Manga>> {
        val epochMillis = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000
        return handler.subscribeToList {
            mangaQueries.getLibraryMangaWithUpcomingChapterUpdate(epochMillis, statuses, MangaMapper::mapManga)
        }
    }

    override suspend fun resetViewerFlags(): Boolean {
        return try {
            handler.await { mangaQueries.resetReaderFlags() }
            true
        } catch (e: Exception) {
            logcat(LogPriority.ERROR, e)
            false
        }
    }

    override suspend fun setMangaCategories(mangaId: Long, categoryIds: List<Long>) {
        handler.await(inTransaction = true) {
            manga_categoryQueries.deleteExcludedMangaCategories(mangaId, categoryIds)
            categoryIds.map { manga_categoryQueries.insert(mangaId, it) }
        }
    }

    override suspend fun insert(manga: Manga): Long? {
        return handler.awaitOneOrNullExecutable(inTransaction = true) {
            mangaQueries.insert(
                sourceId = manga.source,
                sourceUrl = manga.url,
                sourceArtist = manga.artist,
                sourceAuthor = manga.author,
                sourceDescription = manga.description,
                sourceGenre = manga.genre,
                sourceTitle = manga.title,
                sourceStatus = manga.status,
                sourceCover = manga.thumbnailUrl,
                userFavorite = manga.favorite,
                chapterLastUpdate = manga.lastUpdate,
                chapterNextUpdate = manga.nextUpdate,
                calculateInterval = manga.fetchInterval.toLong(),
                initialized = manga.initialized,
                userReaderFlags = manga.viewerFlags,
                userChapterFlags = manga.chapterFlags,
                coverLastModified = manga.coverLastModified,
                dateAdded = manga.dateAdded,
                sourceUpdateStrategy = manga.updateStrategy,
                userNotes = manga.notes,
            )
            mangaQueries.selectLastInsertedRowId()
        }
    }

    override suspend fun update(update: MangaUpdate): Boolean {
        return try {
            partialUpdate(update)
            true
        } catch (e: Exception) {
            logcat(LogPriority.ERROR, e)
            false
        }
    }

    override suspend fun updateAll(mangaUpdates: List<MangaUpdate>): Boolean {
        return try {
            partialUpdate(*mangaUpdates.toTypedArray())
            true
        } catch (e: Exception) {
            logcat(LogPriority.ERROR, e)
            false
        }
    }

    private suspend fun partialUpdate(vararg mangaUpdates: MangaUpdate) {
        handler.await(inTransaction = true) {
            mangaUpdates.forEach { value ->
                mangaQueries.update(
                    sourceId = value.source,
                    sourceUrl = value.url,
                    sourceArtist = value.artist,
                    sourceAuthor = value.author,
                    sourceDescription = value.description,
                    sourceGenre = value.genre?.let(StringListColumnAdapter::encode),
                    sourceTitle = value.title,
                    sourceStatus = value.status,
                    sourceCover = value.thumbnailUrl,
                    userFavorite = value.favorite,
                    chapterLastUpdate = value.lastUpdate,
                    chapterNextUpdate = value.nextUpdate,
                    calculateInterval = value.fetchInterval?.toLong(),
                    initialized = value.initialized,
                    userReaderFlags = value.viewerFlags,
                    userChapterFlags = value.chapterFlags,
                    coverLastModified = value.coverLastModified,
                    dateAdded = value.dateAdded,
                    mangaId = value.id,
                    sourceUpdateStrategy = value.updateStrategy?.let(UpdateStrategyColumnAdapter::encode),
                    userNotes = value.notes,
                )
            }
        }
    }
}

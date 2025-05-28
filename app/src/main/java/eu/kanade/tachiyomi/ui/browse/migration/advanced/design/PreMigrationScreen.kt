package eu.kanade.tachiyomi.ui.browse.migration.advanced.design

import java.io.Serializable

sealed class MigrationType : Serializable {
    data class MangaList(val mangaIds: List<Long>) : MigrationType()
    data class MangaSingle(val fromMangaId: Long, val toManga: Long?) : MigrationType()
}

package eu.kanade.tachiyomi.ui.browse.migration.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import eu.kanade.presentation.browse.MigrateSearchScreen
import eu.kanade.presentation.util.Screen
import mihon.feature.migration.list.MigrateMangaListScreen
import eu.kanade.tachiyomi.ui.manga.MangaScreen
import mihon.feature.migration.dialog.MigrateMangaDialog

class MigrateSearchScreen(private val mangaId: Long) : Screen() {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel =
            rememberScreenModel { MigrateSearchScreenModel(mangaId = mangaId) }
        val state by screenModel.state.collectAsState()

        val dialogScreenModel = rememberScreenModel { MigrateSearchScreenDialogScreenModel(mangaId = mangaId) }
        val dialogState by dialogScreenModel.state.collectAsState()

        MigrateSearchScreen(
            state = state,
            fromSourceId = state.fromSourceId,
            navigateUp = navigator::pop,
            onChangeSearchQuery = screenModel::updateSearchQuery,
            onSearch = { screenModel.search() },
            getManga = { screenModel.getManga(it) },
            onChangeSearchFilter = screenModel::setSourceFilter,
            onToggleResults = screenModel::toggleFilterResults,
            onClickSource = {
                navigator.push(SourceSearchScreen(dialogState.manga!!, it.id, state.searchQuery))
            },
            onClickItem = {
                navigator.items
                    .filterIsInstance<MigrateMangaListScreen>()
                    .last()
                    .newSelectedItem = mangaId to it.id
                navigator.popUntil { it is MigrateMangaListScreen }
            },
            onLongClickItem = { navigator.push(MangaScreen(it.id, true)) },
        )

        when (val dialog = dialogState.dialog) {
            is MigrateSearchScreenDialogScreenModel.Dialog.Migrate -> {
                MigrateMangaDialog(
                    current = dialogState.manga!!,
                    target = dialog.manga,
                    onClickTitle = {
                        navigator.push(MangaScreen(dialog.manga.id, true))
                    },
                    onDismissRequest = { dialogScreenModel.setDialog(null) },
                    onComplete = {
                        if (navigator.lastItem is MangaScreen) {
                            val lastItem = navigator.lastItem
                            navigator.popUntil { navigator.items.contains(lastItem) }
                            navigator.push(MangaScreen(dialog.manga.id))
                        } else {
                            navigator.replace(MangaScreen(dialog.manga.id))
                        }
                    },
                )
            }
            else -> {}
        }
    }
}

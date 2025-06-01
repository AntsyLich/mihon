package mihon.feature.migration.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.CopyAll
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.DoneAll
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import eu.kanade.presentation.components.AppBar
import eu.kanade.presentation.components.AppBarActions
import eu.kanade.presentation.util.animateItemFastScroll
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import mihon.feature.migration.list.components.MigrationItem
import mihon.feature.migration.list.components.MigrationItemResult
import mihon.feature.migration.list.models.MigratingManga
import tachiyomi.core.common.util.lang.withIOContext
import tachiyomi.domain.manga.model.Manga
import tachiyomi.i18n.MR
import tachiyomi.presentation.core.components.ScrollbarLazyColumn
import tachiyomi.presentation.core.components.material.Scaffold
import tachiyomi.presentation.core.components.material.topSmallPaddingValues
import tachiyomi.presentation.core.i18n.stringResource
import tachiyomi.presentation.core.util.plus

@Composable
fun MigrationListScreenContent(
    items: ImmutableList<MigratingManga>,
    migrationDone: Boolean,
    unfinishedCount: Int,
    getManga: suspend (MigratingManga.SearchResult.Result) -> Manga?,
    getChapterInfo: suspend (MigratingManga.SearchResult.Result) -> MigratingManga.ChapterInfo,
    getSourceName: (Manga) -> String,
    onMigrationItemClick: (Manga) -> Unit,
    openMigrationDialog: (Boolean) -> Unit,
    skipManga: (Long) -> Unit,
    searchManually: (MigratingManga) -> Unit,
    migrateNow: (Long) -> Unit,
    copyNow: (Long) -> Unit,
) {
    Scaffold(
        topBar = { scrollBehavior ->
            val titleString = stringResource(MR.strings.migration)
            val title by produceState(initialValue = titleString, items, unfinishedCount, titleString) {
                withIOContext {
                    value = "$titleString ($unfinishedCount/${items.size})"
                }
            }
            AppBar(
                title = title,
                actions = {
                    AppBarActions(
                        persistentListOf(
                            AppBar.Action(
                                title = stringResource(MR.strings.copy),
                                icon = if (items.size == 1) Icons.Outlined.ContentCopy else Icons.Outlined.CopyAll,
                                onClick = { openMigrationDialog(false) },
                                enabled = migrationDone,
                            ),
                            AppBar.Action(
                                title = stringResource(MR.strings.migrate),
                                icon = if (items.size == 1) Icons.Outlined.Done else Icons.Outlined.DoneAll,
                                onClick = { openMigrationDialog(false) },
                                enabled = migrationDone,
                            ),
                        ),
                    )
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { contentPadding ->
        ScrollbarLazyColumn(
            contentPadding = contentPadding + topSmallPaddingValues,
        ) {
            items(items, key = { it.manga.id }) { migrationItem ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .animateItemFastScroll()
                        .padding(horizontal = 16.dp)
                        .height(IntrinsicSize.Min),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    val result by migrationItem.searchResult.collectAsState()
                    MigrationItem(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .weight(1f)
                            .align(Alignment.Top)
                            .fillMaxHeight(),
                        manga = migrationItem.manga,
                        sourcesString = migrationItem.sourcesString,
                        chapterInfo = migrationItem.chapterInfo,
                        onClick = { onMigrationItemClick(migrationItem.manga) },
                    )

                    Icon(
                        Icons.AutoMirrored.Outlined.ArrowForward,
                        contentDescription = stringResource(MR.strings.migrating_to),
                        modifier = Modifier.weight(0.2f),
                    )

                    MigrationItemResult(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .weight(1f)
                            .align(Alignment.Top)
                            .fillMaxHeight(),
                        migrationItem = migrationItem,
                        result = result,
                        getManga = getManga,
                        getChapterInfo = getChapterInfo,
                        getSourceName = getSourceName,
                        onMigrationItemClick = onMigrationItemClick,
                    )

                    MigrationActionIcon(
                        modifier = Modifier
                            .weight(0.2f),
                        result = result,
                        skipManga = { skipManga(migrationItem.manga.id) },
                        searchManually = { searchManually(migrationItem) },
                        migrateNow = {
                            migrateNow(migrationItem.manga.id)
                        },
                        copyNow = {
                            copyNow(migrationItem.manga.id)
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun MigrationActionIcon(
    modifier: Modifier,
    result: MigratingManga.SearchResult,
    skipManga: () -> Unit,
    searchManually: () -> Unit,
    migrateNow: () -> Unit,
    copyNow: () -> Unit,
) {
    var moreExpanded by remember { mutableStateOf(false) }
    val closeMenu = { moreExpanded = false }

    Box(modifier) {
        if (result is MigratingManga.SearchResult.Searching) {
            IconButton(onClick = skipManga) {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = stringResource(MR.strings.action_stop),
                )
            }
        } else if (result is MigratingManga.SearchResult.Result || result is MigratingManga.SearchResult.NotFound) {
            IconButton(onClick = { moreExpanded = !moreExpanded }) {
                Icon(
                    imageVector = Icons.Outlined.MoreVert,
                    contentDescription = stringResource(MR.strings.action_menu_overflow_description),
                )
            }
            DropdownMenu(
                expanded = moreExpanded,
                onDismissRequest = closeMenu,
                offset = DpOffset(8.dp, (-56).dp),
            ) {
                DropdownMenuItem(
                    text = { Text(stringResource(MR.strings.action_search_manually)) },
                    onClick = {
                        searchManually()
                        closeMenu()
                    },
                )
                DropdownMenuItem(
                    text = { Text(stringResource(MR.strings.action_skip_entry)) },
                    onClick = {
                        skipManga()
                        closeMenu()
                    },
                )
                if (result is MigratingManga.SearchResult.Result) {
                    DropdownMenuItem(
                        text = { Text(stringResource(MR.strings.action_migrate_now)) },
                        onClick = {
                            migrateNow()
                            closeMenu()
                        },
                    )
                    DropdownMenuItem(
                        text = { Text(stringResource(MR.strings.action_copy_now)) },
                        onClick = {
                            copyNow()
                            closeMenu()
                        },
                    )
                }
            }
        }
    }
}

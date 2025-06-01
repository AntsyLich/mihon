package mihon.feature.migration.config

import android.view.LayoutInflater
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.isVisible
import eu.kanade.domain.source.service.SourcePreferences
import eu.kanade.presentation.components.AdaptiveSheet
import eu.kanade.tachiyomi.databinding.MigrationBottomSheetBinding
import eu.kanade.tachiyomi.util.system.toast
import mihon.domain.migration.models.MigrationFlag
import tachiyomi.core.common.preference.Preference
import tachiyomi.core.common.util.lang.toLong
import tachiyomi.i18n.MR
import uy.kohesive.injekt.injectLazy

@Composable
fun MigrationConfigScreenSheet(
    onDismissRequest: () -> Unit,
    onStartMigration: (extraParam: String?) -> Unit,
) {
    val startMigration = rememberUpdatedState(onStartMigration)
    val state = remember {
        MigrationConfigScreenSheetState(startMigration)
    }
    AdaptiveSheet(onDismissRequest = onDismissRequest) {
        AndroidView(
            factory = { factoryContext ->
                val binding = MigrationBottomSheetBinding.inflate(LayoutInflater.from(factoryContext))
                state.initPreferences(binding)
                binding.root
            },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

private class MigrationConfigScreenSheetState(private val onStartMigration: State<(extraParam: String?) -> Unit>) {
    private val preferences: SourcePreferences by injectLazy()

    /**
     * Init general reader preferences.
     */
    fun initPreferences(binding: MigrationBottomSheetBinding) {
        val flags = preferences.migrationFlags().get()

        binding.migChapters.isChecked = MigrationFlag.CHAPTER in flags
        binding.migCategories.isChecked = MigrationFlag.CATEGORY in flags
        binding.migCustomCover.isChecked = MigrationFlag.CUSTOM_COVER in flags
        binding.migDeleteDownloaded.isChecked = MigrationFlag.REMOVE_DOWNLOAD in flags
        binding.migNotes.isChecked = MigrationFlag.NOTES in flags

        binding.migChapters.setOnCheckedChangeListener { _, _ -> setFlags(binding) }
        binding.migCategories.setOnCheckedChangeListener { _, _ -> setFlags(binding) }
        binding.migTracking.setOnCheckedChangeListener { _, _ -> setFlags(binding) }
        binding.migCustomCover.setOnCheckedChangeListener { _, _ -> setFlags(binding) }
        binding.migExtra.setOnCheckedChangeListener { _, _ -> setFlags(binding) }
        binding.migDeleteDownloaded.setOnCheckedChangeListener { _, _ -> setFlags(binding) }
        binding.migNotes.setOnCheckedChangeListener { _, _ -> setFlags(binding) }

        binding.useSmartSearch.bindToPreference(preferences.smartMigration())
        binding.extraSearchParamText.isVisible = false
        binding.extraSearchParam.setOnCheckedChangeListener { _, isChecked ->
            binding.extraSearchParamText.isVisible = isChecked
        }
        binding.sourceGroup.bindToPreference(preferences.useSourceWithMost())

        binding.HideNotFoundManga.isChecked = preferences.hideNotFoundMigration().get()
        binding.OnlyShowUpdates.isChecked = preferences.showOnlyUpdatesMigration().get()

        binding.migrateBtn.setOnClickListener {
            preferences.hideNotFoundMigration().set(binding.HideNotFoundManga.isChecked)
            preferences.showOnlyUpdatesMigration().set(binding.OnlyShowUpdates.isChecked)
            onStartMigration.value(
                if (binding.useSmartSearch.isChecked && binding.extraSearchParamText.text.isNotBlank()) {
                    binding.extraSearchParamText.toString()
                } else {
                    null
                },
            )
        }
    }

    private fun setFlags(binding: MigrationBottomSheetBinding) {
        val flags = buildSet {
            if (binding.migChapters.isChecked) add(MigrationFlag.CHAPTER)
            if (binding.migCategories.isChecked) add(MigrationFlag.CATEGORY)
            if (binding.migCustomCover.isChecked) add(MigrationFlag.CUSTOM_COVER)
            if (binding.migDeleteDownloaded.isChecked) add(MigrationFlag.REMOVE_DOWNLOAD)
            if (binding.migNotes.isChecked) add(MigrationFlag.NOTES)
        }
        preferences.migrationFlags().set(flags)
    }

    /**
     * Binds a checkbox or switch view with a boolean preference.
     */
    private fun CompoundButton.bindToPreference(pref: Preference<Boolean>) {
        isChecked = pref.get()
        setOnCheckedChangeListener { _, isChecked -> pref.set(isChecked) }
    }

    /**
     * Binds a radio group with a boolean preference.
     */
    private fun RadioGroup.bindToPreference(pref: Preference<Boolean>) {
        (getChildAt(pref.get().toLong().toInt()) as RadioButton).isChecked = true
        setOnCheckedChangeListener { _, value ->
            val index = indexOfChild(findViewById(value))
            pref.set(index == 1)
        }
    }
}

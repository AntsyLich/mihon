package mihon.feature.migration.list.models

import java.io.Serializable

data class MigrationProcedureConfig(
    var migration: MigrationType,
    val extraSearchParams: String?,
) : Serializable

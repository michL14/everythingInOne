package org.michorga.everythinginone.data.local.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver


actual class DatabaseDriverFactory actual constructor(private val context: Any) {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            AppDatabase.Schema,
            "app.db"
        )
    }
}

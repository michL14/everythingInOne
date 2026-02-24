package org.michorga.everythinginone.data.local.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver


actual class DatabaseDriverFactory actual constructor(private val context: Any) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            AppDatabase.Schema,
            context as Context,
            "app.db"
        )
    }
}
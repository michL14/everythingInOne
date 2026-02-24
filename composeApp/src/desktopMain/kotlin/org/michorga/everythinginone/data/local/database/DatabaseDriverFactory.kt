package org.michorga.everythinginone.data.local.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver

actual class DatabaseDriverFactory actual constructor(private val context: Any) {
    actual fun createDriver(): SqlDriver {
        val driver = JdbcSqliteDriver("jdbc:sqlite:app.db")
        AppDatabase.Schema.create(driver) // Crée les tables si besoin
        return driver
    }
}
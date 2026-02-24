package org.michorga.everythinginone.data.local.database

import app.cash.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory(context: Any) {
    fun createDriver(): SqlDriver
}
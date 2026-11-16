package org.michorga.everythinginone.data.local.database

import app.cash.sqldelight.async.coroutines.awaitCreate
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.worker.WebWorkerDriver
import org.w3c.dom.Worker

actual class DatabaseDriverFactory actual constructor(private val context: Any) {
    actual fun createDriver(): SqlDriver = WebWorkerDriver(Worker("sqlite.worker.js"))
}

/** Crée le schéma uniquement si la base (restaurée depuis IndexedDB) est vide. */
suspend fun SqlDriver.ensureSchema() {
    val hasSchema = executeQuery(
        identifier = null,
        sql = "SELECT name FROM sqlite_master WHERE type = 'table' AND name = 'categoryEntity'",
        mapper = { cursor -> app.cash.sqldelight.db.QueryResult.Value(cursor.next().value) },
        parameters = 0,
    ).await()
    if (!hasSchema) AppDatabase.Schema.awaitCreate(this)
}

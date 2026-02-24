package org.michorga.everythinginone.data.local

import org.koin.dsl.module
import org.michorga.everythinginone.data.local.database.DatabaseDriverFactory

val desktopModule = module {
    single { DatabaseDriverFactory(Any()).createDriver() }
}
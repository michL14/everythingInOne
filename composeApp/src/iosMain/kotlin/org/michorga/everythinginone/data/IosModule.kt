package org.michorga.everythinginone.data

import org.koin.dsl.module
import org.michorga.everythinginone.data.local.database.DatabaseDriverFactory

val iosModule = module {
    single { DatabaseDriverFactory(Any()).createDriver() }
}
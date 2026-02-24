package org.michorga.everythinginone

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
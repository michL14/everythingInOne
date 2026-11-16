package org.michorga.everythinginone.presentation.payment.model

enum class Month(
    val fullName: String,
    val shortName: String,
    val monthNumber: Int
) {
    JANUARY("January", "Jan", 1),
    FEBRUARY("February", "Feb", 2),
    MARCH("March", "Mar", 3),
    APRIL("April", "Apr", 4),
    MAY("May", "May", 5),
    JUNE("June", "Jun", 6),
    JULY("July", "Jul", 7),
    AUGUST("August", "Aug", 8),
    SEPTEMBER("September", "Sep", 9),
    OCTOBER("October", "Oct", 10),
    NOVEMBER("November", "Nov", 11),
    DECEMBER("December", "Dec", 12);

    companion object {
        // 🔹 Listes utiles
        val fullNames = entries.map { it.fullName }
        val shortNames = entries.map { it.shortName }
        val numbers = entries.map { it.monthNumber }

        // 🔹 Maps pour lookup rapide
        private val byNumber = entries.associateBy { it.monthNumber }
        private val byShortName = entries.associateBy { it.shortName.lowercase() }
        private val byFullName = entries.associateBy { it.fullName.lowercase() }

        // 🔹 From methods
        fun fromNumber(number: Int): Month = byNumber[number] ?: error("Invalid month number: $number")

        fun fromShortName(shortName: String): Month =
            byShortName[shortName.lowercase()] ?: error("Invalid month")

        fun fromFullName(fullName: String): Month =
            byFullName[fullName.lowercase()] ?: error("Invalid month")
    }
}
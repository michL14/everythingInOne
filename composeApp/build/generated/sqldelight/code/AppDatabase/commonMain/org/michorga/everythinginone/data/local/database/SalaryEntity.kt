package org.michorga.everythinginone.`data`.local.database

import kotlin.Double
import kotlin.Long
import kotlin.String

public data class SalaryEntity(
  public val id: Long,
  public val amount: Double,
  public val yearMonth: String,
  public val isRecurring: Long,
)

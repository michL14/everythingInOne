package org.michorga.everythinginone.`data`.local.database

import kotlin.Double
import kotlin.Long
import kotlin.String

public data class SalaryConfig(
  public val id: Long,
  public val recurringAmount: Double,
  public val lastAppliedYearMonth: String?,
)

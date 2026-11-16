package org.michorga.everythinginone.`data`.local.database

import kotlin.Double
import kotlin.Long
import kotlin.String

public data class SubscriptionEntity(
  public val id: Long,
  public val name: String,
  public val amount: Double,
  public val dayOfMonth: Long,
  public val isActive: Long,
  public val lastAppliedYearMonth: String?,
)

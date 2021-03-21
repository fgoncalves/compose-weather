package com.example.androiddevchallenge.commons

import org.joda.time.DateTime
import org.joda.time.DateTimeZone

data class Epoch(
    val value: Long,
    val offset: Int = 0,
)

fun Long.epochMs(offset: Int = 0) = Epoch(this, offset)

fun Epoch.toDateTime() = DateTime(value, DateTimeZone.forOffsetMillis(offset))

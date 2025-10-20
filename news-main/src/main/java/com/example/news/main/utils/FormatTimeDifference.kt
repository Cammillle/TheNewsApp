package com.example.news.main.utils

import java.util.Date
import java.util.concurrent.TimeUnit
import kotlin.math.abs

internal fun formatTimeDifference(publishedDate: Date, currentDate: Date): String {
    val diffInMillis = currentDate.time - publishedDate.time
    val isFuture = diffInMillis < 0
    val absDiffInMillis = abs(diffInMillis)

    val seconds = TimeUnit.MILLISECONDS.toSeconds(absDiffInMillis)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(absDiffInMillis)
    val hours = TimeUnit.MILLISECONDS.toHours(absDiffInMillis)
    val days = TimeUnit.MILLISECONDS.toDays(absDiffInMillis)
    val weeks = days / 7
    val months = days / 30
    val years = days / 365

    val suffix = if (isFuture) " from now" else " ago"

    return when {
        seconds < 60 -> if (isFuture) "soon" else "just now"
        minutes < 60 -> "${minutes}m$suffix"
        hours < 24 -> "${hours}h$suffix"
        days < 7 -> "${days}d$suffix"
        weeks < 4 -> "${weeks}w$suffix"
        months < 12 -> "${months}mo$suffix"
        else -> "${years}y$suffix"
    }
}
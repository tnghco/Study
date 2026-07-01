package com.example.studygate

import android.content.Context
import java.time.LocalDate

object StudyPrefs {
    private const val PREF = "study_gate"
    private const val KEY_DATE = "date"
    private const val KEY_SECONDS = "seconds"
    private const val KEY_VIDEO_URI = "video_uri"
    const val TARGET_SECONDS = 30

    private fun today(): String = LocalDate.now().toString()

    private fun prefs(context: Context) =
        context.applicationContext.getSharedPreferences(PREF, Context.MODE_PRIVATE)

    private fun ensureToday(context: Context) {
        val p = prefs(context)
        if (p.getString(KEY_DATE, null) != today()) {
            p.edit().putString(KEY_DATE, today()).putInt(KEY_SECONDS, 0).apply()
        }
    }

    fun watchedSeconds(context: Context): Int {
        ensureToday(context)
        return prefs(context).getInt(KEY_SECONDS, 0)
    }

    fun addOneSecond(context: Context) {
        ensureToday(context)
        val next = (watchedSeconds(context) + 1).coerceAtMost(TARGET_SECONDS)
        prefs(context).edit().putInt(KEY_SECONDS, next).apply()
    }

    fun isUnlocked(context: Context): Boolean = watchedSeconds(context) >= TARGET_SECONDS

    fun saveVideoUri(context: Context, uri: String) {
        prefs(context).edit().putString(KEY_VIDEO_URI, uri).apply()
    }

    fun videoUri(context: Context): String? = prefs(context).getString(KEY_VIDEO_URI, null)
}

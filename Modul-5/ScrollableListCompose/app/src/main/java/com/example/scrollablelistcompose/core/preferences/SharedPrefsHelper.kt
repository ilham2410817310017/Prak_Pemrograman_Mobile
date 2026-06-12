package com.example.scrollablelistcompose.core.preferences

import android.content.Context
import android.content.SharedPreferences

class SharedPrefsHelper(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("movie_prefs", Context.MODE_PRIVATE)

    fun saveLastViewedMovie(title: String) {
        prefs.edit().putString("LAST_VIEWED", title).apply()
    }

    fun getLastViewedMovie(): String {
        return prefs.getString("LAST_VIEWED", "Belum ada film yang dilihat") ?: "Belum ada film yang dilihat"
    }
}
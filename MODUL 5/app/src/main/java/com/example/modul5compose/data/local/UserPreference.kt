package com.example.modul5compose.data.local

import android.content.Context
import android.content.SharedPreferences

class UserPreference(context: Context) {
    private val preference: SharedPreferences = context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)

    fun saveLanguage(languageCode: String) {
        preference.edit().putString("SELECTED_LANGUAGE", languageCode).apply()
    }

    fun getLanguage(): String {
        return preference.getString("SELECTED_LANGUAGE", "en") ?: "en"
    }
}
package com.example.modul5compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.material3.*
import androidx.core.os.LocaleListCompat
import com.example.modul5compose.data.local.UserPreference
import com.example.modul5compose.ui.navigation.AppNavigation


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userPreference = UserPreference(this)
        val savedLanguage = userPreference.getLanguage()
        val localeList = LocaleListCompat.forLanguageTags(savedLanguage)
        AppCompatDelegate.setApplicationLocales(localeList)

        setContent {
            MaterialTheme {
                AppNavigation()
            }
        }
    }
}
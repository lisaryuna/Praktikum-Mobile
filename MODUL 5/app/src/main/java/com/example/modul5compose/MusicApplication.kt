package com.example.modul5compose

import android.app.Application
import timber.log.Timber

class MusicApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
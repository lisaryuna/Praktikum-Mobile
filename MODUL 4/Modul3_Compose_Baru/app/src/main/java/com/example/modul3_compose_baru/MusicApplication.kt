package com.example.modul3_compose_baru

import android.app.Application
import timber.log.Timber

class MusicApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
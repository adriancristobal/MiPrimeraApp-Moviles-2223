package com.example.miprimeraapp_moviles_2223.utils

import android.app.Application
import timber.log.Timber
import timber.log.Timber.*


class AppTimber : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(DebugTree())
    }
}
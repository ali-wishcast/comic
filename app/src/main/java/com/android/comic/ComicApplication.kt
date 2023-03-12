package com.android.comic

import android.app.Application
import com.android.comic.core.utils.logging.CrashReportingTree
import com.android.comic.core.utils.logging.SeaPadDebugTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class ComicApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        configureTimber()
    }

    private fun configureTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(SeaPadDebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }
    }
}
package com.fitfit.bannerit

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BannerItApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
package com.moose.clowning

import android.app.Application
import com.google.firebase.FirebaseApp
import com.moose.data.remote.FirebaseService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ClowningApp: Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}
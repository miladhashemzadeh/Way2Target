package com.vampyreworld.w2t

import android.app.Application
import com.vampyreworld.w2t.di.allAppModules
import com.vampyreworld.w2t.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class W2TApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin(allAppModules) {
            androidLogger()
            androidContext(this@W2TApplication)
        }
    }
}

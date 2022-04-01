package net.omisoft.stores

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import net.omisoft.mvptemplate.BuildConfig
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
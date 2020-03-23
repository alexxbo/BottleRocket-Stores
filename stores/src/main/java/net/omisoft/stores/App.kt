package net.omisoft.stores

import android.app.Application
import net.omisoft.mvptemplate.BuildConfig
import net.omisoft.stores.common.di.AppModule
import net.omisoft.stores.common.di.DaggerAppComponent
import timber.log.Timber

class App : Application() {

    val component by lazy {
        DaggerAppComponent.builder()
                .context(this)
                .plus(AppModule())
                .build()
    }

    override fun onCreate() {
        super.onCreate()

        component.inject(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
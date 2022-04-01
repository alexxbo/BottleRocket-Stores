package net.omisoft.stores.common.di

import dagger.BindsInstance
import dagger.Component
import net.omisoft.stores.App
import net.omisoft.stores.common.data.database.StoresDatabase
import net.omisoft.stores.common.rx.RxWorkers
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(target: App)

    fun app(): App

    fun workers(): RxWorkers

    fun database(): StoresDatabase

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(app: App): Builder

        fun plus(module: AppModule): Builder

        fun build(): AppComponent
    }
}
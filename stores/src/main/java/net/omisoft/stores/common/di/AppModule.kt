package net.omisoft.stores.common.di

import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import net.omisoft.stores.App
import net.omisoft.stores.common.rx.RxWorkers
import net.omisoft.stores.database.StoresDatabase
import net.omisoft.stores.database.getDatabase
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun database(application: App): StoresDatabase = getDatabase(application.applicationContext)

    @Provides
    @Singleton
    fun workers() = RxWorkers(Schedulers.io(), AndroidSchedulers.mainThread())
}
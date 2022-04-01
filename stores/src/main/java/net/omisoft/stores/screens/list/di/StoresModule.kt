package net.omisoft.stores.screens.list.di

import dagger.Module
import dagger.Provides
import net.omisoft.stores.common.data.database.StoresDatabase
import net.omisoft.stores.common.di.scope.ActivityScope
import net.omisoft.stores.common.rx.RxWorkers
import net.omisoft.stores.common.util.createService
import net.omisoft.stores.screens.list.StoresPresenter
import net.omisoft.stores.screens.list.api.StoresApi
import net.omisoft.stores.screens.list.data.StoresRepository

@Module
class StoresModule {

    @Provides
    @ActivityScope
    fun api(): StoresApi = createService(StoresApi::class.java)

    @Provides
    @ActivityScope
    fun repository(database: StoresDatabase, api: StoresApi): StoresRepository {
        return StoresRepository(database.storeDao(), api)
    }

    @Provides
    @ActivityScope
    fun presenter(repository: StoresRepository, workers: RxWorkers): StoresPresenter {
        return StoresPresenter(repository, workers)
    }
}
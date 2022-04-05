package net.omisoft.stores.screens.list.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import net.omisoft.stores.common.data.database.StoresDatabase
import net.omisoft.stores.common.util.DispatcherProvider
import net.omisoft.stores.common.util.createService
import net.omisoft.stores.screens.list.api.StoresApi
import net.omisoft.stores.screens.list.data.StoresRepository

@Module
@InstallIn(ActivityRetainedComponent::class)
class StoresModule {

    @Provides
    fun api(): StoresApi = createService(StoresApi::class.java)

    @Provides
    fun repository(
        database: StoresDatabase,
        api: StoresApi,
        dispatcherProvider: DispatcherProvider,
    ): StoresRepository {
        return StoresRepository(database.storeDao(), api, dispatcherProvider)
    }
}
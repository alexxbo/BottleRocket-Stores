package net.omisoft.stores.screens.list.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import io.ktor.client.*
import net.omisoft.stores.common.data.database.StoresDatabase
import net.omisoft.stores.common.util.DispatcherProvider
import net.omisoft.stores.screens.list.api.StoresApi
import net.omisoft.stores.screens.list.data.StoresRepository

@Module
@InstallIn(ActivityRetainedComponent::class)
class StoresModule {

    @Provides
    fun api(client: HttpClient): StoresApi = StoresApi(client)

    @Provides
    fun repository(
        database: StoresDatabase,
        api: StoresApi,
        dispatcherProvider: DispatcherProvider,
    ): StoresRepository {
        return StoresRepository(database.storeDao(), api, dispatcherProvider)
    }
}
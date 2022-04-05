package net.omisoft.stores.screens.list.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.ktor.client.*
import net.omisoft.stores.common.data.storage.StoresStorage
import net.omisoft.stores.common.util.DispatcherProvider
import net.omisoft.stores.screens.list.api.StoresApi
import net.omisoft.stores.screens.list.data.StoresRepository

@Module
@InstallIn(ViewModelComponent::class)
class StoresModule {

    @Provides
    fun api(client: HttpClient): StoresApi = StoresApi(client)

    @Provides
    fun repository(
        storage: StoresStorage,
        api: StoresApi,
        dispatcherProvider: DispatcherProvider,
    ): StoresRepository {
        return StoresRepository(storage, api, dispatcherProvider)
    }
}
package net.omisoft.stores.common.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import kotlinx.coroutines.Dispatchers
import net.omisoft.bottlerocket.BuildConfig
import net.omisoft.stores.common.data.storage.StoresStorage
import net.omisoft.stores.common.data.storage.database.StoresDatabase
import net.omisoft.stores.common.util.DispatcherProvider
import net.omisoft.stores.common.util.createHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun database(@ApplicationContext context: Context): StoresDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            StoresDatabase::class.java,
            BuildConfig.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun storeStorage(database: StoresDatabase) = StoresStorage(database.storeDao())

    @Provides
    @Singleton
    fun client(): HttpClient = createHttpClient()

    @Provides
    @Singleton
    fun dispatchers() = DispatcherProvider(ioDispatcher = Dispatchers.IO, mainDispatcher = Dispatchers.Main)
}
package net.omisoft.stores.screens.detail.di

import dagger.Module
import dagger.Provides
import net.omisoft.stores.common.di.scope.ActivityScope
import net.omisoft.stores.screens.detail.StoreDetailsPresenter

@Module
class StoreDetailsModule {

    @Provides
    @ActivityScope
    fun presenter() = StoreDetailsPresenter()
}
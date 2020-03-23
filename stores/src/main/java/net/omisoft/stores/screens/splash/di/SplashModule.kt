package net.omisoft.stores.screens.splash.di

import dagger.Module
import dagger.Provides
import net.omisoft.stores.common.di.scope.ActivityScope
import net.omisoft.stores.screens.splash.SplashPresenter

@Module
class SplashModule {

    @Provides
    @ActivityScope
    fun presenter() = SplashPresenter()
}
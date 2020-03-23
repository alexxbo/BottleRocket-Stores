package net.omisoft.stores.screens.detail.di

import dagger.BindsInstance
import dagger.Component
import net.omisoft.stores.common.di.AppComponent
import net.omisoft.stores.common.di.scope.ActivityScope
import net.omisoft.stores.screens.detail.StoreDetailsActivity

@ActivityScope
@Component(modules = [StoreDetailsModule::class], dependencies = [AppComponent::class])
interface StoreDetailsComponent {

    fun inject(target: StoreDetailsActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun activity(activity: StoreDetailsActivity): Builder

        fun appComponent(component: AppComponent): Builder

        fun plus(module: StoreDetailsModule): Builder

        fun build(): StoreDetailsComponent
    }
}
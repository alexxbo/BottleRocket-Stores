package net.omisoft.stores.screens.list.di

import dagger.BindsInstance
import dagger.Component
import net.omisoft.stores.common.di.AppComponent
import net.omisoft.stores.common.di.scope.ActivityScope
import net.omisoft.stores.screens.list.StoresActivity

@ActivityScope
@Component(modules = [StoresModule::class], dependencies = [AppComponent::class])
interface StoresComponent {

    fun inject(target: StoresActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun activity(activity: StoresActivity): Builder

        fun appComponent(component: AppComponent): Builder

        fun plus(module: StoresModule): Builder

        fun build(): StoresComponent
    }
}
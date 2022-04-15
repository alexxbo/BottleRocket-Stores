package net.omisoft.stores.screens.list.navigation

import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import net.omisoft.stores.common.arch.Destination
import net.omisoft.stores.common.arch.ViewRouter
import net.omisoft.stores.common.data.model.Store
import net.omisoft.stores.screens.detail.StoreDetailsActivity
import net.omisoft.stores.screens.list.navigation.StoresDestination.StoreDetailsDestination

class StoresNavigation(
    private val activity: Activity,
    private val lifecycleOwner: LifecycleOwner,
) : ViewRouter<StoresDestination>(lifecycleOwner) {

    override fun navigate(destination: StoresDestination) {
        lifecycleOwner.lifecycleScope.launch {
            when (destination) {
                is StoreDetailsDestination -> openStoreDetails(destination.store)
            }
        }
    }

    private fun openStoreDetails(store: Store) {
        StoreDetailsActivity.launch(activity, store)
    }
}

sealed class StoresDestination : Destination {
    data class StoreDetailsDestination(val store: Store) : StoresDestination()
}
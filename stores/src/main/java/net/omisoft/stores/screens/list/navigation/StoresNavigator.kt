package net.omisoft.stores.screens.list.navigation

import net.omisoft.stores.common.arch.Navigator
import net.omisoft.stores.common.data.model.Store

sealed class StoresNavigator : Navigator {
    data class StoreDetailsNavigation(val store: Store) : StoresNavigator()
}
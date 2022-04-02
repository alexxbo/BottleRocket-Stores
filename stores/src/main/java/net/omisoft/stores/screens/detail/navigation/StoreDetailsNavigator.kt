package net.omisoft.stores.screens.detail.navigation

import net.omisoft.stores.common.arch.Navigator

sealed class StoreDetailsNavigator : Navigator {
    data class MapNavigation(val location: String) : StoreDetailsNavigator()
    object BackNavigation : StoreDetailsNavigator()
}

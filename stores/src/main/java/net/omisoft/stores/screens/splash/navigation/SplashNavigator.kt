package net.omisoft.stores.screens.splash.navigation

import net.omisoft.stores.common.arch.Navigator

sealed class SplashNavigator : Navigator {
    object ContentScreenNavigation : SplashNavigator()
}
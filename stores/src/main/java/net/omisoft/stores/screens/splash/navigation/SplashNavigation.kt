package net.omisoft.stores.screens.splash.navigation

import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import net.omisoft.stores.common.arch.Destination
import net.omisoft.stores.common.arch.ViewRouter
import net.omisoft.stores.screens.list.StoresActivity
import net.omisoft.stores.screens.splash.navigation.SplashDestination.ContentScreenDestination

class SplashNavigation(
    private val activity: Activity,
    private val lifecycleOwner: LifecycleOwner,
) : ViewRouter<SplashDestination>(lifecycleOwner) {

    override fun navigate(destination: SplashDestination) {
        lifecycleOwner.lifecycleScope.launch {
            when (destination) {
                is ContentScreenDestination -> openContentScreen()
            }
        }
    }

    private fun openContentScreen() {
        StoresActivity.launch(activity)
    }
}

sealed class SplashDestination : Destination {
    object ContentScreenDestination : SplashDestination()
}
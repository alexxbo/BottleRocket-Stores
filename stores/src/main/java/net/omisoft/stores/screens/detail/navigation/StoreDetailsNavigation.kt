package net.omisoft.stores.screens.detail.navigation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import net.omisoft.stores.common.arch.Destination
import net.omisoft.stores.common.arch.Navigation

class StoreDetailsNavigation(
    private val activity: Activity,
    private val lifecycleOwner: LifecycleOwner,
) : Navigation<StoreDetailsDestination>(lifecycleOwner) {

    companion object {
        private const val MAP_PACKAGE = "com.google.android.apps.maps"
    }

    override fun navigate(destination: StoreDetailsDestination) {
        lifecycleOwner.lifecycleScope.launch {
            when (destination) {
                is StoreDetailsDestination.MapDestination -> showOnMap(destination.location)
                is StoreDetailsDestination.BackNavigation -> goBack()
            }
        }
    }

    private fun showOnMap(locationData: String) {
        val gmmIntentUri: Uri = Uri.parse(locationData)
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage(MAP_PACKAGE)
        activity.startActivity(mapIntent)
    }

    private fun goBack() {
        activity.finish()
    }
}

sealed class StoreDetailsDestination : Destination {
    data class MapDestination(val location: String) : StoreDetailsDestination()
    object BackNavigation : StoreDetailsDestination()
}
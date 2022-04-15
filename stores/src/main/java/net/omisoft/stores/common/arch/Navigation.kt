package net.omisoft.stores.common.arch

import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.Flow
import net.omisoft.stores.common.util.collectDistinctFlow

//Marker
interface Destination

abstract class ViewRouter<D : Destination>(
    private val lifecycleOwner: LifecycleOwner,
) {

    protected abstract fun navigate(destination: D)

    fun subscribe(navigateTo: Flow<D>) {
        lifecycleOwner.collectDistinctFlow(navigateTo) { destination -> navigate(destination) }
    }
}
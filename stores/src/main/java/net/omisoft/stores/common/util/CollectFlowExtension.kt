package net.omisoft.stores.common.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

/**
 * Collect items from the specified [Flow] only when activity is at least in STARTED state.
 */
fun <T> LifecycleOwner.collectFlow(flow: Flow<T>, onCollect: (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            // this coroutine is launched every time when onStart is called;
            // collecting is cancelled in onStop
            flow.collect {
                onCollect(it)
            }
        }
    }
}

fun <T> LifecycleOwner.collectDistinctFlow(flow: Flow<T>, onCollect: (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.distinctUntilChanged().collect {
                onCollect(it)
            }
        }
    }
}
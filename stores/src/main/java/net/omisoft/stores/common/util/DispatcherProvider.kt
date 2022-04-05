package net.omisoft.stores.common.util

import kotlinx.coroutines.CoroutineDispatcher

data class DispatcherProvider(
    val ioDispatcher: CoroutineDispatcher,
    val mainDispatcher: CoroutineDispatcher,
)
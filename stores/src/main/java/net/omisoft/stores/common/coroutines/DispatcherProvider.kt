package net.omisoft.stores.common.coroutines

import kotlinx.coroutines.CoroutineDispatcher

data class DispatcherProvider(
    val ioDispatcher: CoroutineDispatcher,
    val mainDispatcher: CoroutineDispatcher,
)
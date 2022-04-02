package net.omisoft.stores.screens.detail

import net.omisoft.stores.common.data.model.Store

sealed class StoreDetailsAction {
    object OpenMapClick : StoreDetailsAction()
    object BackClick : StoreDetailsAction()
    data class OnStart(val store: Store) : StoreDetailsAction()
}
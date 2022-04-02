package net.omisoft.stores.screens.list

import net.omisoft.stores.common.data.model.Store

sealed class StoresAction {
    data class ClickItem(val store: Store) : StoresAction()
    data class EmptyStoreList(val empty: Boolean) : StoresAction()
    object FetchStoreList : StoresAction()
}
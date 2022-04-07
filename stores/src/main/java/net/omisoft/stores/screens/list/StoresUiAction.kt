package net.omisoft.stores.screens.list

import net.omisoft.stores.common.data.model.Store

sealed class StoresUiAction {
    data class ClickItem(val store: Store) : StoresUiAction()
    data class EmptyStoreList(val empty: Boolean) : StoresUiAction()
    object RefreshStoreList : StoresUiAction()
}
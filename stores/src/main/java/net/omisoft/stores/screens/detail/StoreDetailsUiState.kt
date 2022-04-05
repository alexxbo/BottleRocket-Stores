package net.omisoft.stores.screens.detail

import net.omisoft.stores.common.data.model.Store

data class StoreDetailsUiState(
    val store: Store? = null,
    val showEmptyState: Boolean = false,
    val locationData: String? = null
)
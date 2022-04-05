package net.omisoft.stores.screens.list

import androidx.paging.PagingData
import net.omisoft.stores.common.data.model.Store

data class StoreUiState(
    val showEmptyState: Boolean = false,
    val pagingData: PagingData<Store>? = null,
)
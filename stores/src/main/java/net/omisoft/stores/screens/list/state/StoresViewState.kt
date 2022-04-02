package net.omisoft.stores.screens.list.state

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import net.omisoft.stores.common.data.model.Store

interface StoresViewState {
    val stores: LiveData<PagingData<Store>>
    val showEmptyState: LiveData<Boolean>
}
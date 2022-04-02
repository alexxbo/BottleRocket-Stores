package net.omisoft.stores.screens.list.state

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import net.omisoft.stores.common.data.model.Store

class StoresViewStateImpl : StoresViewState {
    override var stores = MutableLiveData<PagingData<Store>>()
    override val showEmptyState = MutableLiveData<Boolean>()
}
package net.omisoft.stores.screens.detail.state

import androidx.lifecycle.MutableLiveData
import net.omisoft.stores.common.data.model.Store

class StoreDetailsViewStateImpl : StoreDetailsViewState {
    override var store = MutableLiveData<Store>()
    override val showEmptyState = MutableLiveData<Unit>()
}
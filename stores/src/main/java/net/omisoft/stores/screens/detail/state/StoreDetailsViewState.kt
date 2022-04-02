package net.omisoft.stores.screens.detail.state

import androidx.lifecycle.LiveData
import net.omisoft.stores.common.data.model.Store

interface StoreDetailsViewState {
    val store: LiveData<Store>
    val showEmptyState: LiveData<Unit>
}
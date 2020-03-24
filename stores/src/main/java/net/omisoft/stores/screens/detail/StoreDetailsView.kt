package net.omisoft.stores.screens.detail

import net.omisoft.stores.common.arch.View
import net.omisoft.stores.database.entity.Store

interface StoreDetailsView : View {
    fun publishData(store: Store)
    fun showOnMap(locationData: String)
    fun showEmptyState()
}
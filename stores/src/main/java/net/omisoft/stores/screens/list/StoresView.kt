package net.omisoft.stores.screens.list

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import net.omisoft.stores.common.arch.View
import net.omisoft.stores.database.entity.Store

interface StoresView : View {
    fun showLoading()
    fun hideLoading()
    fun publishData(data: LiveData<PagedList<Store>>)
    fun showMessage(msg: String)
    fun showEmptyState()
    fun openStoreDetails(store: Store)
    fun hideEmptyState()
}
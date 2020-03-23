package net.omisoft.stores.screens.list.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import io.reactivex.Completable
import net.omisoft.stores.database.entity.Store

interface StoresRepository {
    fun getStore(): LiveData<PagedList<Store>>
    fun insertAll(stores: List<Store>): Completable
}
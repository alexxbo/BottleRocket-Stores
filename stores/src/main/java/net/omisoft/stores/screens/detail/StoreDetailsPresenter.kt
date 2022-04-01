package net.omisoft.stores.screens.detail

import net.omisoft.stores.common.arch.Presenter
import net.omisoft.stores.common.data.model.Store

class StoreDetailsPresenter : Presenter<StoreDetailsView>() {

    private var locationData: String? = null

    fun doOnStart(store: Store?) {
        if (store == null) {
            view?.showEmptyState()
        } else {
            prepareLocationData(store.latitude, store.longitude)
            view?.publishData(store)
        }
    }

    fun onOpenMapClick() {
        locationData?.let { view?.showOnMap(it) }
    }

    private fun prepareLocationData(latitude: String, longitude: String) {
        locationData = "geo:$latitude,$longitude"
    }
}
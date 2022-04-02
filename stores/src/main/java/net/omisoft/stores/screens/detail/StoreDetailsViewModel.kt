package net.omisoft.stores.screens.detail

import net.omisoft.stores.common.arch.BaseViewModel
import net.omisoft.stores.common.data.model.Store
import net.omisoft.stores.screens.detail.navigation.StoreDetailsNavigator
import net.omisoft.stores.screens.detail.state.StoreDetailsViewState
import net.omisoft.stores.screens.detail.state.StoreDetailsViewStateImpl
import javax.inject.Inject

class StoreDetailsViewModel @Inject constructor() : BaseViewModel<StoreDetailsNavigator>() {

    private var locationData: String? = null

    private val _viewState = StoreDetailsViewStateImpl()
    val viewState: StoreDetailsViewState = _viewState

    fun onAction(action: StoreDetailsAction) {
        when (action) {
            is StoreDetailsAction.OnStart -> doOnStart(action.store)
            is StoreDetailsAction.OpenMapClick -> onOpenMapClick()
            is StoreDetailsAction.BackClick -> onBackClicked()
        }
    }

    private fun onBackClicked() {
        navigateTo(StoreDetailsNavigator.BackNavigation)
    }

    private fun doOnStart(store: Store?) {
        if (store == null) {
            _viewState.showEmptyState.value = Unit
        } else {
            prepareLocationData(store.latitude, store.longitude)
            _viewState.store.value = store
        }
    }

    private fun onOpenMapClick() {
        locationData?.let { navigateTo(StoreDetailsNavigator.MapNavigation(it)) }
    }

    private fun prepareLocationData(latitude: String, longitude: String) {
        locationData = "geo:$latitude,$longitude"
    }
}
package net.omisoft.stores.screens.detail

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import net.omisoft.stores.common.arch.BaseViewModel
import net.omisoft.stores.common.data.model.Store
import net.omisoft.stores.screens.detail.navigation.StoreDetailsNavigator
import javax.inject.Inject

class StoreDetailsViewModel @Inject constructor() : BaseViewModel<StoreDetailsNavigator>() {

    private val _uiState = MutableStateFlow(StoreDetailsUiState())
    val uiState: StateFlow<StoreDetailsUiState> = _uiState.asStateFlow()

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
            _uiState.update { currentUiState ->
                currentUiState.copy(showEmptyState = true)
            }
        } else {
            _uiState.update { currentUiState ->
                currentUiState.copy(
                    store = store,
                    locationData = "geo:$store.latitude,$store.longitude"
                )
            }
        }
    }

    private fun onOpenMapClick() {
        uiState.value.locationData?.let { navigateTo(StoreDetailsNavigator.MapNavigation(it)) }
    }
}
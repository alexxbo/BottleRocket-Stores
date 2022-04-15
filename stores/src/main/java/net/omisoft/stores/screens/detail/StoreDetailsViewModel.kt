package net.omisoft.stores.screens.detail

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.omisoft.stores.common.arch.BaseViewModel
import net.omisoft.stores.common.data.model.Store
import net.omisoft.stores.screens.detail.navigation.StoreDetailsDestination
import javax.inject.Inject

@HiltViewModel
class StoreDetailsViewModel @Inject constructor(
) : BaseViewModel<StoreDetailsDestination>() {

    private val _uiState = MutableStateFlow(StoreDetailsUiState())
    val uiState: StateFlow<StoreDetailsUiState> = _uiState.asStateFlow()

    fun onAction(action: StoreDetailsAction) {
        when (action) {
            is StoreDetailsAction.OnStart -> doOnStart(action.store)
            is StoreDetailsAction.OpenMapClick -> onOpenMapClick()
            is StoreDetailsAction.BackClick -> onBackClicked()
        }
    }

    private fun onBackClicked() = viewModelScope.launch {
        navigateTo(StoreDetailsDestination.BackNavigation)
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

    private fun onOpenMapClick() = viewModelScope.launch {
        uiState.value.locationData?.let { navigateTo(StoreDetailsDestination.MapDestination(it)) }
    }
}
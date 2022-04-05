package net.omisoft.stores.screens.list


import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.omisoft.stores.common.arch.BaseViewModel
import net.omisoft.stores.common.data.model.Store
import net.omisoft.stores.screens.list.data.StoresRepository
import net.omisoft.stores.screens.list.navigation.StoresNavigator
import javax.inject.Inject

@HiltViewModel
class StoresViewModel @Inject constructor(
    private val repository: StoresRepository,
) : BaseViewModel<StoresNavigator>() {

    private val _uiState = MutableStateFlow(StoreUiState())
    val uiState: StateFlow<StoreUiState> = _uiState.asStateFlow()

    fun onAction(action: StoresUiAction) {
        when (action) {
            is StoresUiAction.FetchStoreList -> fetchStores()
            is StoresUiAction.ClickItem -> onStoreItemClicked(action.store)
            is StoresUiAction.EmptyStoreList -> onStoreListEmpty(action.empty)
        }
    }

    private fun fetchStores() {
        loadStores()
        refreshStores()
    }

    private fun loadStores() = viewModelScope.launch {
        repository.getStore()
            .cachedIn(this)
            .collect { pagingData ->
                _uiState.update { currentUiState ->
                    currentUiState.copy(pagingData = pagingData)
                }
            }
    }

    private fun onStoreItemClicked(store: Store) = viewModelScope.launch {
        navigateTo(StoresNavigator.StoreDetailsNavigation(store))
    }

    private fun onStoreListEmpty(isEmptyList: Boolean) = viewModelScope.launch {
        _uiState.update { currentUiState ->
            currentUiState.copy(showEmptyState = isEmptyList)
        }
    }

    private fun refreshStores() = viewModelScope.launch {
        showProgress(true)

        repository.refreshStores()
            .onFailure(::onError)

        showProgress(false)
    }
}
package net.omisoft.stores.screens.list


import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import net.omisoft.stores.common.arch.BaseViewModel
import net.omisoft.stores.common.data.model.Store
import net.omisoft.stores.screens.list.data.StoresRepository
import net.omisoft.stores.screens.list.navigation.StoresDestination
import javax.inject.Inject

@HiltViewModel
class StoresViewModel @Inject constructor(
    private val repository: StoresRepository,
) : BaseViewModel<StoresDestination>() {

    private val _uiState = MutableStateFlow(StoreUiState())
    val uiState: StateFlow<StoreUiState> = _uiState.asStateFlow()

    val pagingDataFlow: Flow<PagingData<Store>> = repository.getStoresPagingData()
        .cachedIn(viewModelScope)

    fun onAction(action: StoresUiAction) {
        when (action) {
            is StoresUiAction.RefreshStoreList -> refreshStores()
            is StoresUiAction.ClickItem -> onStoreItemClicked(action.store)
            is StoresUiAction.EmptyStoreList -> onStoreListEmpty(action.empty)
        }
    }

    private fun onStoreItemClicked(store: Store) = viewModelScope.launch {
        navigateTo(StoresDestination.StoreDetailsDestination(store))
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
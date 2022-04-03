package net.omisoft.stores.screens.list


import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import net.omisoft.stores.common.arch.BaseViewModel
import net.omisoft.stores.common.data.model.Store
import net.omisoft.stores.screens.list.data.StoresRepository
import net.omisoft.stores.screens.list.navigation.StoresNavigator
import net.omisoft.stores.screens.list.state.StoresViewState
import net.omisoft.stores.screens.list.state.StoresViewStateImpl
import javax.inject.Inject

@HiltViewModel
class StoresViewModel @Inject constructor(
    private val repository: StoresRepository,
) : BaseViewModel<StoresNavigator>() {

    private val _viewState = StoresViewStateImpl()
    val viewState: StoresViewState = _viewState

    fun onAction(storesAction: StoresAction) {
        when (storesAction) {
            is StoresAction.FetchStoreList -> fetchStores()
            is StoresAction.ClickItem -> onStoreItemClicked(storesAction.store)
            is StoresAction.EmptyStoreList -> onStoreListEmpty(storesAction.empty)
        }
    }

    private fun fetchStores() {
        loadStores()
        refreshStores()
    }

    private fun loadStores() = viewModelScope.launch {
        val result = repository.getStore()
        result.collectLatest { pagingData ->
            _viewState.stores.value = pagingData
        }
    }

    private fun onStoreItemClicked(store: Store) {
        viewModelScope.launch {
            navigateTo(StoresNavigator.StoreDetailsNavigation(store))
        }
    }

    private fun onStoreListEmpty(isEmptyList: Boolean) {
        _viewState.showEmptyState.value = isEmptyList
    }

    private fun refreshStores() = viewModelScope.launch {
        showProgress(true)

        repository.refreshStores()
            .onFailure(::onError)

        showProgress(false)
    }
}
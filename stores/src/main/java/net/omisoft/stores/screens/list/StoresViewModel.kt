package net.omisoft.stores.screens.list


import dagger.hilt.android.lifecycle.HiltViewModel
import net.omisoft.stores.common.arch.BaseViewModel
import net.omisoft.stores.common.data.model.Store
import net.omisoft.stores.common.rx.RxWorkers
import net.omisoft.stores.common.util.composeWith
import net.omisoft.stores.screens.list.data.StoresRepository
import net.omisoft.stores.screens.list.navigation.StoresNavigator
import net.omisoft.stores.screens.list.state.StoresViewState
import net.omisoft.stores.screens.list.state.StoresViewStateImpl
import javax.inject.Inject

@HiltViewModel
class StoresViewModel @Inject constructor(
    private val repository: StoresRepository,
    private val workers: RxWorkers,
) : BaseViewModel<StoresNavigator>() {

    private val _viewState = StoresViewStateImpl()
    val viewState: StoresViewState = _viewState

    fun onAction(storesAction: StoresAction) {
        when (storesAction) {
            is StoresAction.FetchStoreList -> loadStores()
            is StoresAction.ClickItem -> onStoreItemClicked(storesAction.store)
            is StoresAction.EmptyStoreList -> onStoreListEmpty(storesAction.empty)
        }
    }

    private fun loadStores() {
        addSubscription(
            repository.getStore()
                .composeWith(workers)
                .subscribe({
                    if (it == null) return@subscribe
                    _viewState.stores.value = it
                }, ::onError)
        )

        refreshStores()
    }

    private fun onStoreItemClicked(store: Store) {
        navigateTo(StoresNavigator.StoreDetailsNavigation(store))
    }

    private fun onStoreListEmpty(isEmptyList: Boolean) {
        _viewState.showEmptyState.value = isEmptyList
    }

    private fun refreshStores() {
        showProgress(true)
        addSubscription(
            repository.refreshStores()
                .composeWith(workers)
                .subscribe({
                    showProgress(false)
                }, ::onError)
        )
    }
}
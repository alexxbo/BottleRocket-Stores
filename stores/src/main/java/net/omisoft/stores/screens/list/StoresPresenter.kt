package net.omisoft.stores.screens.list


import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import net.omisoft.stores.common.arch.Presenter
import net.omisoft.stores.common.arch.RxPresenter
import net.omisoft.stores.common.rx.RxWorkers
import net.omisoft.stores.common.util.composeWith
import net.omisoft.stores.database.entity.Store
import net.omisoft.stores.screens.list.api.StoresApi
import net.omisoft.stores.screens.list.data.StoresRepository
import timber.log.Timber

class StoresPresenter(private val repository: StoresRepository,
                      private val api: StoresApi,
                      private val workers: RxWorkers) : Presenter<StoresView>(), RxPresenter {

    private val subscriptions by lazy { CompositeDisposable() }

    override fun addSubscription(subscription: Disposable) {
        subscriptions.add(subscription)
    }

    override fun cancel() {
        subscriptions.clear()
    }

    fun loadContent() {
        view?.publishData(repository.getStore())
        refreshStores()
    }

    fun onItemClicked(store: Store) {
        view?.openStoreDetails(store)
    }

    fun onStoreListEmpty(isEmptyList: Boolean) {
        if (isEmptyList) {
            view?.showEmptyState()
        } else {
            view?.hideEmptyState()
        }
    }

    private fun refreshStores() {
        view?.showLoading()
        addSubscription(api.getStores()
                .flatMapCompletable { repository.insertAll(it.stores) }
                .composeWith(workers)
                .subscribe({ view?.hideLoading() }, ::onError))
    }

    private fun onError(error: Throwable) {
        Timber.e(error)
        view?.hideLoading()
        error.localizedMessage?.let { view?.showMessage(it) }
    }
}
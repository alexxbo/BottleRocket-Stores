package net.omisoft.stores.common.arch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import net.omisoft.stores.common.util.Event
import timber.log.Timber

abstract class BaseViewModel<N : Navigator> : ViewModel() {

    private val subscriptions by lazy { CompositeDisposable() }

    private val _navigateTo = MutableLiveData<Event<N>>(null)
    val navigateTo: LiveData<Event<N>> = _navigateTo

    private val _progress = MutableLiveData(false)
    val progress: LiveData<Boolean> = _progress

    private val _errorMessage = MutableLiveData<Event<String>>()
    val errorMessage: LiveData<Event<String>> = _errorMessage

    override fun onCleared() {
        super.onCleared()

        subscriptions.clear()
    }

    protected open fun onError(error: Throwable) {
        Timber.e(error)
        showProgress(false)
        error.localizedMessage?.let { _errorMessage.value = Event(it) }
    }

    protected fun showProgress(show: Boolean) {
        _progress.value = show
    }

    protected fun showErrorMessage(message: String) {
        _errorMessage.value = Event(message)
    }

    protected fun navigateTo(destination: N) {
        _navigateTo.value = Event(destination)
    }

    protected fun addSubscription(subscription: Disposable) {
        subscriptions.add(subscription)
    }
}
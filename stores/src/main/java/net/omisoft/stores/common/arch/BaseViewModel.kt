package net.omisoft.stores.common.arch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModel<N : Navigator> : ViewModel() {

    private val subscriptions by lazy { CompositeDisposable() }

    private val _navigateTo: MutableSharedFlow<N> =
        MutableSharedFlow(extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val navigateTo: SharedFlow<N> = _navigateTo.asSharedFlow()

    private val _errorMessage: MutableSharedFlow<String> =
        MutableSharedFlow(extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val errorMessage: SharedFlow<String> = _errorMessage.asSharedFlow()

    private val _progress = MutableStateFlow(false)
    val progress: StateFlow<Boolean> = _progress.asStateFlow()

    protected open fun onError(error: Throwable) = viewModelScope.launch {
        if (errorMessage is CancellationException) return@launch
        Timber.e(error)
        showProgress(false)
        error.localizedMessage?.let { _errorMessage.emit(it) }
    }

    protected fun showProgress(show: Boolean) {
        _progress.value = show
    }

    protected fun showErrorMessage(message: String) = viewModelScope.launch {
        _errorMessage.emit(message)
    }

    protected fun navigateTo(destination: N) = viewModelScope.launch {
        _navigateTo.emit(destination)
    }
}
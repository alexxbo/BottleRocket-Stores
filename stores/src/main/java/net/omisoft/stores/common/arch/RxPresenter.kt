package net.omisoft.stores.common.arch

import io.reactivex.disposables.Disposable

interface RxPresenter {
    fun addSubscription(subscription: Disposable)
    fun cancel()
}
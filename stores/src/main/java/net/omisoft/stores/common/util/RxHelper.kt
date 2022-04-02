package net.omisoft.stores.common.util

import io.reactivex.Completable
import io.reactivex.Flowable
import net.omisoft.stores.common.rx.RxWorkers

fun Completable.composeWith(workers: RxWorkers): Completable = this.compose {
    it.subscribeOn(workers.subscribeWorker).observeOn(workers.observeWorker)
}

fun <T> Flowable<T>.composeWith(workers: RxWorkers): Flowable<T> = this.compose {
    it.subscribeOn(workers.subscribeWorker).observeOn(workers.observeWorker)
}
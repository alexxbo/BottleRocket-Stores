package net.omisoft.stores.common.util

import io.reactivex.Completable
import net.omisoft.stores.common.rx.RxWorkers

fun Completable.composeWith(workers: RxWorkers) = this.compose {
    it.subscribeOn(workers.subscribeWorker).observeOn(workers.observeWorker)
}
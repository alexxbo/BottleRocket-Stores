package net.omisoft.stores.screens.splash

import android.os.Handler
import android.os.Looper
import net.omisoft.stores.common.arch.Presenter

class SplashPresenter : Presenter<SplashView>() {

    companion object {
        private const val SPLASH_DELAY = 1000L
    }

    private var handler = Handler(Looper.getMainLooper())

    fun doOnStart() {
        handler.postDelayed({
            view?.openContentScreen()
        }, SPLASH_DELAY)
    }

    fun doOnStop() {
        handler.removeCallbacksAndMessages(null)
    }
}
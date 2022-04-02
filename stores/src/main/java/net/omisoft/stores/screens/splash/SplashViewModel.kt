package net.omisoft.stores.screens.splash

import android.os.Handler
import android.os.Looper
import dagger.hilt.android.lifecycle.HiltViewModel
import net.omisoft.stores.common.arch.BaseViewModel
import net.omisoft.stores.screens.splash.navigation.SplashNavigator
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : BaseViewModel<SplashNavigator>() {

    companion object {
        private const val SPLASH_DELAY = 1000L
    }

    private var handler = Handler(Looper.getMainLooper())

    fun doOnStart() {
        handler.postDelayed({
            navigateTo(SplashNavigator.ContentScreenNavigation)
        }, SPLASH_DELAY)
    }

    fun doOnStop() {
        handler.removeCallbacksAndMessages(null)
    }
}
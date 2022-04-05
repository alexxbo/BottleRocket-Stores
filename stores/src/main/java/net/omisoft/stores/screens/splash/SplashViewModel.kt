package net.omisoft.stores.screens.splash

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.omisoft.stores.common.arch.BaseViewModel
import net.omisoft.stores.screens.splash.navigation.SplashNavigator
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : BaseViewModel<SplashNavigator>() {

    companion object {
        private const val SPLASH_DELAY = 1000L
    }

    fun doOnStart() = viewModelScope.launch {
        delay(SPLASH_DELAY)
        navigateTo(SplashNavigator.ContentScreenNavigation)
    }
}
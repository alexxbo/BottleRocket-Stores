package net.omisoft.stores.screens.splash

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import net.omisoft.mvptemplate.R
import net.omisoft.stores.common.util.EventObserver
import net.omisoft.stores.screens.list.StoresActivity
import net.omisoft.stores.screens.splash.navigation.SplashNavigator

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onStart() {
        super.onStart()

        subscribeUi()
        viewModel.doOnStart()
    }

    override fun onStop() {
        super.onStop()

        viewModel.doOnStop()
    }

    private fun subscribeUi() {
        viewModel.run {
            navigateTo.observe(this@SplashActivity, EventObserver { destination -> navigateTo(destination) })
        }
    }

    private fun navigateTo(destination: SplashNavigator) {
        when (destination) {
            is SplashNavigator.ContentScreenNavigation -> openContentScreen()
        }
    }

    private fun openContentScreen() {
        StoresActivity.launch(this)
    }
}
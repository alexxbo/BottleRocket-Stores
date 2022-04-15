package net.omisoft.stores.screens.splash

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import net.omisoft.bottlerocket.R
import net.omisoft.stores.screens.splash.navigation.SplashNavigation

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        initNavigation()
    }

    override fun onStart() {
        super.onStart()

        viewModel.doOnStart()
    }

    private fun initNavigation() {
        SplashNavigation(lifecycleOwner = this, activity = this)
            .subscribe(viewModel.navigateTo)
    }
}
package net.omisoft.stores.screens.splash

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import net.omisoft.mvptemplate.R
import net.omisoft.stores.common.arch.BaseActivity
import net.omisoft.stores.screens.list.StoresActivity
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : BaseActivity<SplashView, SplashPresenter>(), SplashView {

    @Inject override lateinit var presenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onStart() {
        super.onStart()

        presenter.doOnStart()
    }

    override fun onStop() {
        super.onStop()

        presenter.doOnStop()
    }

    override fun openContentScreen() {
        StoresActivity.launch(this)
    }
}
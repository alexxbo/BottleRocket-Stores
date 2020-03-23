package net.omisoft.stores.screens.splash

import android.os.Bundle
import net.omisoft.mvptemplate.R
import net.omisoft.stores.App
import net.omisoft.stores.common.arch.BaseActivity
import net.omisoft.stores.screens.list.StoresActivity
import net.omisoft.stores.screens.splash.di.DaggerSplashComponent
import net.omisoft.stores.screens.splash.di.SplashModule
import javax.inject.Inject

class SplashActivity : BaseActivity<SplashView, SplashPresenter>(), SplashView {

    private val component by lazy {
        DaggerSplashComponent.builder()
                .appComponent((application as App).component)
                .activity(this)
                .plus(SplashModule())
                .build()
    }

    @Inject override lateinit var presenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
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
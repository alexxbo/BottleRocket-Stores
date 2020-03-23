package net.omisoft.stores.common.arch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity<V : View, T : Presenter<V>> : AppCompatActivity(), View {

    protected abstract val presenter: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.bindView(view = this as V)
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.unbindView()
    }
}
package net.omisoft.stores.screens.list

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import net.omisoft.mvptemplate.R
import net.omisoft.mvptemplate.databinding.ActivityStoresBinding
import net.omisoft.stores.App
import net.omisoft.stores.common.arch.BaseActivity
import net.omisoft.stores.database.entity.Store
import net.omisoft.stores.screens.detail.StoreDetailsActivity
import net.omisoft.stores.screens.list.adapter.StoresAdapter
import net.omisoft.stores.screens.list.di.DaggerStoresComponent
import net.omisoft.stores.screens.list.di.StoresModule
import javax.inject.Inject

class StoresActivity : BaseActivity<StoresView, StoresPresenter>(), StoresView {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, StoresActivity::class.java)
            activity.startActivity(intent)
            activity.finish()
        }
    }

    @Inject
    override lateinit var presenter: StoresPresenter
    private lateinit var binding: ActivityStoresBinding
    private lateinit var adapter: StoresAdapter

    private val component by lazy {
        DaggerStoresComponent.builder()
            .appComponent((application as App).component)
            .activity(this)
            .plus(StoresModule())
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)

        binding = ActivityStoresBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        presenter.loadContent()
    }

    override fun onDestroy() {
        presenter.cancel()

        super.onDestroy()
    }

    override fun showLoading() {
        binding.swipeRefreshContainer.isRefreshing = true
    }

    override fun hideLoading() {
        binding.swipeRefreshContainer.isRefreshing = false
    }

    override fun publishData(data: LiveData<PagedList<Store>>) {
        data.observe(this, { stores ->
            stores?.let {
                data.observe(this, { adapter.submitList(it) })
                presenter.onStoreListEmpty(adapter.itemCount == 0 && stores.isEmpty())
            }
        })
    }

    override fun openStoreDetails(store: Store) {
        StoreDetailsActivity.launch(this, store)
    }

    override fun showEmptyState() {
        binding.listView.visibility = View.GONE
        binding.emptyState.visibility = View.VISIBLE
    }

    override fun hideEmptyState() {
        binding.emptyState.visibility = View.GONE
        binding.listView.visibility = View.VISIBLE
    }

    override fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun initView() {
        binding.includedToolbar.toolbar.apply {
            title = getString(R.string.stores_toolbar_title)
        }

        adapter = StoresAdapter(object : StoresAdapter.ItemClickListener {
            override fun onItemClick(store: Store) {
                presenter.onItemClicked(store)
            }
        })
        binding.listView.adapter = adapter

        binding.swipeRefreshContainer.setOnRefreshListener { presenter.loadContent() }

        val dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        val divider: Drawable? = ContextCompat.getDrawable(this, R.drawable.divider)
        divider?.let { dividerItemDecoration.setDrawable(divider) }
        binding.listView.addItemDecoration(dividerItemDecoration)
    }
}
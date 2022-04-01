package net.omisoft.stores.screens.list

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import net.omisoft.mvptemplate.R
import net.omisoft.mvptemplate.databinding.ActivityStoresBinding
import net.omisoft.stores.common.arch.BaseActivity
import net.omisoft.stores.common.data.model.Store
import net.omisoft.stores.screens.detail.StoreDetailsActivity
import net.omisoft.stores.screens.list.adapter.StoresAdapter
import javax.inject.Inject

@AndroidEntryPoint
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

    override fun onCreate(savedInstanceState: Bundle?) {
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

    override fun publishData(data: LiveData<PagingData<Store>>) {
        data.observe(this, { stores ->
            stores?.let { data.observe(this, { adapter.submitData(lifecycle, it) }) }
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

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun initView() {
        binding.run {
            includedToolbar.toolbar.apply {
                title = getString(R.string.stores_toolbar_title)
            }

            adapter = StoresAdapter(listener = { presenter.onItemClicked(it) })
            adapter.addLoadStateListener { loadStates ->
                presenter.onStoreListEmpty(
                    loadStates.source.refresh is LoadState.NotLoading
                            && loadStates.append.endOfPaginationReached
                            && adapter.itemCount == 0
                )
            }
            listView.adapter = adapter
            listView.addDivider()

            swipeRefreshContainer.setOnRefreshListener { presenter.loadContent() }
        }
    }

    private fun RecyclerView.addDivider() {
        val dividerItemDecoration = DividerItemDecoration(this@StoresActivity, LinearLayoutManager.VERTICAL)
        val divider: Drawable? = ContextCompat.getDrawable(this@StoresActivity, R.drawable.divider)
        divider?.let { dividerItemDecoration.setDrawable(divider) }
        addItemDecoration(dividerItemDecoration)
    }
}
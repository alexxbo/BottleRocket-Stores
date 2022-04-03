package net.omisoft.stores.screens.list

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import net.omisoft.mvptemplate.R
import net.omisoft.mvptemplate.databinding.ActivityStoresBinding
import net.omisoft.stores.common.data.model.Store
import net.omisoft.stores.common.util.EventObserver
import net.omisoft.stores.screens.detail.StoreDetailsActivity
import net.omisoft.stores.screens.list.adapter.StoresAdapter
import net.omisoft.stores.screens.list.navigation.StoresNavigator

@AndroidEntryPoint
class StoresActivity : AppCompatActivity() {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, StoresActivity::class.java)
            activity.startActivity(intent)
            activity.finish()
        }
    }

    private lateinit var binding: ActivityStoresBinding
    private val viewModel: StoresViewModel by viewModels()
    private var adapter: StoresAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStoresBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        subscribeUi()
        viewModel.onAction(StoresAction.FetchStoreList)
    }

    private fun subscribeUi() = lifecycleScope.launch {
        viewModel.run {
            navigateTo.observe(this@StoresActivity, EventObserver { destination -> navigateTo(destination) })
            errorMessage.observe(this@StoresActivity, EventObserver { showMessage(it) })
            progress.observe(this@StoresActivity, { showLoading(it) })

            viewState.run {
                stores.observe(this@StoresActivity, { updateStores(it) })
                showEmptyState.observe(this@StoresActivity, { showEmptyState(it) })
            }
        }
    }

    private fun showLoading(show: Boolean) {
        binding.swipeRefreshContainer.isRefreshing = show
    }

    private fun updateStores(data: PagingData<Store>) {
        adapter?.submitData(lifecycle, data)
    }

    private fun navigateTo(destination: StoresNavigator) {
        when (destination) {
            is StoresNavigator.StoreDetailsNavigation -> openStoreDetails(destination.store)
        }
    }

    private fun openStoreDetails(store: Store) {
        StoreDetailsActivity.launch(this, store)
    }

    private fun showEmptyState(show: Boolean) {
        binding.listView.isGone = show
        binding.emptyState.isVisible = show
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun initView() {
        binding.run {
            includedToolbar.toolbar.apply {
                title = getString(R.string.stores_toolbar_title)
            }

            adapter = StoresAdapter(listener = { viewModel.onAction(StoresAction.ClickItem(it)) })
            adapter?.addLoadStateListener { loadStates ->
                viewModel.onAction(
                    StoresAction.EmptyStoreList(
                        loadStates.source.refresh is LoadState.NotLoading
                                && loadStates.append.endOfPaginationReached
                                && adapter?.itemCount == 0
                    )
                )
            }
            listView.adapter = adapter
            listView.addDivider()

            swipeRefreshContainer.setOnRefreshListener { viewModel.onAction(StoresAction.FetchStoreList) }
        }
    }

    private fun RecyclerView.addDivider() {
        val dividerItemDecoration = DividerItemDecoration(this@StoresActivity, LinearLayoutManager.VERTICAL)
        val divider: Drawable? = ContextCompat.getDrawable(this@StoresActivity, R.drawable.divider)
        divider?.let { dividerItemDecoration.setDrawable(divider) }
        addItemDecoration(dividerItemDecoration)
    }
}
package net.omisoft.stores.screens.list

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import net.omisoft.bottlerocket.R
import net.omisoft.bottlerocket.databinding.ActivityStoresBinding
import net.omisoft.stores.common.data.model.Store
import net.omisoft.stores.common.util.collectDistinctFlow
import net.omisoft.stores.common.util.collectFlow
import net.omisoft.stores.screens.list.adapter.StoresAdapter
import net.omisoft.stores.screens.list.navigation.StoresNavigation

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
        installSplashScreen()
        super.onCreate(savedInstanceState)

        binding = ActivityStoresBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initNavigation()
        subscribeUi()

        viewModel.onAction(StoresUiAction.RefreshStoreList)
    }

    private fun subscribeUi() {
        viewModel.run {
            collectDistinctFlow(errorMessage) { message -> showMessage(message) }
            collectFlow(progress) { show -> showLoading(show) }
            collectFlow(uiState) { uiState -> showEmptyState(uiState.showEmptyState) }
            collectFlow(pagingDataFlow) { updateStores(it) }
        }
    }

    private fun initNavigation() {
        StoresNavigation(lifecycleOwner = this, activity = this)
            .subscribe(viewModel.navigateTo)
    }

    private fun showLoading(show: Boolean) {
        binding.swipeRefreshContainer.isRefreshing = show
    }

    private fun updateStores(data: PagingData<Store>?) {
        if (data == null) return
        adapter?.submitData(lifecycle, data)
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

            adapter = StoresAdapter(listener = { viewModel.onAction(StoresUiAction.ClickItem(it)) })
            adapter?.addLoadStateListener { loadStates ->
                viewModel.onAction(
                    StoresUiAction.EmptyStoreList(
                        loadStates.source.refresh is LoadState.NotLoading
                                && loadStates.append.endOfPaginationReached
                                && adapter?.itemCount == 0
                    )
                )
            }
            listView.adapter = adapter
            listView.addDivider()

            swipeRefreshContainer.setOnRefreshListener { viewModel.onAction(StoresUiAction.RefreshStoreList) }
        }
    }

    private fun RecyclerView.addDivider() {
        val dividerItemDecoration = DividerItemDecoration(this@StoresActivity, LinearLayoutManager.VERTICAL)
        val divider: Drawable? = ContextCompat.getDrawable(this@StoresActivity, R.drawable.divider)
        divider?.let { dividerItemDecoration.setDrawable(divider) }
        addItemDecoration(dividerItemDecoration)
    }
}
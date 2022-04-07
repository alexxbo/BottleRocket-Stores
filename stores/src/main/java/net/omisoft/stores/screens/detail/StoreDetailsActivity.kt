package net.omisoft.stores.screens.detail

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import net.omisoft.bottlerocket.BuildConfig
import net.omisoft.bottlerocket.R
import net.omisoft.bottlerocket.databinding.ActivityStoreDetailsBinding
import net.omisoft.stores.common.data.model.Store
import net.omisoft.stores.common.util.collectDistinctFlow
import net.omisoft.stores.common.util.collectFlow
import net.omisoft.stores.screens.detail.navigation.StoreDetailsNavigator

private const val STORE_EXTRA = "${BuildConfig.APPLICATION_ID}_STORE_EXTRA"
private var Intent.storeExtra
    get() = getParcelableExtra<Store>(STORE_EXTRA)
    set(value) {
        putExtra(STORE_EXTRA, value)
    }

@AndroidEntryPoint
class StoreDetailsActivity : AppCompatActivity() {

    companion object {
        private const val MAP_PACKAGE = "com.google.android.apps.maps"

        fun launch(activity: Activity, store: Store) {
            Intent(activity, StoreDetailsActivity::class.java)
                .apply { storeExtra = store }
                .also { activity.startActivity(it) }
        }
    }

    private val viewModel: StoreDetailsViewModel by viewModels()
    private lateinit var binding: ActivityStoreDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStoreDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        subscribeUi()
        intent?.storeExtra?.let { viewModel.onAction(StoreDetailsAction.OnStart(it)) }
    }

    private fun subscribeUi() {
        viewModel.run {
            collectDistinctFlow(viewModel.navigateTo) { destination -> navigateTo(destination) }
            collectFlow(uiState) { uiState ->
                showEmptyState(uiState.showEmptyState)
                updateStore(uiState.store)
            }
        }
    }

    private fun navigateTo(destination: StoreDetailsNavigator) {
        when (destination) {
            is StoreDetailsNavigator.MapNavigation -> showOnMap(destination.location)
            is StoreDetailsNavigator.BackNavigation -> goBack()
        }
    }

    private fun updateStore(store: Store?) {
        if (store == null) return
        binding.run {

            storeLogo.load(store.storeLogoURL)
            storeTitle.text = store.name
            storePhone.text = getString(R.string.store_phone, store.phone)
            storeState.text = getString(R.string.store_state, store.state)
            storeZipcode.text = getString(R.string.store_zipcode, store.zipcode)
            storeCity.text = getString(R.string.store_city, store.city)
            storeAddress.text = getString(R.string.store_address, store.address)
        }
    }

    private fun showOnMap(locationData: String) {
        val gmmIntentUri: Uri = Uri.parse(locationData)
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage(MAP_PACKAGE)
        startActivity(mapIntent)
    }

    private fun showEmptyState(show: Boolean) {
        binding.photoContainer.isGone = show
        binding.emptyState.isVisible = show
    }

    private fun goBack() {
        finish()
    }

    private fun initView() {
        binding.includedToolbar.toolbar.apply {
            title = getString(R.string.store_details_toolbar_title)
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener { viewModel.onAction(StoreDetailsAction.BackClick) }
        }

        binding.storeLocationsButton.setOnClickListener { viewModel.onAction(StoreDetailsAction.OpenMapClick) }
    }
}
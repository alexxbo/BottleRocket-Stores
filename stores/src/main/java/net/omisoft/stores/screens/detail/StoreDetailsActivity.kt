package net.omisoft.stores.screens.detail

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.json.Json
import net.omisoft.mvptemplate.BuildConfig
import net.omisoft.mvptemplate.R
import net.omisoft.mvptemplate.databinding.ActivityStoreDetailsBinding
import net.omisoft.stores.common.arch.BaseActivity
import net.omisoft.stores.common.data.model.Store
import javax.inject.Inject

private const val STORE_EXTRA = "${BuildConfig.APPLICATION_ID}_STORE_EXTRA"
private var Intent.storeExtra
    get() = getStringExtra(STORE_EXTRA).orEmpty()
        .let { Json.decodeFromString(Store.serializer(), it) }
    set(value) {
        putExtra(STORE_EXTRA, Json.encodeToString(Store.serializer(), value))
    }

@AndroidEntryPoint
class StoreDetailsActivity : BaseActivity<StoreDetailsView, StoreDetailsPresenter>(),
    StoreDetailsView {

    companion object {
        private const val MAP_PACKAGE = "com.google.android.apps.maps"

        fun launch(activity: Activity, store: Store) {
            Intent(activity, StoreDetailsActivity::class.java)
                .apply { storeExtra = store }
                .also { activity.startActivity(it) }
        }
    }

    @Inject
    override lateinit var presenter: StoreDetailsPresenter
    private lateinit var binding: ActivityStoreDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStoreDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        intent?.storeExtra?.let { presenter.doOnStart(it) }
    }

    override fun publishData(store: Store) {
        binding.run {

            Glide.with(root)
                .load(store.storeLogoURL)
                .into(storeLogo)

            storeTitle.text = store.name
            storePhone.text = getString(R.string.store_phone, store.phone)
            storeState.text = getString(R.string.store_state, store.state)
            storeZipcode.text = getString(R.string.store_zipcode, store.zipcode)
            storeCity.text = getString(R.string.store_city, store.city)
            storeAddress.text = getString(R.string.store_address, store.address)
        }
    }

    override fun showOnMap(locationData: String) {
        val gmmIntentUri: Uri = Uri.parse(locationData)
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage(MAP_PACKAGE)
        startActivity(mapIntent)
    }

    override fun showEmptyState() {
        binding.photoContainer.visibility = View.GONE
        binding.emptyState.visibility = View.VISIBLE
    }

    private fun initView() {
        binding.includedToolbar.toolbar.apply {
            title = getString(R.string.store_details_toolbar_title)
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener { finish() }
        }

        binding.storeLocationsButton.setOnClickListener { presenter.onOpenMapClick() }
    }
}
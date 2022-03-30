package net.omisoft.stores.screens.detail

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import net.omisoft.mvptemplate.BuildConfig
import net.omisoft.mvptemplate.R
import net.omisoft.mvptemplate.databinding.ActivityStoreDetailsBinding
import net.omisoft.stores.App
import net.omisoft.stores.common.arch.BaseActivity
import net.omisoft.stores.database.entity.Store
import net.omisoft.stores.screens.detail.di.DaggerStoreDetailsComponent
import net.omisoft.stores.screens.detail.di.StoreDetailsModule
import javax.inject.Inject

class StoreDetailsActivity : BaseActivity<StoreDetailsView, StoreDetailsPresenter>(),
        StoreDetailsView {

    companion object {
        private const val STORE_EXTRA = "${BuildConfig.APPLICATION_ID}_STORE_EXTRA"
        private const val MAP_PACKAGE = "com.google.android.apps.maps"

        fun launch(activity: Activity, store: Store) {
            val intent = Intent(activity, StoreDetailsActivity::class.java)
            intent.putExtra(STORE_EXTRA, store)
            activity.startActivity(intent)
        }
    }

    private val component by lazy {
        DaggerStoreDetailsComponent.builder()
            .appComponent((application as App).component)
            .activity(this)
            .plus(StoreDetailsModule())
            .build()
    }

    @Inject
    override lateinit var presenter: StoreDetailsPresenter
    private lateinit var binding: ActivityStoreDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)

        binding = ActivityStoreDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        intent?.getParcelableExtra<Store>(STORE_EXTRA).let { presenter.doOnStart(it) }
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
            setSupportActionBar(this)
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener { finish() }
            title = getString(R.string.store_details_toolbar_title)
        }

        binding.storeLocationsButton.setOnClickListener { presenter.onOpenMapClick() }
    }
}
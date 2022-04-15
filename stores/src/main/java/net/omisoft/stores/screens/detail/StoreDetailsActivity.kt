package net.omisoft.stores.screens.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import net.omisoft.bottlerocket.BuildConfig
import net.omisoft.bottlerocket.R
import net.omisoft.stores.common.data.model.Store
import net.omisoft.stores.common.ui.theme.BottlerocketappsTheme
import net.omisoft.stores.screens.detail.navigation.StoreDetailsNavigation
import net.omisoft.stores.screens.detail.ui.EmptyStoreData
import net.omisoft.stores.screens.detail.ui.StoreCard

private const val STORE_EXTRA = "${BuildConfig.APPLICATION_ID}_STORE_EXTRA"
private var Intent.storeExtra
    get() = getParcelableExtra<Store>(STORE_EXTRA)
    set(value) {
        putExtra(STORE_EXTRA, value)
    }

@AndroidEntryPoint
class StoreDetailsActivity : ComponentActivity() {

    companion object {

        fun launch(activity: Activity, store: Store) {
            Intent(activity, StoreDetailsActivity::class.java)
                .apply { storeExtra = store }
                .also { activity.startActivity(it) }
        }
    }

    private val viewModel: StoreDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initNavigation()

        intent?.storeExtra?.also { store ->
            viewModel.onAction(StoreDetailsAction.OnStart(store))
        }
        setContent {
            StoreDetailScreen(viewModel)
        }
    }

    private fun initNavigation() {
        StoreDetailsNavigation(lifecycleOwner = this, activity = this)
            .subscribe(viewModel.navigateTo)
    }
}

@Composable
fun StoreDetailScreen(viewModel: StoreDetailsViewModel) {

    val uiSate by viewModel.uiState.collectAsState()
    BottlerocketappsTheme {
        Scaffold(topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.store_details_toolbar_title))
                },
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.onAction(StoreDetailsAction.BackClick)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp), color = MaterialTheme.colors.background
            ) {
                uiSate.store?.let { store ->
                    StoreCard(store) {
                        viewModel.onAction(StoreDetailsAction.OpenMapClick)
                    }
                } ?: EmptyStoreData()
            }
        }
    }
}
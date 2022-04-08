package net.omisoft.stores.screens.detail

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dagger.hilt.android.AndroidEntryPoint
import net.omisoft.bottlerocket.BuildConfig
import net.omisoft.bottlerocket.R
import net.omisoft.stores.common.data.model.Store
import net.omisoft.stores.common.ui.theme.BottlerocketappsTheme
import net.omisoft.stores.common.util.collectDistinctFlow
import net.omisoft.stores.screens.detail.navigation.StoreDetailsNavigator

private const val STORE_EXTRA = "${BuildConfig.APPLICATION_ID}_STORE_EXTRA"
private var Intent.storeExtra
    get() = getParcelableExtra<Store>(STORE_EXTRA)
    set(value) {
        putExtra(STORE_EXTRA, value)
    }

@AndroidEntryPoint
class StoreDetailsActivity : ComponentActivity() {

    companion object {
        private const val MAP_PACKAGE = "com.google.android.apps.maps"

        fun launch(activity: Activity, store: Store) {
            Intent(activity, StoreDetailsActivity::class.java)
                .apply { storeExtra = store }
                .also { activity.startActivity(it) }
        }
    }

    private val viewModel: StoreDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        subscribeUi()
        intent?.storeExtra?.also { store ->
            viewModel.onAction(StoreDetailsAction.OnStart(store))
        }
        setContent {
            BottlerocketappsTheme {
                StoreDetailScreen(viewModel)
            }
        }
    }

    private fun subscribeUi() {
        viewModel.run {
            collectDistinctFlow(viewModel.navigateTo) { destination -> navigateTo(destination) }
        }
    }

    private fun navigateTo(destination: StoreDetailsNavigator) {
        when (destination) {
            is StoreDetailsNavigator.MapNavigation -> showOnMap(destination.location)
            is StoreDetailsNavigator.BackNavigation -> goBack()
        }
    }

    private fun showOnMap(locationData: String) {
        val gmmIntentUri: Uri = Uri.parse(locationData)
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage(MAP_PACKAGE)
        startActivity(mapIntent)
    }

    private fun goBack() {
        finish()
    }
}

@Composable
private fun StoreDetailScreen(viewModel: StoreDetailsViewModel) {

    val uiSate by viewModel.uiState.collectAsState()

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

@Composable
fun EmptyStoreData() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .wrapContentWidth(Alignment.CenterHorizontally)
            .wrapContentHeight(Alignment.CenterVertically),
        text = stringResource(id = R.string.empty_data),
    )
}

@Composable
fun StoreCard(store: Store, onMapClick: () -> Unit) {

    Column(Modifier.fillMaxWidth()) {
        Row(Modifier.wrapContentHeight()) {
            AsyncImage(
                modifier = Modifier.size(70.dp, 70.dp),
                model = store.storeLogoURL,
                contentDescription = null,
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = store.name,
                    style = MaterialTheme.typography.h6,
                )
                Text(
                    text = stringResource(R.string.store_phone, store.phone),
                    style = MaterialTheme.typography.body1,
                )
            }
        }
        Text(text = stringResource(R.string.store_state, store.state))
        Row(Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.store_zipcode, store.zipcode),
            )
            Text(text = stringResource(R.string.store_city, store.city))
        }
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.store_address, store.address),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onMapClick,
        ) {
            Text(text = stringResource(R.string.show_on_the_map_button))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BottlerocketappsTheme {
        val store = Store(
            storeId = "12",
            address = "270001 US Aveny Sterly 25",
            city = "New York",
            latitude = "28.001",
            longitude = "-82.573301",
            name = "Macy's",
            phone = "813-926-7300",
            state = "FL",
            storeLogoURL = "http://sandbox.bottlerocketapps.com/BR_Android_CodingExam_2015_Server/images/sears.jpeg",
            zipcode = "33761"
        )

        StoreCard(store = store) { /* no-op */ }
    }
}
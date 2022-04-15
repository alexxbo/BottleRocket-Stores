package net.omisoft.stores.screens.detail.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import net.omisoft.bottlerocket.R
import net.omisoft.stores.common.data.model.Store
import net.omisoft.stores.common.ui.theme.BottlerocketappsTheme

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
package net.omisoft.stores.common.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Store(
    val storeId: String,
    val address: String,
    val city: String,
    val latitude: String,
    val longitude: String,
    val name: String,
    val phone: String,
    val state: String,
    val storeLogoURL: String,
    val zipcode: String,
): Parcelable
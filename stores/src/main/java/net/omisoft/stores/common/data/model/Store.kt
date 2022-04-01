package net.omisoft.stores.common.data.model

import kotlinx.serialization.Serializable

@Serializable
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
)
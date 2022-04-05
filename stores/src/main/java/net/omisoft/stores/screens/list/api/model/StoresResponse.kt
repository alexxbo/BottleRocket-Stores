package net.omisoft.stores.screens.list.api.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.omisoft.stores.common.data.storage.database.entity.StoreEntity

@Serializable
data class StoresResponse(
    @SerialName("stores") val stores: List<StoreResponse>
) {

    @Serializable
    data class StoreResponse(
        @SerialName("storeID") val storeId: String,
        @SerialName("address") val address: String,
        @SerialName("city") val city: String,
        @SerialName("latitude") val latitude: String,
        @SerialName("longitude") val longitude: String,
        @SerialName("name") val name: String,
        @SerialName("phone") val phone: String,
        @SerialName("state") val state: String,
        @SerialName("storeLogoURL") val storeLogoURL: String,
        @SerialName("zipcode") val zipcode: String,
    )
}

fun StoresResponse.StoreResponse.toStoreEntity(): StoreEntity {
    return StoreEntity(
        storeId = storeId,
        address = address,
        city = city,
        latitude = latitude,
        longitude = longitude,
        name = name,
        phone = phone,
        state = state,
        storeLogoURL = storeLogoURL,
        zipcode = zipcode,
    )
}
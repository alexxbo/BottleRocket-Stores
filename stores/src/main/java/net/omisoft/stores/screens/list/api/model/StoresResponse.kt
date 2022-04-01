package net.omisoft.stores.screens.list.api.model


import com.google.gson.annotations.SerializedName
import net.omisoft.stores.common.data.database.entity.StoreEntity

data class StoresResponse(
    @SerializedName("stores") val stores: List<StoreResponse>
) {

    data class StoreResponse(
        @SerializedName("storeID") val storeId: String,
        @SerializedName("address") val address: String,
        @SerializedName("city") val city: String,
        @SerializedName("latitude") val latitude: String,
        @SerializedName("longitude") val longitude: String,
        @SerializedName("name") val name: String,
        @SerializedName("phone") val phone: String,
        @SerializedName("state") val state: String,
        @SerializedName("storeLogoURL") val storeLogoURL: String,
        @SerializedName("zipcode") val zipcode: String,
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
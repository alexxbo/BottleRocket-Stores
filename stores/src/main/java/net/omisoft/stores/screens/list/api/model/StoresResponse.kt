package net.omisoft.stores.screens.list.api.model


import com.google.gson.annotations.SerializedName
import net.omisoft.stores.database.entity.Store

data class StoresResponse(@SerializedName("stores") val stores: List<Store>)
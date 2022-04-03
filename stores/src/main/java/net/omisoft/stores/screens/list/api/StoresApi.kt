package net.omisoft.stores.screens.list.api

import net.omisoft.stores.screens.list.api.model.StoresResponse
import retrofit2.http.GET

interface StoresApi {

    @GET("BR_Android_CodingExam_2015_Server/stores.json")
    suspend fun getStores(): StoresResponse
}
package net.omisoft.stores.screens.list.api

import io.ktor.client.*
import io.ktor.client.request.*
import net.omisoft.bottlerocket.BuildConfig
import net.omisoft.stores.screens.list.api.StoresEndpoints.GET_STORES
import net.omisoft.stores.screens.list.api.model.StoresResponse

object StoresEndpoints {
    const val GET_STORES = "${BuildConfig.BASE_URL}BR_Android_CodingExam_2015_Server/stores.json"
}

interface StoresApi {

    companion object {
        operator fun invoke(
            client: HttpClient,
        ): StoresApi {
            return StoresApiImpl(client)
        }
    }

    suspend fun getStores(): StoresResponse
}

private class StoresApiImpl(
    private val client: HttpClient,
) : StoresApi {

    override suspend fun getStores(): StoresResponse {
        return client.get {
            url(GET_STORES)
        }
    }
}
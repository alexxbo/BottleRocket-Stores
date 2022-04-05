package net.omisoft.stores.common.util

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import net.omisoft.mvptemplate.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor

fun createHttpClient(): HttpClient {
    return HttpClient(OkHttp) {

        engine {
            addInterceptor(loggingInterceptor())
        }

        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }
}

private fun loggingInterceptor() =
    HttpLoggingInterceptor().apply {
        level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
    }
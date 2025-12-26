package com.bura.transparent.network.di

import com.bura.transparent.accounts.network.BuildConfig
import com.bura.transparent.network.data.TransparentAccountsApiService
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.new
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

object NetworkGraph {

    val module = module {
        single { new(::provideHttpClient) }
        single { new(::provideRetrofit) }
        single { new(::provideService) }
    }

    private fun provideHttpClient() = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("WEB-API-key", BuildConfig.API_KEY)
                .addHeader("Accept", "application/json")
                .build()
            chain.proceed(request)
        }
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

    @OptIn(ExperimentalSerializationApi::class)
    private val networkJson = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    private fun converter(): Converter.Factory {
        return networkJson.asConverterFactory("application/json".toMediaType())
    }

    private fun provideRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(converter())
        .build()

    private fun provideService(retrofit: Retrofit): TransparentAccountsApiService {
        return retrofit.create(TransparentAccountsApiService::class.java)
    }

    private const val BASE_URL = "https://webapi.developers.erstegroup.com/api/csas/public/sandbox/v3/"
}
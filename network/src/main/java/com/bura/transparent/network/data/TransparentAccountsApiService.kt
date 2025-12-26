package com.bura.transparent.network.data

import com.bura.transparent.network.model.TransparentAccountsPageDto
import retrofit2.http.GET
import retrofit2.http.Query

interface TransparentAccountsApiService {

    @GET("transparentAccounts")
    suspend fun getTransparentAccounts(
        @Query("size") size: Int = 10,
        @Query("page") page: Int = 0,
    ): TransparentAccountsPageDto
}
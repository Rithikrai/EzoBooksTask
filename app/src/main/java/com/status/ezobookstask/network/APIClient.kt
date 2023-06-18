package com.status.ezobookstask.network

import com.status.ezobookstask.entity.PicsumModelResponse
import com.status.ezobookstask.entity.PicsumModelResponseItem
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface APIClient {

    @GET("list")
    fun getFirstAuthorData(): Response<List<PicsumModelResponse>>

    @GET("list")
    fun login(
        @Query("limit") limit: Int
    ): Call<PicsumModelResponse?>?
}
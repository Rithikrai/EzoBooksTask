package com.status.ezobookstask.network

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

object RetrofitClient {

    val BASE_URL = "https://picsum.photos/v2/"

    val logging = HttpLoggingInterceptor()

    private val okHttpClient = OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor(Interceptor { chain: Interceptor.Chain ->
            val request = chain.request()
            val response = chain.proceed(request)
            val responseBody = response.body
            val source = responseBody!!.source()
            source.request(Long.MAX_VALUE) // Buffer the entire body.
            val buffer = source.buffer()
            val respData = buffer.clone().readString(Charset.defaultCharset())
            var resp: JSONObject? = null
            try {
                resp = JSONObject(respData)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            response
        }).build()

    var gson = GsonBuilder()
        .setLenient()
        .create()

    val instance: APIClient by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

        retrofit.create(APIClient::class.java)
    }
}
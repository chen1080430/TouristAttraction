package com.mason.touristattractionshw.model.network

import com.mason.touristattractionshw.model.AttractionResponse
import com.mason.touristattractionshw.util.LogUtil
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface AttractionApi {

    @Headers("accept: application/json")
    @GET("open-api/{lang}/Attractions/All")
    suspend fun fetchAttraction(@Path("lang") lang : String, @Query("page") page : Int) : AttractionResponse


    companion object {
        val base_url = "https://www.travel.taipei/"

        fun create(): AttractionApi {
            var okHttpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request()
                    val startTime = System.nanoTime()
                    val response = chain.proceed(request)
                    val endTime = System.nanoTime()
                    val duration = endTime - startTime
                    val timeInMillis = TimeUnit.NANOSECONDS.toMillis(duration)
                    LogUtil.d(
                        "OkHttp",
                        "XXXXX> Request url: ${request.url()}, timeInMillis: $timeInMillis"
                    )

                    response
                }
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AttractionApi::class.java)
        }
    }
}
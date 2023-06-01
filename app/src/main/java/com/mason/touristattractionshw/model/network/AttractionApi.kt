package com.mason.touristattractionshw.model.network

import com.mason.touristattractionshw.model.AttractionResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface AttractionApi {

    @Headers("accept: application/json")
    @GET("open-api/{lang}/Attractions/All")
    suspend fun fetchAttraction(@Path("lang") lang : String, @Query("page") page : Int) : AttractionResponse


    companion object {
        val base_url = "https://www.travel.taipei/"

        fun create() : AttractionApi {
            return Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AttractionApi::class.java)
        }
    }
}
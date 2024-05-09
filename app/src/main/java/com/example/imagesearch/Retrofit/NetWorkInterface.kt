package com.example.imagesearch.Retrofit

import com.example.imagesearch.Data.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.QueryMap

interface NetWorkInterface {
    @GET("image")
    suspend fun searchImages (
        @Header("Authorization") apiKey : String,
        @QueryMap param :HashMap<String, String>
    ) : SearchResponse
}
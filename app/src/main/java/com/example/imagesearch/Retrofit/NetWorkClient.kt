package com.example.imagesearch.Retrofit

import com.example.imagesearch.BuildConfig
import com.example.searchimageprogram.Constrant.Constrant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetWorkClient {

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constrant.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(NetWorkInterface::class.java)
}
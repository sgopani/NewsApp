package com.example.newsapp.newsNetwork

import com.example.newsapp.BASE_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsApi {
    private val retrofit=Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()
    val retrofitService:NewsApiService by lazy {
        retrofit.create(NewsApiService::class.java)
    }
}


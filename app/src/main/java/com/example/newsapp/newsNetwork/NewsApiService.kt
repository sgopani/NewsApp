package com.example.newsapp.newsNetwork

import com.example.newsapp.apiKey
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


//https://newsapi.org/v2/top-headlines?country=in&apiKey=fefcd21d742d4dee921924ec9f67b93e

interface NewsApiService {
    @GET("top-headlines?apiKey=$apiKey")
    fun getHealines(@Query("country") country : String): Deferred<NewsProperty>
}
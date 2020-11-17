package com.example.newsapp.newsNetwork

import com.example.newsapp.EVERYTHING
import com.example.newsapp.apiKey
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query


//https://newsapi.org/v2/top-headlines?country=in&apiKey=fefcd21d742d4dee921924ec9f67b93e

interface NewsApiService {
    @GET("top-headlines?apiKey=$apiKey")
    fun getHealines(@Query("country") country : String): Deferred<NewsProperty>

    @GET("top-headlines?apiKey=$apiKey")
    fun getSearchedNews(@Query("q") search : String) : Deferred<NewsProperty>
}

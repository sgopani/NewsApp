package com.example.newsapp.newsNetwork

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://newsapi.org/v2/"
const val apiKey="fefcd21d742d4dee921924ec9f67b93e"
//https://newsapi.org/v2/top-headlines?country=in&apiKey=fefcd21d742d4dee921924ec9f67b93e
private val retrofit=Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()
interface NewsApiService {
    @GET("top-headlines?apiKey=$apiKey")
    fun getHealines(@Query("country") country : String): Deferred<NewsProperty>
}
object NewsApi {
    val retrofitService:NewsApiService by lazy {
        retrofit.create(NewsApiService::class.java)
    }
}
package com.example.newsapp.newsNetwork

data class NewsProperty (
 val status:String,
 val totalResults:Int,
 val articles:List<Articles>
)

data class Articles(
    val author:String,
    val title: String,
    val description:String,
    val articleUrl : String,
    val imageUrl : String,
    val publishedAt : String,
    val content : String
    )
package com.example.newsapp

import com.example.newsapp.newsNetwork.Articles

interface NewsItemClickListener {
    fun onNewsItemClick(article: Articles)
    fun onFavouriteClick(isFavourite : Boolean, article: Articles)

}
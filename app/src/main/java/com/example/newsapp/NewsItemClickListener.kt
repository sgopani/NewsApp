package com.example.newsapp

import com.example.newsapp.database.FavouriteNews
import com.example.newsapp.newsNetwork.Articles

interface NewsItemClickListener {
    fun onNewsItemClick(article: Articles)
    fun onFavouriteClick(isFavourite : Boolean, article: Articles)
}
interface NewsItemClickListenerFav{
    fun onNewsItemClick(favNews: FavouriteNews)
    fun onFavouriteClick(isFavourite : Boolean, favNews: FavouriteNews)
}
package com.example.newsapp.newsFavorite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.database.FavouriteNews
import com.example.newsapp.database.NewsDatabaseDao
import com.example.newsapp.newsNetwork.Articles
import kotlinx.coroutines.*
import java.nio.file.Files.delete

class NewsFavViewModel(private val database: NewsDatabaseDao): ViewModel() {
    private val viewModelJob=Job()
    private val coroutineScope=CoroutineScope(viewModelJob + Dispatchers.Main)
    private val _favNewsList=database.getAllNews()

    private val _selectedNews=MutableLiveData<FavouriteNews>()
    val selectedNews: LiveData<FavouriteNews>
        get()=_selectedNews

    val favNewsList: LiveData<List<FavouriteNews>>
        get()=_favNewsList

    fun onClear() {
        coroutineScope.launch {
            clear()
        }
    }
    fun deleteFavouriteNews(favNews : FavouriteNews){
        coroutineScope.launch {
            delete(favNews)
        }
    }


    private suspend fun delete(favNews: FavouriteNews){
        withContext(Dispatchers.IO){
            database.delete(favNews.news_Id)
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    fun eventNavigateToNewsDetail(favNews: FavouriteNews){
        _selectedNews.value = favNews
    }
    fun addFavouriteNews(favNews: FavouriteNews){
        val favouriteNews = articleToFavouriteNews(favNews)
        coroutineScope.launch {
            withContext(Dispatchers.IO){
                database.insert(favouriteNews)
            }
        }
    }
    private fun articleToFavouriteNews(favNews: FavouriteNews): FavouriteNews {
        Log.i("articleToFavouriteNews","Called")
        return FavouriteNews(
            author = favNews.author,
            title =favNews.title,
            description = favNews.description,
            content = favNews.content,
            articleUrl =favNews.articleUrl,
            imageUrl = favNews.imageUrl,
            publishedAt =favNews.publishedAt
        )

    }

    fun onNavigateToNewsDetails(newsId: Long) {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                _selectedNews.postValue(database.get(newsId))
            }
        }
    }

    fun onNavigateToNewsDetailsCompleted() {
        _selectedNews.value=null

    }
}
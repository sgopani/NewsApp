package com.example.newsapp.newsList

import android.util.Log
import android.widget.ProgressBar
import androidx.lifecycle.*
import com.example.newsapp.NewsApiStatus
import com.example.newsapp.R
import com.example.newsapp.database.FavouriteNews
import com.example.newsapp.database.NewsDatabaseDao
import com.example.newsapp.newsNetwork.Articles
import com.example.newsapp.newsNetwork.NewsApi
import com.example.newsapp.newsNetwork.NewsProperty
import kotlinx.coroutines.*

class NewsViewModel(private val database: NewsDatabaseDao) : ViewModel() {
    private var viewModelJob=Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )


    private val _status = MutableLiveData<NewsApiStatus>()
    val status: LiveData<NewsApiStatus>
        get() = _status

    private val _response = MutableLiveData<NewsProperty>()
    val response : LiveData<NewsProperty>
        get() = _response

    private val _selectedNews = MutableLiveData<Articles>()
    val selectedNews : LiveData<Articles>
        get() = _selectedNews

    private val _favNewsList = database.getAllNews()
    val favNewsList : LiveData<List<FavouriteNews>>
        get() = _favNewsList


    private  val _articleList = MutableLiveData<List<Articles>>()
    val articleList : LiveData<List<Articles>>
        get() = _articleList

    fun eventNavigateToNewsDetail(article: Articles){
        _selectedNews.value = article
    }
    fun eventNavigateToNewsDetailCompleted(){
        _selectedNews.value = null
    }
    init {
        _status.value = NewsApiStatus.LOADING
        getNews()
    }
        private fun getNews(){

            Log.i("getNews","Called")
            coroutineScope.launch {
                val deferred=NewsApi.retrofitService.getHealines("in")
                Log.i("NewsViewModel: ", "The Deferred instance is : ${deferred.toString()}")
                try{
                    _status.value = NewsApiStatus.LOADING
                    _response.value = deferred.await()
                    if(_response.value != null){
                        _articleList.value = _response.value!!.articles
                        _status.value = NewsApiStatus.DONE
                    }
                }catch (t : Throwable){
                    _status.value = NewsApiStatus.ERROR
                    _articleList.value = listOf()
                }
                Log.i("NewsViewModel: ", "The Article List is : ${_articleList.value.toString()}")
            }
        }
    fun addFavouriteNews(article: Articles){
        val favouriteNews = articleToFavouriteNews(article)
        coroutineScope.launch {
            withContext(Dispatchers.IO){
                database.insert(favouriteNews)
            }
        }
    }
    private fun articleToFavouriteNews(article: Articles): FavouriteNews {
        Log.i("articleToFavouriteNews","Called")
        return FavouriteNews(
            author = article.author,
            title =article.title!!,
            description = article.description,
            content = article.content,
            articleUrl =article.articleUrl!!,
            imageUrl = article.imageUrl,
            publishedAt =article.publishedAt!!
        )

    }
    fun deleteFavouriteNews(article: Articles) {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                database.delete(article.articleUrl!!)
            }
        }
    }
    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }
}

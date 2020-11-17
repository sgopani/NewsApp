package com.example.newsapp.newsSearch

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.NewsApiStatus
import com.example.newsapp.database.NewsDatabaseDao
import com.example.newsapp.newsNetwork.Articles
import com.example.newsapp.newsNetwork.NewsApi
import com.example.newsapp.newsNetwork.NewsProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NewsSearchViewModel () : ViewModel() {

    private var viewModelJob=Job()
    private val coroutineScope=CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _response=MutableLiveData<NewsProperty>()
    val response: LiveData<NewsProperty>
        get()=_response

    private val _status = MutableLiveData<NewsApiStatus>()
    val status: LiveData<NewsApiStatus>
        get() = _status

    private val _articleList=MutableLiveData<List<Articles>>()
    val articleList: LiveData<List<Articles>>
        get()=_articleList

    private val _selectedNews = MutableLiveData<Articles>()
    val selectedNews : LiveData<Articles>
        get() = _selectedNews

    fun eventNavigateToNewsDetail(article: Articles){
        _selectedNews.value = article
    }
    fun eventNavigateToNewsDetailCompleted(){
        _selectedNews.value = null
    }
    init {
        _status.value = NewsApiStatus.LOADING
    }
    fun getSearchNews(search: String) {
        coroutineScope.launch {
            val deferred=NewsApi.retrofitService.getSearchedNews(search)
            Log.i("NewsSearchViewModel: ", "The Deferred instance is : $deferred")
            try {
                _status.value = NewsApiStatus.LOADING
                _response.value=deferred.await()
                if (_response.value != null) {
                    _articleList.value=_response.value!!.articles
                    _status.value = NewsApiStatus.DONE
                }
                if(_articleList.value!!.isEmpty()){
                    _status.value = NewsApiStatus.ERROR
                }

            } catch (t: Throwable) {
                _status.value = NewsApiStatus.ERROR
                _articleList.value=listOf()
            }
            Log.i("NewsSearchViewModel: ", "The Article List is : ${_articleList.value.toString()}")

        }
    }
}
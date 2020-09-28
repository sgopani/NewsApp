package com.example.newsapp

import androidx.lifecycle.*

class NewsViewModel(): ViewModel() {

    init{

    }

    override fun onCleared() {
        super.onCleared()
    }
}
//    private val _newsLatest=MutableLiveData<List<News>>()
//    val newsLatest: LiveData<List<News>>
//        get()=_newsLatest
//
//    fun getNews() {
//        val news=newslist.getNews()
//
//    }
//}
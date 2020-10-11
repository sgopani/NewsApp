package com.example.newsapp.newsList

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.newsapp.newsNetwork.NewsApi
import com.example.newsapp.newsNetwork.NewsProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel(): ViewModel() {

    private var viewModelJob=Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    private val _response=MutableLiveData<String>()
    val response: LiveData<String>
        get()=_response


    init {
        getNews()
    }

    fun getNews() {
        Log.i("Shubh", "Success")
        coroutineScope.launch {
            //val deferred=NewsApi.retrofitService.getHealines("in")
            var getpropertiesDeferred=NewsApi.retrofitService.getHealines("in")

            Log.i("NewsViewModel: ", "The Deferred instance is : ${getpropertiesDeferred.toString()}")
            try {
                var listResult=getpropertiesDeferred.await()
                Log.i("Shubh", "Success${listResult.articles}")
                _response.value="Success: ${listResult.articles} "
            } catch (t: Throwable) {
                _response.value="failure" + t.message
            }

        }
        _response.value="News API"
    }
    override fun onCleared() {
        //viewModelJob.cancel()
        super.onCleared()
    }
}
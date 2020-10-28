package com.example.newsapp.newsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.database.NewsDatabaseDao

@Suppress("UNCHECKED_CAST")
class NewsViewModelFactory():ViewModelProvider.NewInstanceFactory(){
    private lateinit var database : NewsDatabaseDao

    constructor(database : NewsDatabaseDao) : this(){
        this.database = database
    }
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)){
            return NewsViewModel( database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}




package com.example.newsapp.newsFavorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.database.NewsDatabaseDao

@Suppress("UNCHECKED_CAST")
class NewsFavViewModelFactory(): ViewModelProvider.Factory  {
    private lateinit var database : NewsDatabaseDao

    constructor(database : NewsDatabaseDao) : this(){
        this.database = database
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsFavViewModel::class.java)){
            return NewsFavViewModel(database) as T
        }
        throw IllegalArgumentException("No such viewModel")
    }
}
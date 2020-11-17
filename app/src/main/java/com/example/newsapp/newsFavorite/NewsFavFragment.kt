package com.example.newsapp.newsFavorite

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.NewsItemClickListener
import com.example.newsapp.NewsItemClickListenerFav
import com.example.newsapp.R
import com.example.newsapp.database.FavouriteNews
import com.example.newsapp.database.NewsDatabase
import com.example.newsapp.favouriteNewsToArticle
import com.example.newsapp.newsNetwork.Articles

class NewsFavFragment :Fragment(){
    private lateinit var newsFavView:View
    private var gridLayoutManager: GridLayoutManager?=null
    private lateinit var viewModel : NewsFavViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        newsFavView=inflater.inflate(R.layout.fragmet_news_favorite,container,false)

        val database = NewsDatabase.getInstance(this.requireActivity().application).newsDatabaseDao
        val viewModelFactory = NewsFavViewModelFactory(database)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(NewsFavViewModel::class.java)
        val adapter = NewsFavAdapter(NewsFavAdapter.OnClickListener{ favNews ->
            viewModel.onNavigateToNewsDetails(favNews.news_Id)},getNewsItemClickListener())
        val newsFavList=newsFavView.findViewById<RecyclerView>(R.id.newsFavList)
        newsFavList.adapter=adapter
        gridLayoutManager=GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
        newsFavList.layoutManager=gridLayoutManager
        newsFavList.setHasFixedSize(true)

        viewModel.selectedNews.observe(this.viewLifecycleOwner, Observer {favNews ->
            if (favNews != null) {
                val article = favouriteNewsToArticle(favNews)
                val navController=this.findNavController()
                navController.navigate(NewsFavFragmentDirections.actionNewsFavFragmentToNewsInfoFragment23(article))
                viewModel.onNavigateToNewsDetailsCompleted()
            }
        })
        viewModel.favNewsList.observe(this.viewLifecycleOwner, Observer{favNewsList ->
            adapter.submitList(favNewsList)
        })
        viewModel.favNewsList.observe(this.viewLifecycleOwner, Observer { favNewsList ->
            adapter.submitFavouriteNewsList(favNewsList)
        })
        activity?.title="Favorite News"
        this.setHasOptionsMenu(true)
        return newsFavView
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.clearoption, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       if(item.itemId==R.id.clearNews){
           viewModel.onClear()
           return true
       }
        return false
    }
    private fun getNewsItemClickListener(): NewsItemClickListenerFav {
        return object : NewsItemClickListenerFav {
            override fun onNewsItemClick(favNews: FavouriteNews) {
                viewModel.eventNavigateToNewsDetail(favNews)
            }

            override fun onFavouriteClick(isFavourite: Boolean, favNews: FavouriteNews) {
                if (isFavourite) {
                    viewModel.deleteFavouriteNews(favNews)
                } else {
                    Toast.makeText(context,"Not deleted",Toast.LENGTH_SHORT).show()
                    viewModel.addFavouriteNews(favNews)
                }

            }

        }

    }
}


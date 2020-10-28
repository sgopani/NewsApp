package com.example.newsapp.newsList

import android.annotation.SuppressLint
import android.os.Bundle
import android.transition.TransitionInflater

import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.NewsApiStatus
import com.example.newsapp.NewsItemClickListener
import com.example.newsapp.R
import com.example.newsapp.database.FavouriteNews
import com.example.newsapp.database.NewsDatabase
import com.example.newsapp.newsNetwork.Articles
import kotlinx.android.synthetic.main.fragment_news_list.*
import kotlinx.android.synthetic.main.fragment_news_list.view.*

class NewsList : Fragment() {
    //private var _binding: FragmentNewsListBinding? = null
    //private lateinit var factory: NewsViewModelFactory
    private var gridLayoutManager: GridLayoutManager?=null
    private lateinit var newsListView: View
    lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val transitionListInflater=TransitionInflater.from(requireContext())
        newsListView=inflater.inflate(R.layout.fragment_news_list, container, false)
        exitTransition=transitionListInflater.inflateTransition(R.transition.fade)
        activity?.title="Latest News"
        setHasOptionsMenu(true)
        return newsListView
    }

    //val navController = this.findNavController()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Toast.makeText(this.activity,"onViewCreated",Toast.LENGTH_LONG).show()
        //val database = NewsDatabase.getInstance(this.requireActivity().applicationContext).newsDatabaseDao
        val database=NewsDatabase.getInstance(this.requireContext().applicationContext).newsDatabaseDao
        val viewModelFactory = NewsViewModelFactory(database)
        viewModel=ViewModelProvider(this,viewModelFactory).get(NewsViewModel::class.java)
        gridLayoutManager=GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
        newsList.layoutManager=gridLayoutManager
        newsList.setHasFixedSize(true)
        viewModel.articleList.observe(viewLifecycleOwner, Observer { it ->
            it?.let {
                //Log.i("Viemodel article total", "${viewModel.articleList.value!!}")
                newsList.adapter=NewsAdapter(
                    viewModel.articleList.value,
                    context?.applicationContext!!,getNewsItemClickListener())
            }
            //NewsAdapter.OnClickListener{ article ->
            //   viewModel.eventNavigateToNewsDetail(article)
            //})

        })
        viewModel.selectedNews.observe(this.viewLifecycleOwner, Observer { article ->
            if (article != null) {
                val navController=this.findNavController()
                navController.navigate(NewsListDirections.actionNewsList2ToNewsInfoFragment2(article))
                NewsListDirections.actionNewsList2ToNewsInfoFragment2(article)
                viewModel.eventNavigateToNewsDetailCompleted()
            }
        })
        viewModel.status.observe(this.viewLifecycleOwner, Observer { status ->
            checkInternet(status)
        })
        viewModel.favNewsList.observe(this.viewLifecycleOwner, Observer {favNewsList ->

        })
    }
    private fun checkInternet(status: NewsApiStatus) {
        val statusImageView=newsListView.status_image
        val newsList=newsListView.newsList
        //val newsList=requireView().findViewById<RecyclerView>(R.id.newsList)
        when (status) {
            NewsApiStatus.LOADING -> {
                //Toast.makeText(this.context,"Called",Toast.LENGTH_LONG).show()
                statusImageView.visibility=View.GONE
                newsList.visibility=View.VISIBLE
                statusImageView.setImageResource(R.drawable.progress_animation)
            }
            NewsApiStatus.ERROR -> {
                Toast.makeText(
                    this.context,
                    "Please Check Your Internet Connection",
                    Toast.LENGTH_LONG
                ).show()
                statusImageView.visibility=View.VISIBLE
                newsList.visibility=View.INVISIBLE
                statusImageView.setImageResource(R.drawable.no_internet_connection)
            }
            NewsApiStatus.DONE -> {
                statusImageView.visibility=View.GONE
                newsList.visibility=View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        //Toast.makeText(this.context,"menu created",Toast.LENGTH_LONG).show()
        inflater.inflate(R.menu.favorite, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorite -> onClick()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onClick(): Boolean {
        findNavController().navigate(R.id.action_newsList2_to_newsFavFragment)
        return true
    }

    private fun getNewsItemClickListener(): NewsItemClickListener {
        return object : NewsItemClickListener {
            override fun onNewsItemClick(article: Articles) {
                viewModel.eventNavigateToNewsDetail(article)
            }

            override fun onFavouriteClick(isFavourite: Boolean, article: Articles) {
                if (isFavourite) {
                    //viewModel.deleteFavouriteNews(article)
                } else {
                    //viewModel.addFavouriteNews(article)
                }

            }

        }

    }
}









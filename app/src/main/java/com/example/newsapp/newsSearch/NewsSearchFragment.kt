package com.example.newsapp.newsSearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.NewsApiStatus
import com.example.newsapp.R
import com.example.newsapp.hideKeyboard
import com.example.newsapp.newsList.NewsListDirections
import kotlinx.android.synthetic.main.news_search_design.view.*

class NewsSearchFragment: Fragment() {
    private lateinit var newsSearchView:View
    private var gridLayoutManager: GridLayoutManager?=null
    private lateinit var viewModel: NewsSearchViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {


        newsSearchView=inflater.inflate(R.layout.news_search_design,container,false)
        activity?.title="Search News"
        val adapter=NewsSearchAdapter(NewsSearchAdapter.OnClickListener{ article ->
            viewModel.eventNavigateToNewsDetail(article)
            viewModel.eventNavigateToNewsDetailCompleted()
        })
        val searchRecyclerView=newsSearchView.findViewById<RecyclerView>(R.id.newsSearchRecyclerview)
        searchRecyclerView.adapter=adapter
        gridLayoutManager=GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
        searchRecyclerView.layoutManager=gridLayoutManager
        searchRecyclerView.setHasFixedSize(true)

        val searchView = newsSearchView.findViewById<SearchView>(R.id.searchView)
        searchView.isSubmitButtonEnabled = true

        searchView.setOnQueryTextListener(getQueryTextListener(searchView))
        viewModel=ViewModelProvider(requireActivity()).get(NewsSearchViewModel::class.java)

        viewModel.articleList.observe(this.viewLifecycleOwner, Observer{articleList ->
            adapter.submitList(articleList)
        })

        viewModel.status.observe(this.viewLifecycleOwner, Observer { status ->
            checkInternet(status)
        })
        viewModel.selectedNews.observe(this.viewLifecycleOwner, Observer { article ->
            if (article != null) {
                val navController=this.findNavController()
                navController.navigate(NewsSearchFragmentDirections.actionNewsSearchFragmentToNewsInfoFragment2(article))
                //NewsListDirections.actionNewsList2ToNewsInfoFragment2(article)
                NewsSearchFragmentDirections.actionNewsSearchFragmentToNewsInfoFragment2(article)
                viewModel.eventNavigateToNewsDetailCompleted()
            }
        })
        return newsSearchView
    }

    override fun onDestroyView() {
        hideKeyboard(activity)
        super.onDestroyView()
    }
    private fun checkInternet(status: NewsApiStatus){
        val searchCheck=newsSearchView.findViewById<TextView>(R.id.searchCheckTextView)
        val newsSearchRecycler=newsSearchView.findViewById<RecyclerView>(R.id.newsSearchRecyclerview)
        when (status) {
            NewsApiStatus.LOADING -> {
                //Toast.makeText(this.context,"Called",Toast.LENGTH_LONG).show()
                newsSearchRecycler.visibility=View.GONE
            }
            NewsApiStatus.ERROR -> {
                searchCheck.visibility=View.VISIBLE
                newsSearchRecycler.visibility=View.GONE
                Toast.makeText(this.requireContext(),"No articles available",Toast.LENGTH_SHORT).show()
            }
            NewsApiStatus.DONE -> {
                searchCheck.visibility=View.GONE
                newsSearchRecycler.visibility=View.VISIBLE
            }
        }
    }

    private fun getQueryTextListener(searchView: SearchView): SearchView.OnQueryTextListener {
        return object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                viewModel.getSearchNews(searchView.query.toString())
                searchView.clearFocus()
                return true
            }
        }
    }

}
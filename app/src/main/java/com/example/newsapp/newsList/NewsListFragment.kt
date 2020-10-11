package com.example.newsapp.newsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.newsNetwork.Articles
import kotlinx.android.synthetic.main.fragment_news_list.*


@Suppress("NAME_SHADOWING")
class NewsList : Fragment() {
    // TODO: Rename and change types of parameters
    //private var _binding: FragmentNewsListBinding? = null
    //private lateinit var factory: NewsViewModelFactory
    private var gridLayoutManager: GridLayoutManager?=null
    private lateinit var  viewModel: NewsViewModel
    private lateinit var articles: List<Articles>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View=inflater.inflate(R.layout.fragment_news_list, container, false)
        activity?.title="Latest News"
        ViewModelProvider(this).get(NewsViewModel::class.java)
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsList.adapter=NewsAdapter()
        //viewModel.getNews()
        gridLayoutManager=GridLayoutManager(context,2,LinearLayoutManager.VERTICAL,false)
        newsList.layoutManager=gridLayoutManager
        newsList.setHasFixedSize(true)
        next_button.setOnClickListener {
            view.findNavController().navigate(R.id.action_newsList2_to_newsInfoFragment2)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}







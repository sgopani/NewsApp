package com.example.newsapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.newsapp.databinding.FragmentNewsListBinding


class NewsList : Fragment() {
    // TODO: Rename and change types of parameters
    //private var _binding: FragmentNewsListBinding? = null
    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:FragmentNewsListBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_news_list,container,false)
        binding.nextButton.setOnClickListener {view:View->
            view.findNavController().navigate(R.id.action_newsList2_to_newsInfoFragment2)

        }
        viewModel = ViewModelProvider(this).get( NewsViewModel::class.java)
        activity?.title = "Latest News"
        return binding.root

    }

    override fun onDestroyView() {

        super.onDestroyView()
    }
}




package com.example.newsapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.newsapp.databinding.FragmentNewsInfoBinding
import com.example.newsapp.databinding.FragmentNewsListBinding


class NewsInfoFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        //_binding=FragmentNewsInfoBinding.inflate(inflater,container,false)
        val binding:FragmentNewsInfoBinding =DataBindingUtil.inflate(inflater,R.layout.fragment_news_info,container,false)
        activity?.title = "News Description"
        return binding.root
    }
    override fun onDestroyView(){
        super.onDestroyView()
    }
}
package com.example.newsapp.NewsInfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentNewsInfoBinding


class NewsInfoFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        //_binding=FragmentNewsInfoBinding.inflate(inflater,container,false)
        //val binding:FragmentNewsInfoBinding =DataBindingUtil.inflate(inflater,
        //  R.layout.fragment_news_info,container,false)
        val view:View=inflater.inflate(R.layout.fragment_news_info,container,false)
        activity?.title = "News Description"
        return view
    }
    override fun onDestroyView(){
        super.onDestroyView()
    }
}
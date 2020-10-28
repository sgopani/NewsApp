package com.example.newsapp.newsInfo

import android.annotation.SuppressLint
import android.media.Image
import android.os.Bundle
import android.text.Html
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.newsapp.R
import com.example.newsapp.newsInfo.NewsInfoFragmentArgs.Companion.fromBundle
import org.w3c.dom.Text


class NewsInfoFragment : Fragment() {
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        //_binding=FragmentNewsInfoBinding.inflate(inflater,container,false)
        //val binding:FragmentNewsInfoBinding =DataBindingUtil.inflate(inflater,
          //R.layout.fragment_news_info,container,false)
        val transitionInfoInflator:TransitionInflater=TransitionInflater.from(requireContext())
        enterTransition = transitionInfoInflator.inflateTransition(R.transition.slide_right)
        val view:View=inflater.inflate(R.layout.fragment_news_info,container,false)
        val article =NewsInfoFragmentArgs.fromBundle(requireArguments()).articles
        activity?.title ="News"

        val viewModelFactory = NewsInfoViewModelFactoryModelFactory(article)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(NewsInfoViewModel::class.java)
        val newsImage=view.findViewById<ImageView>(R.id.news_info_image)
        val newsTitle=view.findViewById<TextView>(R.id.news_info_title)
        val newsDescription=view.findViewById<TextView>(R.id.news_info_description)
        val newsUrl=view.findViewById<TextView>(R.id.news_article_url)
        newsUrl.isClickable
        viewModel.selectedNews.observe(viewLifecycleOwner, Observer {
            viewModel.getSetImage(newsImage)
           newsDescription.text = viewModel.newsDescription
            newsTitle.text=viewModel.newsTitle
            newsUrl.text = "Read More:\n${(viewModel.articleUrl)}"
        })

        return view
    }
    override fun onDestroyView(){
        super.onDestroyView()
    }
}
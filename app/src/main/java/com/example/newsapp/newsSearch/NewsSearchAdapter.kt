package com.example.newsapp.newsSearch

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.DiffCallback
import com.example.newsapp.R
import com.example.newsapp.loadImage
import com.example.newsapp.newsNetwork.Articles

class NewsSearchAdapter (private val onClickListener: OnClickListener):
    ListAdapter<Articles, NewsSearchAdapter.NewsSearchViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsSearchViewHolder {
        return NewsSearchViewHolder.createViewHolder(parent)
    }

    override fun onBindViewHolder(holder: NewsSearchViewHolder, position: Int) {
        val articlesadapter=getItem(position)
        holder.bind(articlesadapter)
        holder.searchText.text=articlesadapter!!.title
        holder.itemView.setOnClickListener {
            //Log.i("Image ","clicked")
            onClickListener.onClick(articlesadapter)
        }
    }

    class NewsSearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val searchImage=itemView.findViewById<ImageView>(R.id.newsImage)!!
        val searchText=itemView.findViewById<TextView>(R.id.newsTitle)!!
        val favStar=itemView.findViewById<CheckBox>(R.id.fav_CheckBox)!!

        companion object {
            fun createViewHolder(parent: ViewGroup): NewsSearchViewHolder {
                val newsSearchView=LayoutInflater.from(parent.context)
                    .inflate(R.layout.news_list_degsign, parent, false)
                return NewsSearchViewHolder(newsSearchView)
            }
        }

        fun bind(article: Articles) {
            article.let {
                favStar.visibility=View.GONE
                searchText.text=it.title
                var imgUri: Uri?=null
                it.imageUrl?.let {
                    imgUri=it.toUri().buildUpon().scheme("https").build()
                }

                if (imgUri == null) {
                    searchImage.setImageResource(R.drawable.try_later)
                    this.setIsRecyclable(false)
                } else {
                    imgUri?.let { uri ->
                        loadImage(uri, searchImage)
                    }
                }
            }

        }
    }

    class OnClickListener(val clickListener : (article : Articles) -> Unit){
        fun onClick(article: Articles) = clickListener(article)
    }

}

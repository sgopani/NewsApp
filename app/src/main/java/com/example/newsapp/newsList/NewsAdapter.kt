package com.example.newsapp.newsList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.databinding.NewsListDegsignBinding
import com.example.newsapp.newsNetwork.Articles

class NewsAdapter:RecyclerView.Adapter<NewsAdapter.MyViewHolder>(){
    private val articles=listOf<Articles>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val inflater :LayoutInflater= LayoutInflater.from(parent.context)
        val binding=NewsListDegsignBinding.inflate(inflater,parent,false)
        //val view:View=inflater.inflate(R.layout.news_list_degsign,parent,false)
        return MyViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return articles.size
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.newsTitle.text=articles[position].title
        holder.cardview
        //holder.newsDescription.text=articles[position].description
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var newsTitle=itemView.findViewById<TextView>(R.id.newsTitle)!!
        var cardview=itemView.findViewById<CardView>(R.id.cardView)
        //var newsDescription=itemView.findViewById<TextView>(R.id.newsDescription)!!



    }
}
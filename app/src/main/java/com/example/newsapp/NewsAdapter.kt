package com.example.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.NewsListDegsignBinding

class NewsAdapter(val news:List<News>):RecyclerView.Adapter<NewsAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val inflater :LayoutInflater= LayoutInflater.from(parent.context)
        val binding=NewsListDegsignBinding.inflate(inflater,parent,false)
        //val view:View=inflater.inflate(R.layout.news_list_degsign,parent,false)
        return MyViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
    return news.size
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.newsTitle.text=news[position].title
            holder.newsDescription.text=news[position].description
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var newsTitle=itemView.findViewById<TextView>(R.id.newsTitle)!!
        var newsDescription=itemView.findViewById<TextView>(R.id.newsDescription)!!



    }
}


package com.example.newsapp.newsInfo

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.newsNetwork.Articles

@Suppress("UNCHECKED_CAST")
class NewsInfoViewModelFactoryModelFactory(val article: Articles) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NewsInfoViewModel::class.java)){
            return NewsInfoViewModel(article) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}

//<?xml version="1.0" encoding="utf-8"?>
//<layout xmlns:android="http://schemas.android.com/apk/res/android"
//xmlns:app="http://schemas.android.com/apk/res-auto"
//xmlns:tools="http://schemas.android.com/tools">
//
//<androidx.constraintlayout.widget.ConstraintLayout
//android:id="@+id/cardView"
//android:layout_width="180dp"
//android:layout_height="248dp"
//android:layout_marginTop="8dp"
//android:layout_marginEnd="8dp"
//android:layout_marginBottom="8dp"
//app:layout_constraintBottom_toBottomOf="parent"
//app:layout_constraintEnd_toEndOf="parent"
//app:layout_constraintStart_toStartOf="parent"
//app:layout_constraintTop_toTopOf="parent">
//
//<ImageView
//android:id="@+id/newsImage"
//android:layout_width="0dp"
//android:layout_height="0dp"
//android:layout_marginStart="8dp"
//android:layout_marginTop="8dp"
//android:layout_marginEnd="8dp"
//android:layout_marginBottom="8dp"
//android:adjustViewBounds="true"
//android:contentDescription="@string/news_image"
//android:foregroundGravity="fill_horizontal|fill_vertical|fill"
//android:paddingBottom="8dp"
//android:scaleType="centerCrop"
//app:layout_constraintBottom_toTopOf="@+id/newsTitle"
//app:layout_constraintEnd_toEndOf="parent"
//app:layout_constraintStart_toStartOf="parent"
//app:layout_constraintTop_toTopOf="parent"
//tools:srcCompat="@tools:sample/backgrounds/scenic" />
//
//<TextView
//android:id="@+id/newsTitle"
//style="@style/TextAppearance.MaterialComponents.Subtitle2"
//android:layout_width="0dp"
//android:layout_height="wrap_content"
//android:layout_marginStart="8dp"
//android:layout_marginEnd="8dp"
//android:layout_marginBottom="8dp"
//android:text="@string/News_headline"
//android:textSize="12sp"
//app:layout_constraintBottom_toBottomOf="parent"
//app:layout_constraintEnd_toEndOf="parent"
//app:layout_constraintStart_toStartOf="parent" />
//</androidx.constraintlayout.widget.ConstraintLayout>
//
//
//</layout>




//import androidx.core.view.get
//
//
//import androidx.navigation.Navigation.findNavController
//
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.example.newsapp.R
////import com.example.newsapp.databinding.NewsListDegsignBinding
//import com.example.newsapp.newsNetwork.Articles
//
////import com.example.newsapp.newsNetwork.Article
//
//class NewsAdapter(private val articleList: List<Articles>?, private val context: Context, private val onClickListener:OnClickListener) :RecyclerView.Adapter<NewsAdapter.MyViewHolder>(){
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//
//        val inflater: LayoutInflater= LayoutInflater.from(parent.context)
//        //val binding=NewsListDegsignBinding.inflate(inflater,parent,false)
//        val view: View=inflater.inflate(R.layout.news_list_degsign,parent,false)
//        //val view:View=View.inflate(parent.context,R.layout.news_list_degsign,parent)
//        //return MyViewHolder(binding.root)
//        return MyViewHolder(view)
//    }
//    override fun getItemCount(): Int {
//        Log.i("Articles","size=${articleList!!.size}")
//        return articleList.size
//    }
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val articlesadapter=articleList?.get(position)
//        Log.i("Articles","size=${articlesadapter!!.title}")
//        holder.newsTitle.text=articlesadapter.title
//        //holder.cardView[position]
//        Glide.with(context).load(articlesadapter.imageUrl)
//            .apply { fitCenter().override(600, 200)
//                .placeholder(R.drawable.progress_animation).error(R.drawable.try_later) }
//            .into(holder.image)
//
//        holder.itemView.setOnClickListener {
//            findNavController(it).navigate(R.id.action_newsList2_to_newsInfoFragment2)
//        }
//    }
//
//    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
//        var newsTitle: TextView=itemView.findViewById(R.id.newsTitle)
//        var image: ImageView=itemView.findViewById(R.id.newsImage)
//        //var cardView:CardView=itemView.findViewById(R.id.cardView)
//    }
//    class OnClickListener(val clickListener : (article : Articles) -> Unit){
//        fun onClick(article: Articles) = clickListener(article)
//    }
//}






//<TextView
//android:id="@+id/news_info_description"
//android:layout_width="0dp"
//android:layout_height="wrap_content"
//android:layout_marginStart="8dp"
//android:layout_marginTop="8dp"
//android:layout_marginEnd="16dp"
//android:layout_marginBottom="16dp"
//android:text="@string/news_description"
//app:layout_constraintBottom_toBottomOf="parent"
//app:layout_constraintEnd_toEndOf="parent"
//app:layout_constraintStart_toStartOf="parent"
//app:layout_constraintTop_toBottomOf="@+id/news_info_image" />
//
//<TextView
//android:id="@+id/news_article_url"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:layout_marginStart="16dp"
//android:layout_marginTop="16dp"
//android:layout_marginEnd="8dp"
//android:layout_marginBottom="8dp"
//android:text="@string/news_article_url"
//app:layout_constraintBottom_toBottomOf="parent"
//app:layout_constraintEnd_toEndOf="parent"
//app:layout_constraintStart_toEndOf="@+id/read_more"
//app:layout_constraintTop_toBottomOf="@+id/news_info_description" />
//
//<TextView
//android:id="@+id/read_more"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:layout_marginStart="8dp"
//android:layout_marginTop="16dp"
//android:layout_marginBottom="8dp"
//android:text="@string/read_more"
//app:layout_constraintBottom_toBottomOf="parent"
//app:layout_constraintStart_toStartOf="parent"
//app:layout_constraintTop_toBottomOf="@+id/news_info_description" />





//<?xml version="1.0" encoding="utf-8"?>
//
//<layout xmlns:android="http://schemas.android.com/apk/res/android"
//xmlns:app="http://schemas.android.com/apk/res-auto"
//xmlns:tools="http://schemas.android.com/tools">
//<data>
//<variable
//name="newslist"
//type="com.example.newsapp.newsList.NewsList"/>
//</data>
//
//<androidx.constraintlayout.widget.ConstraintLayout
//android:id="@+id/frameLayout"
//android:layout_width="match_parent"
//android:layout_height="match_parent"
//tools:context=".newsList.NewsList">
//
//<androidx.recyclerview.widget.RecyclerView
//android:id="@+id/newsList"
//android:layout_width="0dp"
//android:layout_height="0dp"
//android:layout_marginStart="8dp"
//android:layout_marginEnd="8dp"
//app:layout_constraintBottom_toBottomOf="parent"
//app:layout_constraintEnd_toEndOf="parent"
//app:layout_constraintStart_toStartOf="parent"
//app:layout_constraintTop_toTopOf="parent" />
//
//<ImageView
//android:id="@+id/status_image"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:contentDescription="@string/status_image"
//android:visibility="gone"
//app:layout_constraintBottom_toBottomOf="parent"
//app:layout_constraintEnd_toEndOf="@+id/newsList"
//app:layout_constraintStart_toStartOf="parent"
//app:layout_constraintTop_toTopOf="@+id/newsList"
//app:srcCompat="@drawable/no_internet_connection"
//tools:srcCompat="@tools:sample/avatars" />
//
//</androidx.constraintlayout.widget.ConstraintLayout>
//</layout>
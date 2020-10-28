package com.example.newsapp.newsList
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import com.example.newsapp.NewsItemClickListener
import com.example.newsapp.R
import com.example.newsapp.database.FavouriteNews
import com.example.newsapp.newsNetwork.Articles
//private val onClickListener:OnClickListener
class NewsAdapter(private val articleList: List<Articles>?,private val context: Context,
                  private val newsItemClickListeners: NewsItemClickListener) :RecyclerView.Adapter<NewsAdapter.MyViewHolder>(){
    lateinit var favNewsList : List<FavouriteNews>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater:LayoutInflater= LayoutInflater.from(parent.context)
        val view:View=inflater.inflate(R.layout.news_list_degsign,parent,false)
        return MyViewHolder(view)
    }
    override fun getItemCount(): Int {
        //Log.i("Articles","size=${articleList!!.size}")
        return articleList!!.size
    }
    fun submitFavouriteNewsList(favNewsList : List<FavouriteNews>){
        this.favNewsList  = favNewsList
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val articlesadapter=articleList?.get(position)
        holder.newsTitle.text=articlesadapter!!.title
        Glide.with(context).load(articlesadapter.imageUrl)
            .apply {
                fitCenter().override(600, 200)
                    .placeholder(R.drawable.progress_animation).error(R.drawable.try_later)
            }
            .into(holder.image)
        holder.itemView.setOnClickListener {
            //onClickListener.onClick(articlesadapter)
            newsItemClickListeners.onNewsItemClick(articlesadapter)
        }

        holder.favImage.setOnClickListener {
            Toast.makeText(this.context, (position as Int).toString(),Toast.LENGTH_SHORT).show();
            holder.isFavourite=holder.isFavourite.not()
            holder.isFavourite = checkFavouriteOrNot(articlesadapter, favNewsList)
            holder.setFavouriteIcon()
        }

    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var newsTitle:TextView=itemView.findViewById(R.id.newsTitle)
        var image:ImageView=itemView.findViewById(R.id.newsImage)
        var favImage:ImageView=itemView.findViewById(R.id.fav_image)

        var isFavourite = false
        fun setFavouriteIcon(){
            if (isFavourite){
                favImage.setImageResource(R.drawable.ic_baseline_star_24)
            }else{
                favImage.setImageResource(R.drawable.ic_outline_star_border_24)
            }
        }
    }
    private fun checkFavouriteOrNot(article: Articles?, favNewsList: List<FavouriteNews>): Boolean{
        for (favNews in favNewsList){
            if (article?.articleUrl == favNews.articleUrl){
                return true
            }
        }
        return false
    }
    //class OnClickListener(val clickListener : (article : Articles) -> Unit){
      //  fun onClick(article: Articles) = clickListener(article)
    //}
}





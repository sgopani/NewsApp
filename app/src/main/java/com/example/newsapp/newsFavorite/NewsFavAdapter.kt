package com.example.newsapp.newsFavorite

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.NewsItemClickListener
import com.example.newsapp.NewsItemClickListenerFav
import com.example.newsapp.R
import com.example.newsapp.database.FavouriteNews
import com.example.newsapp.loadImage


class NewsFavAdapter(private val onClickListener: OnClickListener,private val newsItemClickListeners: NewsItemClickListenerFav) : ListAdapter<FavouriteNews,
        NewsFavAdapter.FavouriteNewsHolder>(DiffCallback) {
    lateinit var favNewsList: List<FavouriteNews>
    //lateinit var newsItemClickListeners: NewsItemClickListenerFav
    //lateinit var articles: Articles
    object DiffCallback : DiffUtil.ItemCallback<FavouriteNews>() {
        override fun areItemsTheSame(oldItem: FavouriteNews, newItem: FavouriteNews): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem: FavouriteNews, newItem: FavouriteNews): Boolean {
            return oldItem.news_Id == newItem.news_Id
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsFavAdapter.FavouriteNewsHolder {
        return FavouriteNewsHolder.createViewHolder(parent)
    }
    fun submitFavouriteNewsList(favNewsList: List<FavouriteNews>) {
        this.favNewsList=favNewsList
    }

    override fun onBindViewHolder(holder: NewsFavAdapter.FavouriteNewsHolder, position: Int) {
        val favNews=getItem(position)
        holder.bind(favNews,favNewsList)
        holder.itemView.setOnClickListener{
            onClickListener.onClick(favNews)
        }
        holder.fav_checkBox.setOnClickListener {
            Log.i("CheckBox","${holder.adapterPosition}Position")
            newsItemClickListeners.onFavouriteClick(holder.isFavourite,favNews)
            holder.setFavouriteIcon()
            holder.isFavourite=holder.isFavourite.not()
        }
    }

    class FavouriteNewsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //val fav_cardView=itemView.findViewById<CardView>(R.id.fav_cardView)
        private var favImage=itemView.findViewById<ImageView>(R.id.new_favorite_image)
        private val favTitle=itemView.findViewById<TextView>(R.id.fav_title)
        var fav_checkBox=itemView.findViewById<CheckBox>(R.id.FavFragment_Checkbox)!!
        var isFavourite:Boolean=false
        companion object {
            fun createViewHolder(parent: ViewGroup): FavouriteNewsHolder {
                val favNewsview=LayoutInflater.from(parent.context)
                    .inflate(R.layout.news_favorite_recyclerview, parent, false)
                return FavouriteNewsHolder(favNewsview)
            }
        }


        fun bind(favouriteNews: FavouriteNews,favNewsList: List<FavouriteNews>) {
            isFavourite=checkFavouriteOrNot(favouriteNews,favNewsList)
            setFavouriteIcon()
            favouriteNews.let {
                favTitle.text=it.title
                var imgUri: Uri?=null
                it.imageUrl?.let {
                    imgUri=it.toUri().buildUpon().scheme("https").build()
                }
                if (imgUri == null) {
                    favImage.setImageResource(R.drawable.try_later)
                    this.setIsRecyclable(false)
                } else {
                    imgUri?.let { uri ->
                        loadImage(uri, favImage)
                    }
                }

            }
        }
        fun setFavouriteIcon() {
            if (isFavourite) {
                //Toast.makeText(itemView.context,"Added to the Favorite News",Toast.LENGTH_SHORT).show()
                fav_checkBox.setBackgroundResource(R.drawable.ic_baseline_star_24)
                //favImage.setBackgroundResource(R.drawable.ic_baseline_star_24)
            }else {
                //Toast.makeText(itemView.context,"Removed from Favorite News",Toast.LENGTH_SHORT).show()
                fav_checkBox.setBackgroundResource(R.drawable.ic_outline_star_border_24)
            }
        }
        private fun checkFavouriteOrNot(favNewsArticle: FavouriteNews, favNewsList: List<FavouriteNews>
        ): Boolean {
            for (favNews in favNewsList) {
                if (favNewsArticle.articleUrl == favNews.articleUrl) {
                    return true
                }
            }
            return false
        }


    }
    class OnClickListener(val clickListener : (favNews : FavouriteNews) -> Unit){
        fun onClick(favNews : FavouriteNews) = clickListener(favNews)
    }
}
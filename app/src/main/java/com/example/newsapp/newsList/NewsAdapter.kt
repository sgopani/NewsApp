package com.example.newsapp.newsList
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.net.toUri
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.DiffCallback
import com.example.newsapp.NewsItemClickListener
import com.example.newsapp.R
import com.example.newsapp.database.FavouriteNews
import com.example.newsapp.loadImage
import com.example.newsapp.newsNetwork.Articles


class NewsAdapter(private val newsItemClickListeners: NewsItemClickListener) :
    ListAdapter<Articles,NewsAdapter.MyViewHolder>(DiffCallback) {
    lateinit var favNewsList: List<FavouriteNews>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.createViewHolder(parent)
    }
    fun submitFavouriteNewsList(favNewsList: List<FavouriteNews>) {
        this.favNewsList=favNewsList
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val articlesadapter=getItem(position)
        holder.bind(articlesadapter,favNewsList)
        holder.newsTitle.text=articlesadapter!!.title
        holder.itemView.setOnClickListener {
            newsItemClickListeners.onNewsItemClick(articlesadapter)
        }

        holder.favImage.setOnClickListener {
            newsItemClickListeners.onFavouriteClick(holder.isFavourite, articlesadapter)
            holder.isFavourite=holder.isFavourite.not()
            holder.setFavouriteIcon()
        }

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var newsTitle: TextView=itemView.findViewById(R.id.newsTitle)
        var image: ImageView=itemView.findViewById(R.id.newsImage)
        var favImage:CheckBox=itemView.findViewById(R.id.fav_CheckBox)
        var isFavourite:Boolean=false
        companion object {

            fun createViewHolder(parent: ViewGroup): MyViewHolder {
                val newsView=LayoutInflater.from(parent.context)
                    .inflate(R.layout.news_list_degsign, parent, false)

                return MyViewHolder(newsView)
            }
        }
        fun bind(article: Articles, favNewsList: List<FavouriteNews>) {
            isFavourite=checkFavouriteOrNot(article, favNewsList)
            setFavouriteIcon()
            article.let { it ->

                newsTitle.text=it.title
                var imgUri: Uri?=null
                it.imageUrl?.let {
                    imgUri=it.toUri().buildUpon().scheme("https").build()
                }

                if (imgUri == null) {
                    image.setImageResource(R.drawable.try_later)
                }
                else  {
                    imgUri?.let { uri ->
                        loadImage(uri, image)
                    }
                }
            }
        }

        fun setFavouriteIcon() {
            if (isFavourite) {
                //Toast.makeText(itemView.context,"Added to the Favorite News",Toast.LENGTH_SHORT).show()
                favImage.setBackgroundResource(R.drawable.ic_baseline_star_24)
                //favImage.setBackgroundResource(R.drawable.ic_baseline_star_24)
            } else {
                //Toast.makeText(itemView.context,"Removed from Favorite News",Toast.LENGTH_SHORT).show()
                favImage.setBackgroundResource(R.drawable.ic_outline_star_border_24)
            }
        }

        private fun checkFavouriteOrNot(article: Articles?,favNewsList: List<FavouriteNews>
        ): Boolean {
            for (favNews in favNewsList) {
                if (article?.articleUrl == favNews.articleUrl) {
                    return true
                }
            }
            return false
        }


        //class OnClickListener(val clickListener : (article : Articles) -> Unit){
        //  fun onClick(article: Articles) = clickListener(article)
        //}
    }


}





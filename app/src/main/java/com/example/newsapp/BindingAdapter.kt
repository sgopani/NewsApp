package com.example.newsapp

import android.content.Context
import android.net.Uri
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.newsapp.database.FavouriteNews
import com.example.newsapp.newsNetwork.Articles
object DiffCallback : DiffUtil.ItemCallback<Articles>() {
    override fun areItemsTheSame(oldItem: Articles, newItem: Articles): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Articles, newItem: Articles): Boolean {
        return oldItem.title == newItem.title
    }
}

fun loadImage(imgUri: Uri, imageView: ImageView){
    Glide.with(imageView.context)
        .load(imgUri)
        .apply(
            RequestOptions()
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.try_later)
                .fitCenter().override(600, 200)
        )
        .into(imageView)
}
fun favouriteNewsToArticle(favNews: FavouriteNews): Articles{
    val article = Articles(
        id = favNews.news_Id.toString(),
        author = favNews.author,
        title = favNews.title,
        description = favNews.description,
        content = favNews.content,
        imageUrl = favNews.imageUrl,
        articleUrl = favNews.articleUrl,
        publishedAt = favNews.publishedAt,

        )
    return article
}
fun hideKeyboard(activity: FragmentActivity?) {
    val inputMethodManager =
        activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    // Check if no view has focus
    val currentFocusedView = activity.currentFocus
    currentFocusedView?.let {
        inputMethodManager.hideSoftInputFromWindow(
            currentFocusedView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}
package com.example.newsapp.newsNetwork

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Articles(
    @SerializedName("id")
    val id : String?,

    @SerializedName("author")
    val author: String?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("url")
    val articleUrl: String?,

    @SerializedName("urlToImage")
    val imageUrl: String?,

    @SerializedName("publishedAt")
    val publishedAt: String?,

    @SerializedName("content")
    val content: String?,

    @SerializedName("isSelected")
    var isSelected: Boolean=true,

    ) : Parcelable
{
    fun isIs_selected(): Boolean {
        return is_selected
    }

    fun setIs_selected(selected: Boolean) {
        this.is_selected=selected
    }

    var is_selected=true
}
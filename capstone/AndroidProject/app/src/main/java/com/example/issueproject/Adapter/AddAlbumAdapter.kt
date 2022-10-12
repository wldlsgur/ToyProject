package com.example.issueproject.Adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.issueproject.R

class AddAlbumAdapter(var list:MutableList<Uri?>) : RecyclerView.Adapter<AddAlbumAdapter.AddAlbumViewHolder>() {

    inner class AddAlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val albumimage: ImageView = itemView.findViewById(R.id.imageView_AlbumImageItem)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddAlbumViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_album_item2, parent, false)
        return AddAlbumViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: AddAlbumAdapter.AddAlbumViewHolder, position: Int) {
        val image_url = list[position]

        Glide.with(holder.albumimage.context)
            .load(image_url)
            .into(holder.albumimage)
    }
}
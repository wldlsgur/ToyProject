package com.example.issueproject.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.issueproject.R
import com.example.issueproject.retrofit.RetrofitBuilder

private const val TAG = "AlbumItemAdapter"
class AlbumItemAdapter(val context: Context) : RecyclerView.Adapter<AlbumItemAdapter.AlbumItemViewHolder>() {

    var list: List<String> = mutableListOf()

    inner class AlbumItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bindinfo(data: String){

            Glide.with(context)
                .load("${RetrofitBuilder.servers}/image/album/${data}")
                .into(itemView.findViewById(R.id.imageView_AlbumImageItem))
       }
    }

    override fun onCreateViewHolder(album: ViewGroup, viewType: Int): AlbumItemViewHolder {
        val view = LayoutInflater.from(album.context).inflate(R.layout.activity_album_item2,album,false)
        return AlbumItemViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: AlbumItemViewHolder, position: Int) {
        holder.bindinfo(list[position])
//        val imagelist = list[position]
//
//        Log.d(TAG, "onBindViewHolder: $imagelist")
//        Glide.with(holder.image.context)
//            .load("${RetrofitBuilder.servers}/image/album/${imagelist}")
//            .into(holder.image)
    }
}
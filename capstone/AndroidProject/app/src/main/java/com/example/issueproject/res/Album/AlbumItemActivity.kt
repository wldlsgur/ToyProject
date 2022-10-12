package com.example.issueproject.res.Album

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.issueproject.Adapter.AlbumItemAdapter
import com.example.issueproject.databinding.ActivityAlbumItemBinding
import com.example.issueproject.dto.AlbumResult
import com.example.issueproject.retrofit.RetrofitCallback
import com.example.issueproject.service.ResponseService

private const val TAG = "AlbumItemActivity"
class AlbumItemActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityAlbumItemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }

}
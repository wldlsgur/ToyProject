package com.example.issueproject.res.Album

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.issueproject.Adapter.AlbumAdapter
import com.example.issueproject.R
import com.example.issueproject.databinding.ActivityAlbumBinding
import com.example.issueproject.dto.AlbumResult
import com.example.issueproject.dto.GetRoom
import com.example.issueproject.retrofit.RetrofitCallback
import com.example.issueproject.service.ResponseService

private const val TAG = "AlbumActivity"
class AlbumActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityAlbumBinding.inflate(layoutInflater)
    }
    private lateinit var albumAdapter : AlbumAdapter
    val roomList = mutableListOf<String>()
    var room : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val school = intent.getStringExtra("school")
        val room = intent.getStringExtra("room")
        val job = intent.getStringExtra("job")

        binding.textViewAlbumSchool.text = school


        GetRoom(school!!)

        Log.d(TAG, "school: $school")
        Log.d(TAG, "room: $room")

        binding.spinnerAlbumRoom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val room = roomList[position]
                ShowRecycler(school!!,room!!)
            }

        }
    }

    private fun initAdapter(lists:MutableList<AlbumResult>){
        albumAdapter = AlbumAdapter(lists, this)

        binding.albumRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = albumAdapter
        }
    }

    private fun ShowRecycler(school: String, room: String) {
        ResponseService().GetAlbumInfo(school, room, object : RetrofitCallback<MutableList<AlbumResult>> {
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }

            override fun onSuccess(code: Int, responseData: MutableList<AlbumResult>) {
                Log.d(TAG, "onSuccess: $responseData")
                for (i in 0..responseData.size-1){
                    initAdapter(responseData)
                }
            }

            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }
        })
    }

    fun GetRoom(school: String){
        ResponseService().GetRoom(school, object: RetrofitCallback<MutableList<GetRoom>> {
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }

            override fun onSuccess(code: Int, responseData: MutableList<GetRoom>) {
                roomList.clear()
                for(item in responseData) {
                    roomList.add(item.room)
                }

                val adapter = ArrayAdapter(this@AlbumActivity, R.layout.spinner, roomList)
                binding.spinnerAlbumRoom.adapter = adapter
            }

            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }

        })
    }
}
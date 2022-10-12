package com.example.issueproject.res.Add

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.issueproject.databinding.ActivityAddRoomBinding
import com.example.issueproject.dto.LoginResult
import com.example.issueproject.dto.Presidentinfo
import com.example.issueproject.dto.PresidentinfoResult
import com.example.issueproject.dto.SignUpResult
import com.example.issueproject.res.MenuActivity
import com.example.issueproject.retrofit.RetrofitBuilder
import com.example.issueproject.retrofit.RetrofitCallback
import com.example.issueproject.service.ResponseService
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream

private const val TAG = "AddRoomActivity"
class AddRoomActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityAddRoomBinding.inflate(layoutInflater)
    }
    var id : String = ""
    var name : String = ""
    val roomList = mutableListOf<String>()
    val addroomList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        roomList.clear()
        addroomList.clear()
        id = intent.getStringExtra("id")!!
        name = intent.getStringExtra("name")!!

        GetPresidentInfo(id)

        binding.imageButtonAddRoom.setOnClickListener {
            val roomname = binding.AddRoomName.text.toString()
            if(roomname != null){
                roomList.add(roomname)
                addroomList.add(roomname)
                binding.AddRoomName.text = null
                Toast.makeText(this, "반이 추가되었습니다.", Toast.LENGTH_SHORT).show()

                val adapter = ArrayAdapter(this@AddRoomActivity, android.R.layout.simple_list_item_1, roomList)
                binding.RoomLV.adapter = adapter
            }
            else{
                Toast.makeText(this, "반이름을 작성해주세요", Toast.LENGTH_SHORT).show()
            }

        }

        binding.textViewAddRoom.setOnClickListener {
            Log.d(TAG, "onCreate: ")
            val school = binding.AddRoomSchoolName.text.toString()
            val number = binding.AddRoomNumber.text.toString()

            for(i in 0..addroomList.size-1) {
                val room = addroomList[i]

                var presidentinfo = Presidentinfo(id, school, room, number)
                SchoolAdd(presidentinfo)
            }
        }
    }

    fun SchoolAdd(info: Presidentinfo){
        ResponseService().CreatePresidentinfo(info, object: RetrofitCallback<SignUpResult> {
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }
            override fun onSuccess(code: Int, responseData: SignUpResult) {
                Log.d(TAG, "onSuccess: $responseData")
                if(responseData.msg == "success"){
                    Toast.makeText(this@AddRoomActivity, "성공", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }
        })
    }

    fun GetPresidentInfo(id: String) {
        ResponseService().GetPresidentInfo(id, object : RetrofitCallback<MutableList<PresidentinfoResult>> {
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }
            override fun onSuccess(code: Int, responseData: MutableList<PresidentinfoResult>) {
                Log.d(TAG, "onSuccess: $responseData")

                binding.AddRoomSchoolName.text = responseData[0].school
                binding.AddRoomNumber.text = responseData[0].number

                Glide.with(this@AddRoomActivity)
                    .load("${RetrofitBuilder.servers}/image/president/${responseData[0].image_url}")
                    .into(binding.AddRoomImageview)

                for (i in 0..responseData.size-1){
                    roomList.add(responseData[i].room)
                }

                val adapter = ArrayAdapter(this@AddRoomActivity, android.R.layout.simple_list_item_1, roomList)
                binding.RoomLV.adapter = adapter
            }
            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }
        })
    }
}
package com.example.issueproject.res

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.issueproject.R
import com.example.issueproject.databinding.ActivityUserInfoChangeBinding
import com.example.issueproject.dto.*
import com.example.issueproject.retrofit.RetrofitBuilder
import com.example.issueproject.retrofit.RetrofitCallback
import com.example.issueproject.service.ResponseService

private const val TAG = "UserInfoChangeActivity"
class UserInfoChangeActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityUserInfoChangeBinding.inflate(layoutInflater)
    }
    var keyId : Int = 0
    var id: String = ""
    var job: String = ""
    var school: String = ""
    var position: String = ""
    var room : String = ""
    var name : String = ""
    val itemList = mutableListOf<String>()
    val roomList = mutableListOf<String>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        id = intent.getStringExtra("id").toString()
        job = intent.getStringExtra("job").toString()
        school = intent.getStringExtra("school").toString()
        name = intent.getStringExtra("name").toString()
        position = intent.getStringExtra("position").toString()

        ChildInfo(id, name)
        GetSchool()

        binding.changeSpinnerSchool.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                school = itemList[position]
                GetRoom(school)
            }

        }
        binding.changeSpinnerRoom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                room = roomList[position]
            }
        }

        binding.buttonChangeChildinfo.setOnClickListener {
            val number = binding.textViewUserChangeNum.text.toString()
            val age = binding.textViewUserChangeAge.text.toString()
            val spec = binding.textViewUserChangeSpec.text.toString()

            var presidentinfo = ParentInfoUpdate(keyId, school, room, number, name, age, spec)
            Log.d(TAG, "onCreate: $age")
            UpdateParentinfo(presidentinfo)
        }

        binding.buttonUpdatePW.setOnClickListener {
            UpdatePW(id)
        }
    }

    fun GetSchool(){
        ResponseService().GetSchool(object : RetrofitCallback<MutableList<GetSchool>>{
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }
            override fun onSuccess(code: Int, responseData: MutableList<GetSchool>) {
                itemList.clear()
                itemList.add("어린이집")
                for(item in responseData) {
                    itemList.add(item.school)
                }
                val adapter = ArrayAdapter(this@UserInfoChangeActivity, R.layout.spinner, itemList)
                binding.changeSpinnerSchool.adapter = adapter
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
                roomList.add("반")
                for(item in responseData) {
                    roomList.add(item.room)
                }
                val adapter = ArrayAdapter(this@UserInfoChangeActivity, R.layout.spinner, roomList)
                binding.changeSpinnerRoom.adapter = adapter
            }
            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }
        })
    }

    fun ChildInfo(id: String, name: String){
        ResponseService().ChildInfo(id, name, object : RetrofitCallback<ParentInfoResult> {
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }
            override fun onSuccess(code: Int, responseData: ParentInfoResult) {
                Log.d(TAG, "onSuccess: $code")
                keyId = responseData.key_id
                binding.textViewUserChangeID.text = responseData.id
                binding.textViewUserChangeName.text = responseData.child_name
                binding.textViewUserChangeAge.setText("${responseData.child_age}")
                binding.textViewUserChangeSpec.setText("${responseData.spec}")
                binding.textViewUserChangeNum.setText("${responseData.number}")

                if(responseData.image_url != null){
                    Glide.with(this@UserInfoChangeActivity)
                        .load("${RetrofitBuilder.servers}/image/parent/${responseData.image_url}")
                        .into(binding.imageViewChildImg)
                }else if(responseData.image_url == null || responseData.image_url == ""){
                    Glide.with(this@UserInfoChangeActivity)
                        .load(R.drawable.addimage)
                        .into(binding.imageViewChildImg)
                }
            }

            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }
        })
    }

    fun UpdateParentinfo(ParentinfoUpdate : ParentInfoUpdate){
        ResponseService().UpdateParentinfo(ParentinfoUpdate, object : RetrofitCallback<SignUpResult>{
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }

            override fun onSuccess(code: Int, responseData: SignUpResult) {
                Log.d(TAG, "onSuccess: $code")
                if(responseData.msg == "success"){
                    Toast.makeText(this@UserInfoChangeActivity, "수정이 완료되었습니다.", Toast.LENGTH_SHORT).show()

                    var intent = Intent(this@UserInfoChangeActivity, MainParentActivity::class.java).apply {
                        putExtra("id", id)
                        putExtra("position", position)
                    }
                    startActivity(intent)
                }
            }
            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }

        })
    }

    fun UpdatePW(id: String){
        ResponseService().UpdatePW(ID(id), object : RetrofitCallback<SignUpResult>{
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }

            override fun onSuccess(code: Int, responseData: SignUpResult) {
                Log.d(TAG, "onSuccess: $code")
                if(responseData.msg == "success"){
                    Toast.makeText(this@UserInfoChangeActivity, "비밀번호 변경이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                    binding.textViewUserChangePW.text = null
                }
            }

            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }

        })
    }
}
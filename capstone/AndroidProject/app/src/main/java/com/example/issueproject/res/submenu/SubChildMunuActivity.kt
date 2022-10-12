package com.example.issueproject.res.submenu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.issueproject.Adapter.ChildAdapter
import com.example.issueproject.databinding.ActivitySubChildMunuBinding
import com.example.issueproject.dto.AgreeChange
import com.example.issueproject.dto.ParentInfoResult
import com.example.issueproject.dto.RoomChildListResult
import com.example.issueproject.dto.SignUpResult
import com.example.issueproject.res.Add.ChildAddActivity
import com.example.issueproject.res.MainParentActivity
import com.example.issueproject.retrofit.RetrofitCallback
import com.example.issueproject.service.ResponseService
import kotlinx.coroutines.runBlocking

private const val TAG = "SubChildMunuActivity"
class SubChildMunuActivity : AppCompatActivity() {
    lateinit var ChildAdapter: ChildAdapter

    private val binding by lazy{
        ActivitySubChildMunuBinding.inflate(layoutInflater)
    }
    var id: String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        id = intent.getStringExtra("id").toString()


        ChildAdapter = ChildAdapter(this)

        runBlocking {
            ShowRecycler(id)
        }

        initRecycler()

        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, ChildAddActivity::class.java).apply {
                putExtra("id", id)
                Log.d(TAG, "id: $id")
            }
            startActivity(intent)
        }
    }

    private fun initRecycler(){

        binding.subchildRv.apply {
            adapter = ChildAdapter
            layoutManager = LinearLayoutManager(context)
        }

        // 아이 컨스트레인트 클릭 이벤트
        ChildAdapter.setchildConClickListener(object : ChildAdapter.MenuClickListener {
            override fun onClick(position: Int, item: ParentInfoResult) {
                if(item.agree == "yes"){
                    val intent = Intent(this@SubChildMunuActivity, MainParentActivity::class.java).apply{
                        putExtra("id", intent.getStringExtra("id"))
                        Log.d(TAG, "position: $position")
                        putExtra("position", position.toString())
                    }
                    startActivity(intent)
                }
                else if(item.agree == "no"){
                    Toast.makeText(this@SubChildMunuActivity, "승인까지 기다려주세요!", Toast.LENGTH_SHORT).show()
                }
            }
        })

        ChildAdapter.setchildDeleteClickListener(object : ChildAdapter.MenuClickListener {
            override fun onClick(position: Int, item: ParentInfoResult) {
                DeleteChild(item.key_id, position)
            }

        })
    }

    private fun ShowRecycler(id: String) {
        ResponseService().GetParentInfo(id, object : RetrofitCallback<MutableList<ParentInfoResult>>{
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }

            override fun onSuccess(code: Int, responseData: MutableList<ParentInfoResult>) {
                Log.d(TAG, "onSuccess: $responseData")
                ChildAdapter.list = responseData
                ChildAdapter.notifyDataSetChanged()
            }

            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }

        })
    }

    fun DeleteChild(key_id : Int, position: Int){
        ResponseService().DeleteChildItem(AgreeChange(key_id), object : RetrofitCallback<SignUpResult>{
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }

            override fun onSuccess(code: Int, responseData: SignUpResult) {
                Log.d(TAG, "onSuccess: $responseData")
                Toast.makeText(this@SubChildMunuActivity, "삭제되었습니다.", Toast.LENGTH_SHORT).show()

                ChildAdapter.notifyItemRemoved(position)

                if(responseData.res == true && responseData.msg == "success") {
                    ShowRecycler(id)
                }
            }

            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }

        })
    }
}
package com.example.issueproject.res.RoomManager

import RoomChildListAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.issueproject.Adapterimport.SchoolTeacherListAdapter
import com.example.issueproject.databinding.ActivityRoomChildListBinding
import com.example.issueproject.dto.AgreeChange
import com.example.issueproject.dto.RoomChildListResult
import com.example.issueproject.dto.SchoolteacherListResult
import com.example.issueproject.dto.SignUpResult
import com.example.issueproject.res.Add.ChildAddActivity
import com.example.issueproject.retrofit.RetrofitCallback
import com.example.issueproject.service.ResponseService
import kotlinx.coroutines.*

private const val TAG = "RoomChildListActivity"
class RoomChildListActivity : AppCompatActivity() {
    lateinit var RoomChildListAdapter: RoomChildListAdapter

    private val binding by lazy{
        ActivityRoomChildListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val school = intent.getStringExtra("school")
        val room = intent.getStringExtra("room")

        RoomChildListAdapter = RoomChildListAdapter(this)

        runBlocking {
            getChildList(school!!, room!!)
        }

        initRecycler(school!!, room!!)

        binding.textViewChildListRoom.text = room


//        ShowRecycler(school!!, room!!)

    }

    private fun initRecycler(school: String, room: String) {


        binding.RoomChildListRV.apply {
            adapter = RoomChildListAdapter
            layoutManager = LinearLayoutManager(context)
        }

        // item 승인 버튼 클릭 이벤트
        RoomChildListAdapter.setApprovalItemClickListener(object : RoomChildListAdapter.MenuClickListener {
            override fun onClick(position: Int, item: RoomChildListResult) {
                Agreechange(item, position)
            }
        })

        // item 승인취소 버튼 클릭 이벤트
        RoomChildListAdapter.setCancelApprovalItemClickListener(object : RoomChildListAdapter.MenuClickListener {
            override fun onClick(position: Int, item: RoomChildListResult) {
                Deletechildlist(item, position)
            }
        })

        // item 수정 클릭 이벤트
        RoomChildListAdapter.setModifyItemClickListener(object : RoomChildListAdapter.MenuClickListener {
            override fun onClick(position: Int, item: RoomChildListResult) {

            }
        })

        // item 삭제 클릭 이벤트
        RoomChildListAdapter.setDeleteItemClickListener(object : RoomChildListAdapter.MenuClickListener {
            override fun onClick(position: Int, item: RoomChildListResult) {
                Deletechildlist(item, position)
            }
        })
    }

    private fun getChildList(school: String, room: String) {

        ResponseService().RoomChildListShow(school, room, object : RetrofitCallback<MutableList<RoomChildListResult>>{
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }

            override fun onSuccess(code: Int, responseData: MutableList<RoomChildListResult>) {
                Log.d(TAG, "onSuccess: $responseData")
//                initRecycler(responseData)
                RoomChildListAdapter.list = responseData
                RoomChildListAdapter.notifyDataSetChanged()
            }

            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }
        })

    }

    private fun Agreechange(roomChildListResult : RoomChildListResult, position: Int) {

        ResponseService().Agreechange(AgreeChange(roomChildListResult.key_id), object : RetrofitCallback<SignUpResult>{
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }

            override fun onSuccess(code: Int, responseData: SignUpResult) {
                Log.d(TAG, "onSuccess: $responseData")
                Toast.makeText(this@RoomChildListActivity, "승인이 완료되었습니다.", Toast.LENGTH_SHORT).show()

                if(responseData.res == true && responseData.msg == "success") {
                    getChildList(roomChildListResult.school, roomChildListResult.room)
                }
            }

            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }

        })
    }

    private fun Deletechildlist(roomChildListResult: RoomChildListResult, position: Int) {

        ResponseService().Deletechildlist(AgreeChange(roomChildListResult.key_id), object : RetrofitCallback<SignUpResult>{
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }

            override fun onSuccess(code: Int, responseData: SignUpResult) {
                Log.d(TAG, "onSuccess: $responseData")

//                D/ResponseService: onResponse: 200
//                D/ResponseService: onResponse: body is not null
//                D/RoomChildListActivity: onSuccess: SignUpResult(res=true, msg=success)
                Toast.makeText(this@RoomChildListActivity, "삭제되었습니다.", Toast.LENGTH_SHORT).show()

                RoomChildListAdapter.notifyItemRemoved(position)

                if(responseData.res == true && responseData.msg == "success") {
                    getChildList(roomChildListResult.school, roomChildListResult.room)
                }
            }

            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }

        })
    }
}
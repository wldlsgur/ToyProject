package com.example.issueproject.res.SchoolManager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.issueproject.Adapterimport.SchoolTeacherListAdapter
import com.example.issueproject.databinding.ActivitySchoolTeacherListBinding
import com.example.issueproject.dto.AgreeChange
import com.example.issueproject.dto.SchoolteacherListResult
import com.example.issueproject.dto.SignUpResult
import com.example.issueproject.dto.TeacherListKeyId
import com.example.issueproject.res.RoomManager.RoomChildListActivity
import com.example.issueproject.retrofit.RetrofitCallback
import com.example.issueproject.service.ResponseService
import kotlinx.coroutines.runBlocking

private const val TAG = "SchoolTeacherActivity"
class SchoolTeacherListActivity : AppCompatActivity() {
    lateinit var SchoolTeacherListAdapter: SchoolTeacherListAdapter
    var school: String = ""
    private val binding by lazy {
        ActivitySchoolTeacherListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        school = intent.getStringExtra("school").toString()

        SchoolTeacherListAdapter = SchoolTeacherListAdapter(this)

        runBlocking {
            getTeacherList(school!!)
        }

        initRecycler(school!!)

        binding.textViewTeacherListSchool.text = school
    }

    private fun initRecycler(school: String) {


        binding.SchoolteacherListRV.apply {
            adapter = SchoolTeacherListAdapter
            layoutManager = LinearLayoutManager(context)
        }

//        SchoolTeacherListAdapter.setItemClickListener(object: SchoolTeacherListAdapter.OnItemClickListener{
//
//            override fun onClick(v: View, position: Int, item: SchoolteacherListResult) {
//                val intent = Intent(this@SchoolTeacherListActivity, RoomChildListActivity::class.java).apply {
//                    Log.d(TAG, "onClick: $school")
//                    Log.d(TAG, "onClick: ${item.room}")
//                    putExtra("school", school)
//                    putExtra("room", item.room)
//                }
//                startActivity(intent)
//            }
//        })

        SchoolTeacherListAdapter.setChildLayoutClickListener(object : SchoolTeacherListAdapter.MenuClickListener{
            override fun onClick(position: Int, item: SchoolteacherListResult) {
                val intent = Intent(this@SchoolTeacherListActivity, RoomChildListActivity::class.java).apply {
                    Log.d(TAG, "onClick: $school")
                    Log.d(TAG, "onClick: ${item.room}")
                    putExtra("school", school)
                    putExtra("room", item.room)
                }
                startActivity(intent)
            }

        })

        // item 승인 버튼 클릭 이벤트
        SchoolTeacherListAdapter.setApprovalItemClickListener(object : SchoolTeacherListAdapter.MenuClickListener {
            override fun onClick(position: Int, item: SchoolteacherListResult) {
                Log.d(TAG, "onClick: ${item.id}")
                Teacheragreechange(item.id, item.school, position)
            }
        })

        // item 승인취소 버튼 클릭 이벤트
        SchoolTeacherListAdapter.setCancelApprovalItemClickListener(object : SchoolTeacherListAdapter.MenuClickListener {
            override fun onClick(position: Int, item: SchoolteacherListResult) {
                deleteteacherlist(item.id, item.school, position)
            }
        })

        // item 수정 클릭 이벤트
        SchoolTeacherListAdapter.setModifyItemClickListener(object : SchoolTeacherListAdapter.MenuClickListener {
            override fun onClick(position: Int, item: SchoolteacherListResult) {

            }
        })

        // item 삭제 클릭 이벤트
        SchoolTeacherListAdapter.setDeleteItemClickListener(object : SchoolTeacherListAdapter.MenuClickListener {
            override fun onClick(position: Int, item: SchoolteacherListResult) {
                deleteteacherlist(item.id, item.school, position)
            }
        })
    }

    private fun getTeacherList(school: String) {
        ResponseService().SchoolTeacherListShow(school, object : RetrofitCallback<MutableList<SchoolteacherListResult>> {
                override fun onError(t: Throwable) {
                    Log.d(TAG, "onError: $t")
                }

                override fun onSuccess(code: Int, responseData: MutableList<SchoolteacherListResult>) {
                    Log.d(TAG, "onSuccess: $responseData")
                    SchoolTeacherListAdapter.list = responseData
                    SchoolTeacherListAdapter.notifyDataSetChanged()
                }
                override fun onFailure(code: Int) {
                    Log.d(TAG, "onFailure: $code")
                }
            })

    }

    private fun Teacheragreechange(id: String, school: String, position: Int){
        ResponseService().Teacheragreechange((TeacherListKeyId(id)), object : RetrofitCallback<SignUpResult>{
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }

            override fun onSuccess(code: Int, responseData: SignUpResult) {
                Log.d(TAG, "onSuccess: $responseData")
                Toast.makeText(this@SchoolTeacherListActivity, "승인이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                if(responseData.res == true && responseData.msg == "success") {
                    getTeacherList(school)
                }
            }

            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }

        })
    }

    private fun deleteteacherlist(id: String, school: String, position: Int){
        ResponseService().deleteteacherlist(TeacherListKeyId(id), object : RetrofitCallback<SignUpResult>{
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }

            override fun onSuccess(code: Int, responseData: SignUpResult) {
                Log.d(TAG, "onSuccess: ${id}")
                Log.d(TAG, "onSuccess: $responseData")
                Toast.makeText(this@SchoolTeacherListActivity, "삭제되었습니다.", Toast.LENGTH_SHORT).show()

                SchoolTeacherListAdapter.notifyItemRemoved(position)

                if(responseData.res == true && responseData.msg == "success") {
                    getTeacherList(school)
                }
            }

            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }

        })
    }
}

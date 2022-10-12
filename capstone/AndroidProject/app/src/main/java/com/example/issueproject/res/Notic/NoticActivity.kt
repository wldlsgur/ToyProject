package com.example.issueproject.res.Notic

import DayNoticAdapter
import NoticAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.issueproject.Adapterimport.SchoolTeacherListAdapter
import com.example.issueproject.R
import com.example.issueproject.databinding.ActivityNoticBinding
import com.example.issueproject.dto.*
import com.example.issueproject.res.Add.AddNoticActivity
import com.example.issueproject.res.UpdateNoticActivity
import com.example.issueproject.retrofit.RetrofitCallback
import com.example.issueproject.service.ResponseService
import kotlinx.coroutines.runBlocking

private const val TAG = "NoticActivity"
class NoticActivity : AppCompatActivity() {
    lateinit var NoticAdapter: NoticAdapter

    private val binding by lazy{
        ActivityNoticBinding.inflate(layoutInflater)
    }
    var job : String = ""
    var menu : String = ""
    var school : String = ""
    var room : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val id = intent.getStringExtra("id")
        var name = intent.getStringExtra("name")
        job = intent.getStringExtra("job").toString()
        school = intent.getStringExtra("school").toString()
        room = intent.getStringExtra("room").toString()
        menu = intent.getStringExtra("menu").toString()

        binding.textViewNoticSchool.text = school

        if(job == "선생님" || job== "원장님"){
            binding.buttonNoticAdd.visibility = View.VISIBLE
        }
        else if(job == "부모님"){
            binding.buttonNoticAdd.visibility = View.INVISIBLE
        }

        Log.d(TAG, "onCreate: $school")
        Log.d(TAG, "onCreate: $room")
        Log.d(TAG, "onCreate: $menu")

        NoticAdapter = NoticAdapter(this, job)

        runBlocking {
            ShowRecycler(menu!!,school!!,room!!)
        }

        initRecycler()

        binding.buttonNoticAdd.setOnClickListener {
            var intent = Intent(this, AddNoticActivity::class.java).apply {
                putExtra("id", id)
                putExtra("job", job)
                putExtra("name", name)
                putExtra("menu", menu)
                putExtra("school", school)
                putExtra("room", room)
            }
            startActivity(intent)
        }
    }

    private fun initRecycler(){


        binding.NoticRV.apply {
            adapter = NoticAdapter
            layoutManager = LinearLayoutManager(context)
        }

        // item 수정 클릭 이벤트
        NoticAdapter.setModifyItemClickListener(object : NoticAdapter.MenuClickListener {
            override fun onClick(position: Int, item: GetSchoolManagement) {
                var intent = Intent(this@NoticActivity, UpdateNoticActivity::class.java).apply{
                    putExtra("key_id", item.key_id.toString())
                    putExtra("menu", item.menu)
                    putExtra("content", item.content)
                    putExtra("school", item.school)
                    putExtra("writer", item.writer)
                    putExtra("date", item.date)
                    putExtra("title", item.title)
                    putExtra("job", job)
                }
                startActivity(intent)            }
        })

        // item 삭제 클릭 이벤트
        NoticAdapter.setDeleteItemClickListener(object : NoticAdapter.MenuClickListener {
            override fun onClick(position: Int, item: GetSchoolManagement) {
                var key_id = AgreeChange(item.key_id)
                deleteNoticitem(key_id, position)
            }
        })
    }

    private fun ShowRecycler(menu: String, school: String, room: String) {
        ResponseService().DayNoticInfoShow(menu, school, room, object :
            RetrofitCallback<MutableList<GetSchoolManagement>> {
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }

            override fun onSuccess(code: Int, responseData: MutableList<GetSchoolManagement>) {
                Log.d(TAG, "onSuccess: $responseData")
                NoticAdapter.list = responseData
                NoticAdapter.notifyDataSetChanged()
            }

            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }
        })
    }

    fun deleteNoticitem(key_id : AgreeChange, position: Int){
        ResponseService().deleteNoticItem(key_id, object : RetrofitCallback<SignUpResult>{
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }

            override fun onSuccess(code: Int, responseData: SignUpResult) {
                Toast.makeText(this@NoticActivity, "삭제되었습니다.", Toast.LENGTH_SHORT).show()

                NoticAdapter.notifyItemRemoved(position)

                if(responseData.res == true && responseData.msg == "success") {
                    ShowRecycler(menu!!,school!!,room!!)
                }
            }

            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }

        })
    }
}
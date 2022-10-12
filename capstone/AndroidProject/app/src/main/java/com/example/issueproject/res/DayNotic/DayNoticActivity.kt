package com.example.issueproject.res.DayNotic

import DayNoticAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.issueproject.R
import com.example.issueproject.databinding.ActivityDayNoticBinding
import com.example.issueproject.dto.*
import com.example.issueproject.res.MenuActivity
import com.example.issueproject.res.UpdateNoticActivity
import com.example.issueproject.retrofit.RetrofitCallback
import com.example.issueproject.service.ResponseService
import kotlinx.coroutines.runBlocking

private const val TAG = "DayNoticActivity"
class DayNoticActivity : AppCompatActivity() {
    lateinit var DayNoticAdapter: DayNoticAdapter

    private val binding by lazy{
        ActivityDayNoticBinding.inflate(layoutInflater)
    }
    val roomList = mutableListOf<String>()
    var room : String = ""
    var school: String =""
    var menu: String = ""
    var job: String = "원장님"

    override fun onBackPressed() {
        startActivity(Intent(this@DayNoticActivity, MenuActivity::class.java))
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // 원장님일 경우 이 액티비티로
        val id = intent.getStringExtra("id")
        var name = intent.getStringExtra("name")
        school = intent.getStringExtra("school").toString()
        menu = intent.getStringExtra("menu").toString()
        
        binding.textViewDayNoticSchool.text = school

        GetRoom(school!!)

//        room = roomList[0]
//        Log.d(TAG, "onCreate: $room")
        DayNoticAdapter = DayNoticAdapter(this, job)

        runBlocking {
            ShowRecycler(menu!!,school!!,room!!)
        }

        initRecycler()

        Log.d(TAG, "onCreate: $school")
        Log.d(TAG, "onCreate: $menu")

        binding.spinnerDayNoticSchool.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                room = roomList[position]
                ShowRecycler(menu!!,school!!,room)
            }
        }
    }
    private fun initRecycler(){

        binding.DayNoticRV.apply {
            adapter = DayNoticAdapter
            layoutManager = LinearLayoutManager(context)
        }

        // item 수정 클릭 이벤트
        DayNoticAdapter.setModifyItemClickListener(object : DayNoticAdapter.MenuClickListener {
            override fun onClick(position: Int, item: GetSchoolManagement) {
                    var intent = Intent(this@DayNoticActivity, UpdateNoticActivity::class.java).apply{
                        putExtra("key_id", item.key_id.toString())
                        putExtra("menu", item.menu)
                        putExtra("content", item.content)
                        putExtra("school", item.school)
                        putExtra("writer", item.writer)
                        putExtra("date", item.date)
                        putExtra("title", item.title)
                        putExtra("job", job)
                    }
                    startActivity(intent)
            }
        })

        // item 삭제 클릭 이벤트
        DayNoticAdapter.setDeleteItemClickListener(object : DayNoticAdapter.MenuClickListener {
            override fun onClick(position: Int, item: GetSchoolManagement) {
                var key_id = AgreeChange(item.key_id)
                deleteNoticitem(key_id, position)
            }
        })
    }

    private fun ShowRecycler(menu: String, school: String, room: String) {
        ResponseService().DayNoticInfoShow(menu, school, room, object : RetrofitCallback<MutableList<GetSchoolManagement>> {
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }

            override fun onSuccess(code: Int, responseData: MutableList<GetSchoolManagement>) {
                Log.d(TAG, "onSuccess: $responseData")
                DayNoticAdapter.list = responseData
                DayNoticAdapter.notifyDataSetChanged()
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

                val adapter = ArrayAdapter(this@DayNoticActivity, R.layout.spinner, roomList)
                binding.spinnerDayNoticSchool.adapter = adapter


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
                Toast.makeText(this@DayNoticActivity, "삭제되었습니다.", Toast.LENGTH_SHORT).show()

                DayNoticAdapter.notifyItemRemoved(position)

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
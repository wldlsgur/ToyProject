
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
import com.example.issueproject.databinding.ActivityDayNoticTeacherBinding
import com.example.issueproject.dto.*
import com.example.issueproject.res.Add.AddNoticActivity
import com.example.issueproject.res.MainParentActivity
import com.example.issueproject.res.MainTeacherActivity
import com.example.issueproject.res.UpdateNoticActivity
import com.example.issueproject.retrofit.RetrofitCallback
import com.example.issueproject.service.ResponseService
import kotlinx.coroutines.runBlocking

private const val TAG = "DayNoticTeacherActivity"
class DayNoticTeacherActivity : AppCompatActivity() {
    lateinit var DayNoticAdapter: DayNoticAdapter

    private val binding by lazy{
        ActivityDayNoticTeacherBinding.inflate(layoutInflater)
    }
    var job : String = ""
    var menu : String = ""
    var school : String = ""
    var room : String = ""
    var id: String = ""
    var name: String = ""
    var img_url: String = ""
    var position: String = ""

    override fun onBackPressed() {
        if(job == "선생님"){
            var intent = Intent(this@DayNoticTeacherActivity, MainTeacherActivity::class.java).apply {
                putExtra("id", id)
                putExtra("name", name)
                putExtra("school", school)
                putExtra("room", room)
                putExtra("img_url", img_url)
            }
            startActivity(intent)
            finish()
        }
        else{
            var intent = Intent(this@DayNoticTeacherActivity, MainParentActivity::class.java).apply {
                putExtra("id", id)
                putExtra("position", position)
            }
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        id = intent.getStringExtra("id").toString()
        name = intent.getStringExtra("name").toString()
        job = intent.getStringExtra("job").toString()
        school = intent.getStringExtra("school").toString()
        room = intent.getStringExtra("room").toString()
        menu = intent.getStringExtra("menu").toString()
        img_url = intent.getStringExtra("img_url").toString()
        position = intent.getStringExtra("position").toString()

        // 부모님과 선생님일 경우 이액티비티로
        if(job == "선생님"){
            binding.buttonDayNoticAddTeacher.visibility = View.VISIBLE
        }
        else if(job == "부모님" || job == "원장님"){
            binding.buttonDayNoticAddTeacher.visibility = View.INVISIBLE
        }

        binding.textViewDayNoticTeacherSchool.text = school
        binding.textViewDayNoticTeacherRoom.text = room

        DayNoticAdapter = DayNoticAdapter(this, job!!)

        runBlocking {
            ShowRecycler(menu!!,school!!,room!!)
        }

        initRecycler()

        binding.buttonDayNoticAddTeacher.setOnClickListener {
            var intent = Intent(this, AddNoticActivity::class.java).apply {
                putExtra("job", job)
                putExtra("id", id)
                putExtra("name", name)
                putExtra("school", school)
                putExtra("room", room)
                putExtra("menu", menu)
                putExtra("img_url", img_url)
            }
            startActivity(intent)
        }
    }

    private fun initRecycler(){

        binding.DayNoticTeacherRV.apply {
            adapter = DayNoticAdapter
            layoutManager = LinearLayoutManager(context)
        }

        // item 수정 클릭 이벤트
        DayNoticAdapter.setModifyItemClickListener(object : DayNoticAdapter.MenuClickListener {
            override fun onClick(position: Int, item: GetSchoolManagement) {
                var intent = Intent(this@DayNoticTeacherActivity, UpdateNoticActivity::class.java).apply{
                    putExtra("key_id", item.key_id.toString())
                    putExtra("menu", item.menu)
                    putExtra("content", item.content)
                    putExtra("school", item.school)
                    putExtra("writer", item.writer)
                    putExtra("date", item.date)
                    putExtra("title", item.title)
                    putExtra("job", job)
                    putExtra("id", id)
                    putExtra("name", name)
                    putExtra("room", room)
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
    fun deleteNoticitem(key_id : AgreeChange, position: Int){
        ResponseService().deleteNoticItem(key_id, object : RetrofitCallback<SignUpResult>{
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }

            override fun onSuccess(code: Int, responseData: SignUpResult) {
                Toast.makeText(this@DayNoticTeacherActivity, "삭제되었습니다.", Toast.LENGTH_SHORT).show()

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
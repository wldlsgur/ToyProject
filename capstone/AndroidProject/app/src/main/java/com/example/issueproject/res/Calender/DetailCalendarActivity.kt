package com.example.issueproject.res.Calender

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.issueproject.Adapter.DailyDetailAdapter
import com.example.issueproject.databinding.ActivityDetailCalendarBinding
import com.example.issueproject.dto.*
import com.example.issueproject.res.UpdateNoticActivity
import com.example.issueproject.retrofit.RetrofitCallback
import com.example.issueproject.service.ResponseService

private const val TAG = "DetailCalenderActivity"
class DetailCalendarActivity : AppCompatActivity() {
    lateinit var DailyDetailAdapter: DailyDetailAdapter
    private val binding by lazy{
        ActivityDetailCalendarBinding.inflate(layoutInflater)
    }
    lateinit var calenderinfo : MutableList<GetCalenderDetail>
    lateinit var school : String
    lateinit var date : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
         school = intent.getStringExtra("school").toString()
         date = intent.getStringExtra("date").toString()
        ShowRecycler(school, date)

        //GetAlarmInfo
    }
    private fun initRecycler(){


        binding.detailCalenderRV.apply {
            adapter = DailyDetailAdapter
            layoutManager = LinearLayoutManager(context)
        }

        // item 수정 클릭 이벤트
        DailyDetailAdapter.setModifyItemClickListener(object : DailyDetailAdapter.OnItemClickListener {
            override fun onClick(position: Int, item: GetCalenderDetail) {
                var intent = Intent(this@DetailCalendarActivity, UpdateNoticActivity::class.java).apply{
                    putExtra("title", item.title)
                    putExtra("content", item.content)
                    putExtra("color", item.color)
                    putExtra("id", item.id)
                    putExtra("startTime", item.startTime)
                    putExtra("endTime", item.endTime)
                }
                startActivity(intent)            }
        })

        // item 삭제 클릭 이벤트
        DailyDetailAdapter.setDeleteItemClickListener(object : DailyDetailAdapter.OnItemClickListener {
            override fun onClick(position: Int, item: GetCalenderDetail) {
                lateinit var  data : deleteCalender
                data.id = calenderinfo[position].id
                data.title = calenderinfo[position].title
                data.content = calenderinfo[position].content
                data.school = school

                deleteCalender(data, position)
            }
        })
    }
    private fun ShowRecycler(school: String, date: String) {
        ResponseService().GetCalenderInfo(school, date, object : RetrofitCallback<MutableList<GetCalenderDetail>> {
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }

            override fun onSuccess(code: Int, responseData: MutableList<GetCalenderDetail>) {
                Log.d(TAG, "onSuccess: $responseData")
                calenderinfo = responseData
                DailyDetailAdapter = DailyDetailAdapter(this@DetailCalendarActivity, responseData)
                initRecycler()
            }

            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }
        })
    }

    fun deleteCalender(data : deleteCalender, position: Int){
        ResponseService().DeleteCalenderInfo(data, object : RetrofitCallback<SignUpResult> {
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }

            override fun onSuccess(code: Int, responseData: SignUpResult) {
                Toast.makeText(this@DetailCalendarActivity, "삭제되었습니다.", Toast.LENGTH_SHORT).show()

                DailyDetailAdapter.notifyItemRemoved(position)

                if(responseData.res == true && responseData.msg == "success") {
                }
            }

            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }

        })
    }
}
package com.example.issueproject.res.Add

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.toColor
import com.example.issueproject.R
import com.example.issueproject.databinding.ActivityDailyAddBinding
import com.example.issueproject.dto.Calenderinfo
import com.example.issueproject.dto.SignUpResult
import com.example.issueproject.dto.deleteCalender
import com.example.issueproject.retrofit.RetrofitCallback
import com.example.issueproject.service.ResponseService
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "DailyAddActivity"
class DailyAddActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityDailyAddBinding.inflate(layoutInflater)
    }
//    val itemList = mutableListOf<String>()
    lateinit var school : String
    lateinit var id : String
    lateinit var color : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        var Calenderinfo : Calenderinfo = Calenderinfo("", "", "", "", "", "", "", "", "")
        id = intent.getStringExtra("id").toString()
        school = intent.getStringExtra("school").toString()
        val currentTime = System.currentTimeMillis()
        convertTimestampToDate(currentTime)
        var itemList = arrayListOf<String>("빨간색", "파란색", "초록색", "노란색", "케인색")
        val adapter = ArrayAdapter(this@DailyAddActivity, R.layout.spinner, itemList)
        binding.spinnerDailyAddColor.adapter = adapter
        binding.spinnerDailyAddColor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position == 0){
                    binding.imageViewPoint.setColorFilter(Color.RED); // 검정색
                    color = "red"
                }
                if(position == 1){
                    //resources.getColor(R.color.red_)
                    binding.imageViewPoint.setColorFilter(Color.BLUE); // 빨간색
                    color = "blue"
                }
                if(position == 2){
                    binding.imageViewPoint.setColorFilter(Color.GREEN); // 파란색
                    color = "green"
                }
                if(position == 3){
                    binding.imageViewPoint.setColorFilter(Color.YELLOW); // 노란색
                    color = "yellow"
                }
                if(position == 4){
                    binding.imageViewPoint.setColorFilter(Color.CYAN); // 초록색
                    color = "cyan"
                }
            }
        }

        //추가버튼 클릭시
        binding.textViewDailyAddBtn.setOnClickListener {
            bindinfo(Calenderinfo)
            insertDaily(Calenderinfo)
        }

        //DatePicker 클릭시
        binding.DailyDatePicker.setOnClickListener {
            showDatePicker()
        }
        binding.DailyEndDatePicker.setOnClickListener {
            showDatePicker2()
        }

        //시작시간 클릭시
        binding.textViewDailyAddStartTime.setOnClickListener {
            getTime(binding.textViewDailyAddStartTime, this)
        }

        //종료시간 클릭시
        binding.textViewDailyAddEndTime.setOnClickListener {
            getTime(binding.textViewDailyAddEndTime, this)
        }

    }

    private fun bindinfo(Calenderinfo : Calenderinfo){
        Calenderinfo.title = binding.editTextDailyTitle.text.toString()
        Calenderinfo.content = binding.editTextDailyContent.text.toString()
        Calenderinfo.startDate = start_date
        Calenderinfo.endDate = end_date
        Calenderinfo.startTime = binding.textViewDailyAddStartTime.text.toString()
        Calenderinfo.endTime = binding.textViewDailyAddEndTime.text.toString()
        Calenderinfo.school = school
        Calenderinfo.id = id
        Calenderinfo.color = color
        Log.d(TAG, "ok")
    }

    private fun insertDaily(data : Calenderinfo){
        Log.d(TAG, data.title)
        Log.d(TAG, data.content)
        Log.d(TAG, data.startDate)
        Log.d(TAG, data.endDate)
        Log.d(TAG, data.startTime)
        Log.d(TAG, data.endTime)
        Log.d(TAG, data.school)
        Log.d(TAG, data.id)
        Log.d(TAG, data.color)


        ResponseService().InsertCalenderInfo(data, object : RetrofitCallback<SignUpResult> {

            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }

            override fun onSuccess(code: Int, responseData: SignUpResult) {
                Toast.makeText(this@DailyAddActivity, "추가되었습니다.", Toast.LENGTH_SHORT).show()


                if(responseData.res == true && responseData.msg == "success") {
                }
            }

            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }

        })
    }


    fun getTime(date: TextView, context: Context){

        val cal = Calendar.getInstance()

        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute  ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            date.text = SimpleDateFormat("HH : mm a").format(cal.time)
        }

        TimePickerDialog(context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()

    }

    var cal = Calendar.getInstance()
    var start_date : String = ""
    var end_date : String = ""
    private val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        updateDateInText()
        }
    private val dateSetListener2 = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, monthOfYear)
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        updateDateInText2()
    }

    private fun showDatePicker(){
        DatePickerDialog(this,
            dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)).show()
    }
    private fun showDatePicker2(){
        DatePickerDialog(this,
            dateSetListener2,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)).show()
    }
    private fun convertTimestampToDate(timestamp: Long){
        val sdf = SimpleDateFormat("yyyy년 MM월 dd일")
        val date = sdf.format(timestamp)

        binding.textViewDailyAddDate.text = date
        binding.textViewDailyEndDate.text = date
        var year = date.substring(0,4)
        var month = date.substring(6,8)
        var day = date.substring(10,12)
        Log.d(TAG, "datetest: ${year}-${month}-${day}")
    }

    private fun updateDateInText(){
        var formatter = SimpleDateFormat("yyyy년 MM월 dd일")
        val send = SimpleDateFormat("yyyy-MM-dd")
        start_date = send.format(cal.time)
        binding.textViewDailyAddDate.text = formatter.format(cal.time)

    }
    private fun updateDateInText2(){
        var formatter = SimpleDateFormat("yyyy년 MM월 dd일")
        val send = SimpleDateFormat("yyyy-MM-dd")
        end_date = send.format(cal.time)
        binding.textViewDailyEndDate.text = formatter.format(cal.time)

    }
}
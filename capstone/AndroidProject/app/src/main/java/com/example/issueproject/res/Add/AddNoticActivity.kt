package com.example.issueproject.res.Add

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.issueproject.databinding.ActivityAddNoticBinding
import com.example.issueproject.dto.AddManagement
import com.example.issueproject.dto.AddManagementResult
import com.example.issueproject.res.DayNotic.DayNoticTeacherActivity
import com.example.issueproject.res.Notic.NoticActivity
import com.example.issueproject.retrofit.RetrofitCallback
import com.example.issueproject.service.ResponseService
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

private const val TAG = "AddNoticActivity"

class AddNoticActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityAddNoticBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.textViewMenu.text = intent.getStringExtra("menu")
        binding.textViewWriter.text = intent.getStringExtra("name")

        val currentTime = System.currentTimeMillis()
        convertTimestampToDate(currentTime)

        binding.buttonDatePicker.setOnClickListener {
            showDatePicker()
        }

        binding.buttonAdd.setOnClickListener {
            var title = binding.editTextAddTitle.text.toString()
            var content = binding.editTextAddContent.text.toString()
            var date = binding.textViewDate.text.toString()
            var school = intent.getStringExtra("school")!!
            var room = intent.getStringExtra("room")!!
            var menu = intent.getStringExtra("menu")!!
            var writer = intent.getStringExtra("name")!!

            var addManagement = AddManagement(menu, writer, school, title, content, date, room)
            insertaddManagement(addManagement)

        }
    }

    var cal = Calendar.getInstance()
    private val dateSetListener =
        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInText()
        }

    private fun showDatePicker(){
        DatePickerDialog(this,
            dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)).show()
    }
    private fun convertTimestampToDate(timespamp: Long){
        val sdf = SimpleDateFormat("yyyy년 MM월 dd일")
        val date = sdf.format(timespamp)

        binding.textViewDate.text = date
        var year = date.substring(0,4)
        var month = date.substring(6,8)
        var day = date.substring(10,12)
        Log.d(TAG, "datetest: ${year}-${month}-${day}")
    }

    private fun updateDateInText(){
        var formatter = SimpleDateFormat("yyyy년 MM월 dd일")
        binding.textViewDate.text = formatter.format(cal.time)
    }

    private fun insertaddManagement(addManagement: AddManagement){
        ResponseService().AddManagementService(addManagement, object:
            RetrofitCallback<AddManagementResult> {
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }

            override fun onSuccess(code: Int, responseData: AddManagementResult) {
                Log.d(TAG, "onSuccess: $responseData")
                Toast.makeText(this@AddNoticActivity, "성공",Toast.LENGTH_SHORT).show()

                if(intent.getStringExtra("menu") == "공지사항"){
                    var intent1 = Intent(this@AddNoticActivity, NoticActivity::class.java).apply {
                        putExtra("job", intent.getStringExtra("job"))
                        putExtra("id", intent.getStringExtra("id"))
                        putExtra("name", intent.getStringExtra("name"))
                        putExtra("school", intent.getStringExtra("school"))
                        putExtra("room", intent.getStringExtra("room"))
                        putExtra("menu", intent.getStringExtra("menu"))
                        putExtra("img_url", intent.getStringExtra("img_url"))
                    }
                    startActivity(intent1)
                }
                else if(intent.getStringExtra("menu") == "알림장"){
                    var intent2 = Intent(this@AddNoticActivity, DayNoticTeacherActivity::class.java).apply {
                        putExtra("job", intent.getStringExtra("job"))
                        putExtra("id", intent.getStringExtra("id"))
                        putExtra("name", intent.getStringExtra("name"))
                        putExtra("school", intent.getStringExtra("school"))
                        putExtra("room", intent.getStringExtra("room"))
                        putExtra("menu", intent.getStringExtra("menu"))
                        putExtra("img_url", intent.getStringExtra("img_url"))
                    }
                    startActivity(intent2)
                }
            }

            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }
        })
    }
}
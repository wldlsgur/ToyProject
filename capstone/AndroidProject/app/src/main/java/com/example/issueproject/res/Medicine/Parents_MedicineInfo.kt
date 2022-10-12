package com.example.issueproject.res.Medicine

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.issueproject.R
import com.example.issueproject.databinding.ActivityMedicineBinding
import com.example.issueproject.dto.*
import com.example.issueproject.res.MainActivity
import com.example.issueproject.res.viewmodel.MainViewModels
import com.example.issueproject.retrofit.RetrofitBuilder
import com.example.issueproject.retrofit.RetrofitCallback
import com.example.issueproject.service.ResponseService
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "Parents_MedicineInfo"
class Parents_MedicineInfo : AppCompatActivity() {

    private val binding by lazy{
        ActivityMedicineBinding.inflate(layoutInflater)
    }

    var id : String = ""
    var cname : String = ""
    var mname : String = ""
    var add : Boolean = false
    var school : String = ""
    var room : String = ""
    var date : String = ""
    var morning : String = ""
    var content : String = ""
    var lunch : String = ""
    var dinner : String = ""
    var mplace : String = ""
    var img_url : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()
        val currentTime = System.currentTimeMillis()
        convertTimestampToDate(currentTime)

        binding.medicineButtonSave.setOnClickListener {
            // update 값 변경 통신
        }
        binding.medicineButtonDelete.setOnClickListener {
            // delete값 변경 통신
        }
        binding.buttonAdd.setOnClickListener {
            makedata()
            var medicine = PostMedicine(id,cname,mname,morning,lunch,dinner,date,mplace,content,school,room)
            CreateMedicine(medicine)
        }
        binding.buttonDatePicker.setOnClickListener {
            showDatePicker()
        }
    }

    fun makedata(){
        date = binding.TextViewDate.text.toString()
        content = binding.EditMedicineContent.text.toString()
        mname = binding.EditMedicineMname.text.toString()
        if(binding.CheckMorning.isChecked == true) morning = "true"
        else morning = "false"
        if(binding.CheckLunch.isChecked == true) lunch = "true"
        else morning = "false"
        if(binding.CheckDinner.isChecked == true) dinner = "true"
        else morning = "false"
        if(binding.checkBoxOn.isChecked == true) mplace = "실온"
        else if(binding.checkBoxOn.isChecked == false)mplace = "냉장"
    }
    fun init(){
        id = intent.getStringExtra("id").toString()
        cname = intent.getStringExtra("cname").toString()
        mname = intent.getStringExtra("mname").toString()
        add = intent.getBooleanExtra("add",false)
        school = intent.getStringExtra("school").toString()
        room = intent.getStringExtra("room").toString()
        img_url = intent.getStringExtra("img_url").toString()
//        binding.medicineAppbar.textViewTitle.text = "약 관리"
        binding.medicineSchool.text = school
        binding.medicineRoom.text = room
        binding.medicineCname.text = cname
        binding.medicineContent.visibility = View.INVISIBLE
        binding.medicineMname.visibility = View.INVISIBLE
        if(add == false) {
            binding.buttonAdd.visibility = View.INVISIBLE
            GetmedicineInfo(id,cname,mname)
        }
        else if(add == true){
            binding.medicineButtonSave.visibility = View.INVISIBLE
            binding.medicineButtonDelete.visibility = View.INVISIBLE
        }

        if(img_url != null){
            Glide.with(this)
                .load("${RetrofitBuilder.servers}/image/parent/${img_url}")
                .into(binding.imageViewmedicine)
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

        binding.TextViewDate.text = date
        var year = date.substring(0,4)
        var month = date.substring(6,8)
        var day = date.substring(10,12)
        Log.d(TAG, "datetest: ${year}-${month}-${day}")
    }

    private fun updateDateInText(){
        var formatter = SimpleDateFormat("yyyy년 MM월 dd일")
        binding.TextViewDate.text = formatter.format(cal.time)
    }

    fun bindinfo(data: Medicine) {
        binding.medicineCname.text = data.child_name
        binding.EditMedicineMname.setText(data.m_name)
        binding.EditMedicineContent.setText(data.content)

        if(data.morning == "true") binding.CheckMorning.isChecked = true

        else binding.CheckMorning.isChecked = false


        if(data.lunch == "true") binding.CheckLunch.isChecked = true

        else binding.CheckLunch.isChecked = false


        if(data.dinner == "true") binding.CheckDinner.isChecked = true

        else binding.CheckDinner.isChecked = false


        if(data.mPlace == "실온") {
            binding.checkBoxOn.isChecked = true
            binding.checkBoxOut.isChecked = false

        }
        else {
            binding.checkBoxOn.isChecked = false

            binding.checkBoxOut.isChecked = true

        }

    }

    fun GetmedicineInfo(id: String, child_name: String, m_name: String){
        ResponseService().GetMedcineInfo(
            id,child_name,m_name,
            object : RetrofitCallback<Medicine> {
                override fun onError(t: Throwable) {
                    Log.d(TAG, "onError: $t")
                }

                override fun onSuccess(code: Int, responseData: Medicine) {
                    Log.d(TAG, "onSuccess: $responseData")
                    this@Parents_MedicineInfo.bindinfo(responseData)
                }

                override fun onFailure(code: Int) {
                    Log.d(TAG, "onFailure: $code")
                }

            })

    }

    fun CreateMedicine(data: PostMedicine){
        ResponseService().CreateMedicine(data, object: RetrofitCallback<SignUpResult> {
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }

            override fun onSuccess(code: Int, responseData: SignUpResult) {
                Log.d(TAG, "onSuccess: $code")
                if(responseData.msg == "success"){

                    var intent = Intent(this@Parents_MedicineInfo, ParentsMedicineList::class.java).apply{
                        putExtra("id",id)
                        putExtra("cname", cname)
                        putExtra("school",school)
                        putExtra("room",room)
                    }

                    startActivity(intent)
                }else if(responseData.msg == "failed"){
                }
            }

            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }
        })
    }
    //CreateMedicine

}
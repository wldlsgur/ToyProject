package com.example.issueproject.res.Medicine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.core.view.isInvisible
import com.bumptech.glide.Glide
import com.example.issueproject.R
import com.example.issueproject.databinding.ActivityMedicineBinding
import com.example.issueproject.dto.Medicine
import com.example.issueproject.dto.MedicineManage
import com.example.issueproject.res.viewmodel.MainViewModels
import com.example.issueproject.retrofit.RetrofitBuilder
import com.example.issueproject.retrofit.RetrofitCallback
import com.example.issueproject.service.ResponseService

private const val TAG = "MedicineInfo"
class Teacher_MedicineInfo : AppCompatActivity() {

    private val binding by lazy{
        ActivityMedicineBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setContentView(binding.root)
        val id = intent.getStringExtra("id").toString()
        val cname = intent.getStringExtra("cname").toString()
        val mname = intent.getStringExtra("mname").toString()
        val img_url = intent.getStringExtra("img_url").toString()

        if(img_url != null){
            Glide.with(this)
                .load("${RetrofitBuilder.servers}/image/teacher/${img_url}")
                .into(binding.imageViewmedicine)
        }

        GetmedicineInfo(id,cname,mname)

    }

    fun init(){
        binding.buttonAdd.visibility = View.INVISIBLE
        binding.medicineButtonSave.visibility = View.INVISIBLE
        binding.medicineButtonDelete.visibility = View.INVISIBLE
        binding.EditMedicineContent.visibility = View.INVISIBLE
        binding.EditMedicineMname.visibility = View.INVISIBLE
    }

    fun bindinfo(data: Medicine) {
        binding.medicineCname.text = data.child_name
        binding.medicineMname.text = data.m_name
        binding.medicineContent.text = data.content

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
                    this@Teacher_MedicineInfo.bindinfo(responseData)
                }

                override fun onFailure(code: Int) {
                    Log.d(TAG, "onFailure: $code")
                }

            })

    }

}

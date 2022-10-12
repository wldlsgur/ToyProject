package com.example.issueproject.res.Add

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.issueproject.Adapter.AddAlbumAdapter
import com.example.issueproject.R
import com.example.issueproject.databinding.ActivityFoodAddBinding
import com.example.issueproject.dto.*
import com.example.issueproject.retrofit.RetrofitCallback
import com.example.issueproject.service.ResponseService
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

private const val TAG = "FoodAddActivity"
class FoodAddActivity : AppCompatActivity() {
    private lateinit var getResult: ActivityResultLauncher<Intent>
    private lateinit var currentImageUri: Uri
    private val binding by lazy{
        ActivityFoodAddBinding.inflate(layoutInflater)
    }

    var school: String = ""
    var id: String = ""
    var year: String = ""
    var month : String =""
    var key_id : Int = 0

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        school = intent.getStringExtra("school").toString()
        id = intent.getStringExtra("id").toString()

        val currentTime = System.currentTimeMillis()
        convertTimestampToDate(currentTime)

//        val yearlist = resources.getStringArray(R.drawable.yearspiiner)
//        val monthlist = DateFormatSymbols().months
        val yearlist = arrayListOf<String>("선택안함", "2022년", "2021년", "2020년", "2019년", "2018년")
        val monthlist = arrayListOf<String>("선택안함", "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월","10월", "11월", "12월")

        val Yearadapter = ArrayAdapter(this@FoodAddActivity, R.layout.spinner, yearlist)
        binding.spinnerYear.adapter = Yearadapter

        val Monthadapter = ArrayAdapter(this@FoodAddActivity, R.layout.spinner, monthlist)
        binding.spinnerMonth.adapter = Monthadapter

        getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK && it.data !=null) {
                currentImageUri = it.data?.data!!
                try {
                    currentImageUri?.let {
                        Glide.with(this)
                            .load(currentImageUri)
                            .into(binding.imageViewFoodList)
                    }
                }catch(e:Exception) {
                    e.printStackTrace()
                }
            } else if(it.resultCode == RESULT_CANCELED){
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }else{
                Log.d("ActivityResult","something wrong")
            }
        }

        binding.spinnerYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                year = yearlist[position]
            }
        }

        binding.spinnerMonth.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                month = monthlist[position]

                if(position != 0){
                    binding.textViewFoodAddDate.text = year + month
                }
            }
        }

        binding.imageViewFoodList.setOnClickListener {
            val intent: Intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            getResult.launch(intent)
        }

        binding.AddFoodAddBtn.setOnClickListener{
            val date = binding.textViewFoodAddDate.text.toString()

            var foodList = FoodList(school, date)
            CreateFoodList(foodList)
        }
    }

    fun CreateFoodList(info : FoodList){
        ResponseService().CreateFoodList(info, object : RetrofitCallback<SignUpResult>{
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }

            override fun onSuccess(code: Int, responseData: SignUpResult) {
                Log.d(TAG, "onSuccess: $responseData")
                if(responseData.msg == "success"){
                    Toast.makeText(this@FoodAddActivity, "성공", Toast.LENGTH_SHORT).show()
                    val date = binding.textViewFoodAddDate.text.toString()
                    GetFoodList(school, date)
                }
            }

            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }

        })
    }
    private fun convertTimestampToDate(timespamp: Long){
        val sdf = SimpleDateFormat("yyyy년 MM월")
        val date = sdf.format(timespamp)

        binding.textViewFoodAddDate.text = date
        var year = date.substring(0,4)
        var month = date.substring(6,8)
        Log.d(TAG, "datetest: ${year}-${month}")
    }

    fun savaimage(uri: Uri){
        val file = File(uri!!.path)
        var fileExtension = contentResolver.getType(uri)
        var inputStream : InputStream? = null
        try{
            inputStream = this.contentResolver.openInputStream(uri!!)
        }catch (e : IOException){
            e.printStackTrace()
        }

        val bitmap = BitmapFactory.decodeStream(inputStream)
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,20,byteArrayOutputStream)
        val requestBody = RequestBody.create(MediaType.parse("image/*"),byteArrayOutputStream.toByteArray())
        val uploadFile = MultipartBody.Part.createFormData("image","${file.name}.${fileExtension?.substring(6)}",requestBody)

        uploadimage("food", key_id.toString(), uploadFile)
    }

    fun uploadimage(target: String, key: String, file: MultipartBody.Part){
        Log.d(TAG, "uploadimage: ....")
        ResponseService().uploadimage(target, key, file, object : RetrofitCallback<LoginResult>{
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }

            override fun onSuccess(code: Int, responseData: LoginResult) {
                Log.d(TAG, "onSuccess: $responseData")
            }

            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }
        })
    }

    fun GetFoodList(school: String, date: String){
        ResponseService().GetFoodList(school, date, object : RetrofitCallback<GetFoodList>{
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }

            override fun onSuccess(code: Int, responseData: GetFoodList) {
                Log.d(TAG, "onSuccess: $responseData")
                key_id = responseData.key_id
                savaimage(currentImageUri)
            }

            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }
        })
    }
}
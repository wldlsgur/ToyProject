package com.example.issueproject.res.Add

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.issueproject.databinding.ActivitySchoolAddBinding
import com.example.issueproject.retrofit.RetrofitCallback
import com.example.issueproject.service.ResponseService
import androidx.core.app.ActivityCompat.startActivityForResult

import android.provider.MediaStore

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.issueproject.dto.*
import com.example.issueproject.res.MenuActivity
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream

private const val TAG = "SchoolAddActivity"
class SchoolAddActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivitySchoolAddBinding.inflate(layoutInflater)
    }
    private lateinit var getResult: ActivityResultLauncher<Intent>
    private lateinit var currentImageUri: Uri
    var id : String = ""
    var name : String = ""
    val roomList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        roomList.clear()
        id = intent.getStringExtra("id")!!
        name = intent.getStringExtra("name")!!

        getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK && it.data !=null) {
                currentImageUri = it.data?.data!!
                try {
                    currentImageUri?.let {
                        Glide.with(this)
                            .load(currentImageUri)
                            .into(binding.imageViewSchool)
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

        binding.imageViewSchool.setOnClickListener {
            val intent: Intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            getResult.launch(intent)
        }

        binding.imageViewRoomAdd.setOnClickListener {
            val roomname = binding.editTextRoomName.text.toString()
            if(roomname != null){
                roomList.add(roomname)
                binding.editTextRoomName.text = null
                Toast.makeText(this, "반이 추가되었습니다.", Toast.LENGTH_SHORT).show()

                val adapter = ArrayAdapter(this@SchoolAddActivity, R.layout.simple_list_item_1, roomList)
                binding.classRv.adapter = adapter
            }
            else{
                Toast.makeText(this, "반이름을 작성해주세요", Toast.LENGTH_SHORT).show()
            }

        }

        binding.buttonSchoolAdd.setOnClickListener {
            Log.d(TAG, "onCreate: ")
            val school = binding.editTextSchoolName.text.toString()
            val number = binding.editTextSchoolNum.text.toString()

            for(i in 0..roomList.size-1) {
                val room = roomList[i]

                var presidentinfo = Presidentinfo(id, school, room, number)
                SchoolAdd(presidentinfo)
            }
//            val room = binding.editTextRoomName.text.toString()


        }
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

        uploadimage("president", id, uploadFile)
    }

    fun uploadimage(target: String, key: String, file: MultipartBody.Part){
        Log.d(TAG, "uploadimage: ....")
        ResponseService().uploadimage(target, key, file, object : RetrofitCallback<LoginResult>{

            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }

            override fun onSuccess(code: Int, responseData: LoginResult) {
                Log.d(TAG, "onSuccess: $responseData")
                Toast.makeText(this@SchoolAddActivity, "성공", Toast.LENGTH_SHORT).show()
                GetPresidentInfo(id, name)
            }

            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }
        })
    }

    fun SchoolAdd(info: Presidentinfo){
        ResponseService().CreatePresidentinfo(info, object: RetrofitCallback<SignUpResult>{
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }
            override fun onSuccess(code: Int, responseData: SignUpResult) {
                Log.d(TAG, "onSuccess: $responseData")
                if(responseData.msg == "success"){
                    savaimage(currentImageUri)
                }
            }
            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }
        })
    }

    fun GetPresidentInfo(id: String, name: String) {
        ResponseService().GetPresidentInfo(id, object : RetrofitCallback<MutableList<PresidentinfoResult>> {
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }

            override fun onSuccess(code: Int, responseData: MutableList<PresidentinfoResult>) {
                Log.d(TAG, "onSuccess: $responseData")

                var intent = Intent(this@SchoolAddActivity, MenuActivity::class.java).apply{
                    putExtra("id", id)
                    putExtra("name", name)
                    putExtra("school", responseData[0].school)
                    putExtra("room", responseData[0].room)
                    putExtra("img_url", responseData[0].image_url)
                }
                startActivity(intent)
            }
            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }
        })
    }
}
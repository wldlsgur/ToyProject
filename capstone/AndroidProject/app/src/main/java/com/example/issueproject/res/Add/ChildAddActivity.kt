package com.example.issueproject.res.Add

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.issueproject.R
import com.example.issueproject.databinding.ActivityChildAddBinding
import com.example.issueproject.retrofit.RetrofitCallback
import com.example.issueproject.service.ResponseService
import com.example.issueproject.dto.*
import com.example.issueproject.res.submenu.SubChildMunuActivity
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.*


private const val TAG = "ChildAddActivity"
class ChildAddActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityChildAddBinding.inflate(layoutInflater)
    }
    private lateinit var getResult: ActivityResultLauncher<Intent>
    var id: String = ""
    var school : String = ""
    var room : String = ""
    var key_id : String = ""
    val itemList = mutableListOf<String>()
    val roomList = mutableListOf<String>()
    var count:Int = 0
    private lateinit var currentImageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        id = intent.getStringExtra("id").toString()

        GetSchool()

        getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK && it.data !=null) {
                currentImageUri = it.data?.data!!
                try {
                    currentImageUri?.let {
                        Glide.with(this)
                            .load(currentImageUri)
                            .into(binding.imageView2)
                    }
                    count++
                }catch(e:Exception) {
                    e.printStackTrace()
                }
            } else if(it.resultCode == RESULT_CANCELED){
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }else{
                Log.d("ActivityResult","something wrong")
            }
        }

        binding.imageView2.setOnClickListener {
            val intent: Intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            getResult.launch(intent)
        }


        binding.spinnerSchool.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                school = itemList[position]
                GetRoom(school)
            }
        }

        binding.spinnerRoom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                room = roomList[position]
            }
        }

        binding.buttonChildAdd.setOnClickListener {
            val id = id
            val schoolname = school
            val roomname = room
            val childage = binding.editTextChildAge.text.toString()
            val childname = binding.editTextChildName.text.toString()
            val childspec = binding.editTextChildSpec.text.toString()
            val parentnum = binding.editTextParentNum.text.toString()

            if(childname == "" || childage == "" || childspec == "" || schoolname == "" || roomname == "" || parentnum == ""){
                Toast.makeText(this, "모든 정보를 기입해주세요", Toast.LENGTH_SHORT).show()
            }
            else{
                var parentinfo = ParentInfo(id, schoolname, roomname, parentnum, childname, childage, childspec)
                Log.d(TAG, "onCreate: $parentinfo")
                ChildAdd(parentinfo)
            }
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

        Log.d(TAG, "savaimage: $key_id")
        uploadimage("parent", key_id, uploadFile)
    }

    fun uploadimage(target: String, key: String, file: MultipartBody.Part){
        Log.d(TAG, "uploadimage: ....")
        ResponseService().uploadimage(target, key, file, object : RetrofitCallback<LoginResult>{

            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }

            override fun onSuccess(code: Int, responseData: LoginResult) {
                Log.d(TAG, "onSuccess: $responseData")
                Toast.makeText(this@ChildAddActivity, "성공", Toast.LENGTH_SHORT).show()
                var intent = Intent(this@ChildAddActivity, SubChildMunuActivity::class.java).apply {
                    putExtra("id", id)
                    Log.d(TAG, "onSuccess: $id")
                }
                startActivity(intent)
            }

            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }
        })
    }
    fun ChildAdd(info: ParentInfo){
        ResponseService().CreateParentinfo(info, object: RetrofitCallback<SignUpResult> {
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }

            override fun onSuccess(code: Int, responseData: SignUpResult) {
                Log.d(TAG, "onSuccess: $responseData")
                if(responseData.msg == "success"){
                    if(count != 0){
                        val childname = binding.editTextChildName.text.toString()
                        ChildInfo(id!!, childname!!)
                    }else{
                        Toast.makeText(this@ChildAddActivity, "성공", Toast.LENGTH_SHORT).show()
                        var intent = Intent(this@ChildAddActivity, SubChildMunuActivity::class.java).apply {
                            putExtra("id", id)
                            Log.d(TAG, "onSuccess: $id")
                        }
                        startActivity(intent)
                    }
                }
            }

            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }
        })
    }

    fun ChildInfo(id: String, name: String){
        ResponseService().ChildInfo(id, name, object : RetrofitCallback<ParentInfoResult> {
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }
            override fun onSuccess(code: Int, responseData: ParentInfoResult) {
                Log.d(TAG, "onSuccess: ..")
                key_id = responseData.key_id.toString()
                savaimage(currentImageUri)
            }

            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: ..")
            }
        })
    }

    fun GetSchool(){
        ResponseService().GetSchool(object : RetrofitCallback<MutableList<GetSchool>>{
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }

            override fun onSuccess(code: Int, responseData: MutableList<GetSchool>) {
                itemList.clear()
                itemList.add("어린이집")
                for(item in responseData) {
                    itemList.add(item.school)
                }
                val adapter = ArrayAdapter(this@ChildAddActivity, R.layout.spinner, itemList)
                  binding.spinnerSchool.adapter = adapter

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
                roomList.add("반")
                for(item in responseData) {
                    roomList.add(item.room)
                }

                val adapter = ArrayAdapter(this@ChildAddActivity, R.layout.spinner, roomList)
                binding.spinnerRoom.adapter = adapter
            }

            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }

        })
    }

}
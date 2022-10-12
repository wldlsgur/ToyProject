package com.example.issueproject.res.Album

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.issueproject.databinding.ActivityAddAlbumBinding
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import com.example.issueproject.Adapter.AddAlbumAdapter
import com.example.issueproject.dto.LoginResult
import com.example.issueproject.dto.SignUpResult
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
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

private const val TAG = "AddAlbumActivity"
class AddAlbumActivity : AppCompatActivity() {
    lateinit var AddAlbumAdapter: AddAlbumAdapter

    private val binding by lazy{
        ActivityAddAlbumBinding.inflate(layoutInflater)
    }
    var itemList = mutableListOf<Uri?>()
    var images = ArrayList<MultipartBody.Part>()
    var job : String = ""
    var id: String = ""
    var name: String =""
    var img_url: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val currentTime = System.currentTimeMillis()
        convertTimestampToDate(currentTime)

        val school = intent.getStringExtra("school")!!
        val room = intent.getStringExtra("room")!!
        job = intent.getStringExtra("job").toString()
        id = intent.getStringExtra("id").toString()
        name = intent.getStringExtra("name").toString()
        img_url = intent.getStringExtra("img_url").toString()

        binding.imageViewAlbumdatepicker.setOnClickListener {
            showDatePicker()
        }

        binding.imageViewAlbumImageAdd.setOnClickListener {
            var intent = Intent(Intent.ACTION_PICK)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT

            startActivityForResult(intent, 200)
        }

        binding.buttonAddAlbum.setOnClickListener {
            val title = binding.textViewAlbumTitle.text.toString()
            val date = binding.textViewAlbumdate.text.toString()

            for(i in 0..itemList.size-1){
                savaimage(itemList[i]!!)
            }
            Uploadimages(school, room, title, date, images)
            
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {   // 어떤 이미지도 선택하지 않은 경우
            Toast.makeText(this, "이미지를 선택하지 않았습니다.", Toast.LENGTH_LONG).show()
        } else {   // 이미지를 하나라도 선택한 경우
            if (data.clipData == null) {     // 이미지를 하나만 선택한 경우
                Log.e("single choice: ", data.data.toString())
                val imageUri = data.data
                itemList.add(imageUri)

                AddAlbumAdapter = AddAlbumAdapter(itemList)

                binding.imageRV.apply {
                    adapter = AddAlbumAdapter
                    layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
                }

            } else {      // 이미지를 여러장 선택한 경우
                val clipData = data.clipData
                Log.e("clipData", clipData!!.itemCount.toString())
                if (clipData.itemCount > 10) {   // 선택한 이미지가 11장 이상인 경우
                    Toast.makeText(this, "사진은 10장까지 선택 가능합니다.", Toast.LENGTH_LONG)
                        .show()
                } else {   // 선택한 이미지가 1장 이상 10장 이하인 경우
                    Log.e(TAG, "multiple choice")
                    for (i in 0 until clipData.itemCount) {
                        val imageUri = clipData.getItemAt(i).uri // 선택한 이미지들의 uri를 가져온다.
                        try {
                            Log.e("multiple choice: ", data.data.toString())
                            itemList.add(imageUri) //uri를 list에 담는다.
                        } catch (e: Exception) {
                            Log.e(TAG, "File select error", e)
                        }
                    }
                    AddAlbumAdapter = AddAlbumAdapter(itemList)

                    binding.imageRV.apply {
                        adapter = AddAlbumAdapter
                        layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
                    }
                     // 리사이클러뷰 수평 스크롤 적용
                }
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
        images.add(uploadFile)
        Log.d(TAG, "savaimage: ${images.size}")
    }

    fun Uploadimages(school: String, room: String, title: String, date: String, images: ArrayList<MultipartBody.Part>){
        ResponseService().Uploadimages(school, room, title, date, images, object: RetrofitCallback<LoginResult>{
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }

            override fun onSuccess(code: Int, responseData: LoginResult) {
                Log.d(TAG, "onSuccess: $code")
                Log.d(TAG, "onSuccess: $responseData")

                var intent = Intent(this@AddAlbumActivity, AlbumTeacherActivity::class.java).apply {
                    putExtra("school", school)
                    putExtra("room", room)
                    putExtra("job", job)
                    putExtra("id", id)
                    putExtra("name", name)
                    putExtra("img_url", img_url)
                }
                startActivity(intent)
            }

            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }

        })
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

        binding.textViewAlbumdate.text = date
        var year = date.substring(0,4)
        var month = date.substring(6,8)
        var day = date.substring(10,12)
        Log.d(TAG, "datetest: ${year}-${month}-${day}")
    }

    private fun updateDateInText(){
        var formatter = SimpleDateFormat("yyyy년 MM월 dd일")
        binding.textViewAlbumdate.text = formatter.format(cal.time)
    }

}
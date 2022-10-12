package com.example.issueproject.res

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.example.issueproject.databinding.ActivityMainBinding
import com.example.issueproject.dto.*
import com.example.issueproject.res.Add.SchoolAddActivity
import com.example.issueproject.res.Add.TeacherAddActivity
import com.example.issueproject.res.submenu.SubChildMunuActivity
import com.example.issueproject.res.viewmodel.MainViewModels
import com.example.issueproject.retrofit.RetrofitCallback
import com.example.issueproject.service.MyFirebaseMessagingService.Companion.CHANNEL_ID
import com.example.issueproject.service.ResponseService
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    var token : String = ""
    val name :String = ""
    val school :String = ""
    val room :String = ""
    val id : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.buttonLogin.setOnClickListener {
            Log.d(TAG, "onCreate: clicklogin")
            Login(binding.editTextID.text.toString(), binding.editTextPW.text.toString())
        }

        binding.buttonSignUp.setOnClickListener {
            Log.d(TAG, "onCreate: clicksignup")
            var intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        initFcm()
    }

    fun Login(id: String, pw: String){
        ResponseService().LoginCheckService(id, pw, object: RetrofitCallback<LoginResult>{
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
                Toast.makeText(this@MainActivity, "다시 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            override fun onSuccess(code: Int, responseData: LoginResult) {
                if(!responseData.res){
                    Log.d(TAG, "res: false")
                    if(responseData.msg == "not found"){
                        Log.d(TAG, "msg: not found")
                        Toast.makeText(this@MainActivity, "아이디를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
                        binding.editTextID.text = null
                    }else{
                        Log.d(TAG, "msg: failed")
                        Toast.makeText(this@MainActivity, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                    }
                    binding.editTextPW.text = null
                }
                else{
                    Log.d(TAG, "res: true")
                    if(responseData.msg == "success"){
                        Log.d(TAG, "msg: success")
                        Log.d(TAG, "onSuccess: $id")
                        GetUserInfo(id)
                    }
                }
            }
            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }
        })
    }
    fun GetUserInfo(id: String){
        ResponseService().GetUserInfo(id, object : RetrofitCallback<UserInfo> {
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }

            override fun onSuccess(code: Int, responseData: UserInfo) {
                Log.d(TAG, "onSuccess: $responseData")
                val name = responseData.name
                val job = responseData.job
                val id = binding.editTextID.text.toString()

                if(job == "원장님"){
                    GetPresidentInfo(id, name)
                }
                else if(job == "선생님"){
                    GetTeacherInfo(id, name)
                }

                else if(job == "부모님"){
                    var intent = Intent(this@MainActivity, SubChildMunuActivity::class.java).apply{
                        putExtra("id", id)
                        putExtra("name", name)
                        Log.d(TAG, "id: $id")
                        Log.d(TAG, "job: $responseData")
                    }
                    startActivity(intent)
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
                if(responseData.isEmpty()){
                    var intent = Intent(this@MainActivity, SchoolAddActivity::class.java).apply{
                        putExtra("id", id)
                        putExtra("name", name)
                    }
                    startActivity(intent)
                }
                else{
                    var intent = Intent(this@MainActivity, MenuActivity::class.java).apply{
                        putExtra("id", id)
                        putExtra("name", name)
                        putExtra("school", responseData[0].school)
                        putExtra("room", responseData[0].room)
                        putExtra("img_url", responseData[0].image_url)
                    }
                    startActivity(intent)
                }
            }

            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }
        })
    }

    fun GetTeacherInfo(id: String, name: String){
        ResponseService().GetTeacherInfo(id, object :RetrofitCallback<MutableList<TeacherinfoResult>>{
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }

            override fun onSuccess(code: Int, responseData: MutableList<TeacherinfoResult>) {
                Log.d(TAG, "onSuccess: $responseData")
                if(responseData.isEmpty()){
                    var intent = Intent(this@MainActivity, TeacherAddActivity::class.java).apply{
                        putExtra("id", id)
                        putExtra("name", name)
                    }
                    startActivity(intent)
                }
                else{
                    var intent = Intent(this@MainActivity, MainTeacherActivity::class.java).apply{putExtra("id", id)
                        putExtra("id", id)
                        putExtra("name", name)
                        putExtra("school", responseData[0].school)
                        putExtra("room", responseData[0].room)
                        putExtra("img_url", responseData[0].image_url)
                    }
                    startActivity(intent)
                }
            }

            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }

        })
    }
    //알람 토큰 통신
    fun CallAlarm(target_token : String){
        ResponseService().CallAlarm(
            target_token,
            object : RetrofitCallback<SignUpResult> {
                override fun onError(t: Throwable) {
                    Log.d(TAG, "token onError: $t")
                }

                override fun onSuccess(code: Int, responseData: SignUpResult) {
                    Log.d(TAG, "token onSuccess: $responseData")
                }

                override fun onFailure(code: Int) {
                    Log.d(TAG, "token onFailure: $code")
                }

            })

    }

    /**
     * FCM 토큰 수신 및 채널 생성
     */
    private fun initFcm() {
        // FCM 토큰 수신
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "FCM 토큰 얻기에 실패하였습니다.", task.exception)
                return@OnCompleteListener
            }
            // token log 남기기
            Log.d(TAG, "token: ${task.result?:"task.result is null"}")
            Log.d(TAG, "initFcm: ${task.result}")
            CallAlarm(task.result!!)

            // 유저 토큰 갱신 update
        })

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(CHANNEL_ID, "issue")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(id: String, name: String) {
        val importance = NotificationManager.IMPORTANCE_DEFAULT // or IMPORTANCE_HIGH
        val channel = NotificationChannel(id, name, importance)

        val notificationManager: NotificationManager
                = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

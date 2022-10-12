package com.example.issueproject.res

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.issueproject.R
import com.example.issueproject.databinding.ActivitySignUpBinding
import com.example.issueproject.dto.SignUpResult
import com.example.issueproject.dto.SingUpInfo
import com.example.issueproject.retrofit.RetrofitCallback
import com.example.issueproject.service.ResponseService

private const val TAG = "SignUpActivity"
class SignUpActivity : AppCompatActivity() {
    var job = ""
    var idcheck = false

    private val binding by lazy{
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonIdcheck.setOnClickListener {
            if(binding.editTextISignUpID.text.toString() == ""){
                Toast.makeText(this, "아이디를 입력 후 클릭해주세요", Toast.LENGTH_SHORT).show()
            }
            else {
                SameID(binding.editTextISignUpID.text.toString())
            }
        }

        binding.radioGroup.setOnCheckedChangeListener{ group, checkedid ->
            when(checkedid){
                R.id.radioButtonCaptain -> job = "원장님"
                R.id.radioButtonParrent -> job = "부모님"
                R.id.radioButtonTeacher -> job = "선생님"
            }
        }

        binding.buttonSingUpCheck.setOnClickListener {
            var id = binding.editTextISignUpID.text.toString()
            var pw = binding.editTextISignUpPW.text.toString()
            var name = binding.editTextISignUpName.text.toString()
            if(idcheck == false){
                Toast.makeText(this, "중복확인을 해주세요", Toast.LENGTH_SHORT).show()
            }
            if(job == ""){
                Toast.makeText(this, "해당 직업을 선택해주세요", Toast.LENGTH_SHORT).show()
            }
            if(id == "" || pw == "" || name == ""){
                Toast.makeText(this, "모든 정보를 기입해주세요", Toast.LENGTH_SHORT).show()
            }
            else{
                var signupinfo = SingUpInfo(id,pw,name,job)
                SignUp(signupinfo)
            }
        }
    }

    fun SignUp(data: SingUpInfo){
        ResponseService().SignUpService(data, object: RetrofitCallback<SignUpResult> {
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }

            override fun onSuccess(code: Int, responseData: SignUpResult) {
                Log.d(TAG, "onSuccess: $code")
                if(responseData.msg == "success"){
                    var intent = Intent(this@SignUpActivity, MainActivity::class.java)
                    startActivity(intent)
                }else if(responseData.msg == "failed"){

                }
            }

            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }
        })
    }

    fun SameID(id: String){
        ResponseService().Sameid(id, object: RetrofitCallback<SignUpResult>{
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }

            override fun onSuccess(code: Int, responseData: SignUpResult) {
                Log.d(TAG, "onSuccess: $code")
                if(responseData.msg == "found"){
                    Toast.makeText(this@SignUpActivity, "중복된 아이디입니다.", Toast.LENGTH_SHORT).show()
                    binding.editTextISignUpID.text = null
                }else if(responseData.msg == "not found"){
                    Toast.makeText(this@SignUpActivity, "사용할 수 있는 아이디입니다.", Toast.LENGTH_SHORT).show()
                    idcheck = true
                }
            }

            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }

        })
    }
}
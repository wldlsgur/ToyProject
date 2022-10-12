package com.example.issueproject.res;

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.issueproject.databinding.ActivityUpdateNoticBinding
import com.example.issueproject.dto.SignUpResult
import com.example.issueproject.dto.UpdateNotic
import com.example.issueproject.res.DayNotic.DayNoticActivity
import com.example.issueproject.res.DayNotic.DayNoticTeacherActivity
import com.example.issueproject.res.Notic.NoticActivity
import com.example.issueproject.retrofit.RetrofitCallback
import com.example.issueproject.service.ResponseService

private const val TAG = "UpdateNoticActivity"
class UpdateNoticActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityUpdateNoticBinding.inflate(layoutInflater)
    }

    var menu: String = ""
    var school: String = ""
    var job: String = ""
    var id: String = ""
    var room : String = ""
    var name: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        menu = intent.getStringExtra("menu").toString()
        school = intent.getStringExtra("school").toString()
        job = intent.getStringExtra("job").toString()
        val key_id = intent.getStringExtra("key_id")
        var content = intent.getStringExtra("content")
        var writer = intent.getStringExtra("writer")
        var date = intent.getStringExtra("date")
        var title = intent.getStringExtra("title")
        id = intent.getStringExtra("id").toString()
        name = intent.getStringExtra("name").toString()
        room = intent.getStringExtra("room").toString()

        binding.UpdateNoticMenu.text = menu
        binding.UpdateNoticWriter.text = writer
        binding.UpdateNoticContent.setText("$content")
        binding.UpdateNoticDate.text = date
        binding.UpdateNoticTitle.setText("$title")

        binding.UpdateBtn.setOnClickListener{
            Log.d(TAG, "onCreate: $key_id")

            title = binding.UpdateNoticTitle.text.toString()
            content = binding.UpdateNoticContent.text.toString()
            date = binding.UpdateNoticDate.text.toString()

            val noticinfo = UpdateNotic(key_id!!.toInt(), title!!, content!!, date!!)
            updateNotic(noticinfo)
        }

    }

    fun updateNotic(updatenoticinfo : UpdateNotic){
        ResponseService().UpdateNoticItem(updatenoticinfo, object : RetrofitCallback<SignUpResult> {
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }

            override fun onSuccess(code: Int, responseData: SignUpResult) {
                Log.d(TAG, "onSuccess: $responseData")
                Toast.makeText(this@UpdateNoticActivity, "수정이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                if(menu == "알림장" && job == "원장님"){
                    var intent1 = Intent(this@UpdateNoticActivity, DayNoticActivity::class.java).apply {
                        putExtra("menu", menu)
                        putExtra("school", school)
                    }
                    startActivity(intent1)
                }else if(menu == "알림장" && job == "선생님"){
                    var intent2 = Intent(this@UpdateNoticActivity, DayNoticTeacherActivity::class.java).apply {
                        putExtra("job", job)
                        putExtra("id", id)
                        putExtra("name", name)
                        putExtra("room", room)
                        putExtra("menu", menu)
                        putExtra("school", school)
                    }
                    startActivity(intent2)
                }else if(menu == "공지사항"){
                    var intent3 = Intent(this@UpdateNoticActivity, NoticActivity::class.java).apply {
                        putExtra("job", job)
                        putExtra("id", id)
                        putExtra("name", name)
                        putExtra("room", room)
                        putExtra("menu", menu)
                        putExtra("school", school)
                    }
                    startActivity(intent3)
                }
            }

            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }
        })
    }
}
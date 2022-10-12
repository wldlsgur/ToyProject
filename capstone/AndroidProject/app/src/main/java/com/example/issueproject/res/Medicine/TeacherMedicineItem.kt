package com.example.issueproject.res.Medicine
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.issueproject.R
import com.example.issueproject.res.Medicine.Teacher_MedicineInfo
import com.example.issueproject.res.Notic.NoticActivity

class TeacherMedicineItem : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicine_list_item)
        val con : ConstraintLayout = findViewById(R.id.ConstraintLayoutmlist)
        val btn: Button = findViewById(R.id.button_apply)
        val cname: TextView = findViewById(R.id.stu_cname)
        val mname: TextView = findViewById((R.id.stu_mname))
        con.setOnClickListener{
            var intent = Intent(this, Teacher_MedicineInfo::class.java).apply{
                putExtra("cname", cname.toString())
                putExtra("mname",mname.toString())
            }
            startActivity(intent)
        }
        btn.setOnClickListener {
            //서버로 적용
            //적용메시지 출력
        }
    }
}
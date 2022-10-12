package com.example.issueproject.res

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.example.issueproject.R
import com.example.issueproject.databinding.ActivityMenuNaviBinding
import com.example.issueproject.dto.DeleteInfo
import com.example.issueproject.dto.SignUpResult
import com.example.issueproject.res.Add.AddRoomActivity
import com.example.issueproject.res.Album.AlbumActivity
import com.example.issueproject.res.Calender.DailyActivity
import com.example.issueproject.res.DayNotic.DayNoticActivity
import com.example.issueproject.res.Foodlist.FoodlistActivity
import com.example.issueproject.res.Notic.NoticActivity
import com.example.issueproject.res.SchoolManager.SchoolTeacherListActivity
import com.example.issueproject.retrofit.RetrofitBuilder
import com.example.issueproject.retrofit.RetrofitCallback
import com.example.issueproject.service.ResponseService
import com.google.android.material.navigation.NavigationView

private const val TAG = "MenuActivity"
class MenuActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener{
    private val binding by lazy{
        ActivityMenuNaviBinding.inflate(layoutInflater)
    }
    var id: String = ""
    var school: String = ""
    var room: String = ""
    var name: String = ""
    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout
    lateinit var constraintLayout : ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        id = intent.getStringExtra("id").toString()
        name = intent.getStringExtra("name").toString()
        school = intent.getStringExtra("school").toString()
        room = intent.getStringExtra("room").toString()
        constraintLayout = findViewById(R.id.menu)
        val img_url = intent.getStringExtra("img_url")
        binding.menu.textViewName.text = name
        binding.menu.textViewSchool.text = school

        if(img_url != null){
            Glide.with(this)
                .load("${RetrofitBuilder.servers}/image/president/${img_url}")
                .into(binding.menu.imageViewPresident)
        }

        binding.menu.PresidentNotic.setOnClickListener {
            var intent = Intent(this, NoticActivity::class.java).apply {
                putExtra("school", school)
                putExtra("room", room)
                putExtra("job", "원장님")
                putExtra("name", name)
                putExtra("menu", "공지사항")
            }
            startActivity(intent)
        }


        binding.menu.PresidentAlbum.setOnClickListener {
            var intent = Intent(this, AlbumActivity::class.java).apply {
                putExtra("school", school)
                putExtra("room", room)
                putExtra("job", "원장님")
                putExtra("id", id)
                putExtra("name", name)
            }
            startActivity(intent)
        }
        binding.menu.PresidentDaliy.setOnClickListener {
            var intent = Intent(this, DailyActivity::class.java).apply{
                putExtra("school",school)
                putExtra("id",id)
                putExtra("job","원장님")
            }
            startActivity(intent)
        }
        binding.menu.PresidentDayNotic.setOnClickListener {
            var intent = Intent(this, DayNoticActivity::class.java).apply {
                putExtra("school", school)
                putExtra("room", room)
                putExtra("job", "원장님")
                putExtra("name", name)
                putExtra("menu", "알림장")
            }
            startActivity(intent)
        }
        binding.menu.PresidentFoodList.setOnClickListener {
            var intent = Intent(this, FoodlistActivity::class.java).apply {
                putExtra("school", school)
            }
            startActivity(intent)
        }
        binding.menu.PresidentMedicinemanagement.setOnClickListener {
//            var intent = Intent(this, ::class.java)
//            startActivity(intent)
        }
//        binding.menu.Buttontest.setOnClickListener {
//        var intent = Intent(this, testActivity::class.java)
//        startActivity(intent)
//    }

        val toolbar = binding.menuAppbarPresident.tool // toolBar를 통해 App Bar 생성
        toolbar.setTitle("알림장")
        setSupportActionBar(toolbar) // 툴바 적용
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 드로어를 꺼낼 홈 버튼 활성화
//        supportActionBar?.setHomeAsUpIndicator(R.drawable.menu) // 홈버튼 이미지 변경
        supportActionBar?.setDisplayShowTitleEnabled(true) // 툴바에 타이틀 안보이게

        // 네비게이션 드로어 생성
        drawerLayout = findViewById(R.id.drawer_layout_president)

        // 네비게이션 드로어 내에있는 화면의 이벤트를 처리하기 위해 생성
        navigationView = findViewById(R.id.nav_view_president)
        navigationView.setNavigationItemSelectedListener(this) //navigation 리스너
    }

    // 툴바 메뉴 버튼이 클릭 됐을 때 실행하는 함수
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // 클릭한 툴바 메뉴 아이템 id 마다 다르게 실행하도록 설정
        when (item!!.itemId) {
            android.R.id.home -> {
                // 햄버거 버튼 클릭시 네비게이션 드로어 열기
                drawerLayout.openDrawer(Gravity.LEFT)

            }
        }
        return super.onOptionsItemSelected(item)
    }

    // 드로어 내 아이템 클릭 이벤트 처리하는 함수
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        var intent2 = Intent(this, UserInfoChangeActivity::class.java).apply{
            putExtra("id", id)
            putExtra("job", "원장님")
            putExtra("school", school)
        }
        var intent3 = Intent(this, MainActivity::class.java)

        var schoolManage = Intent(this, SchoolTeacherListActivity::class.java).apply {
            putExtra("school",school)
        }

        var roomadd = Intent(this, AddRoomActivity::class.java).apply{
            putExtra("id", id)
            putExtra("name", name)
        }

        when (item.itemId) {
            R.id.menu_item1 -> startActivity(intent2)
            R.id.menu_item2 -> startActivity(schoolManage)
            R.id.menu_item3 -> Toast.makeText(this,"알림",Toast.LENGTH_SHORT).show()
            R.id.menu_item4 -> startActivity(intent3)
            R.id.menu_item5 -> showDialog()
            R.id.menu_item6 -> startActivity(roomadd)
        }
        return false
    }
    fun showDialog(){
        lateinit var dialog: AlertDialog
        val deleteinfo = DeleteInfo(id, "원장님")

        val builder =  AlertDialog.Builder(this)
        builder.setTitle("회원 탈퇴")

        builder.setMessage("정말 회원 탈퇴를 하시겠습니까?")

        val dialogClickListener = DialogInterface.OnClickListener{ _, which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE -> DeleteInfo(deleteinfo)  //yes 클릭시
                //DialogInterface.BUTTON_NEGATIVE -> toast("Negative/No button clicked.") // no 클릭시
                DialogInterface.BUTTON_NEUTRAL -> Toast.makeText(this, "취소하였습니다.", Toast.LENGTH_SHORT).show() // cancel 클릭시
            }
        }
        builder.setPositiveButton("예",dialogClickListener)
        //builder.setNegativeButton("아니오",dialogClickListener)
        builder.setNeutralButton("취소",dialogClickListener)
        dialog = builder.create()
        dialog.show()
    }

    fun DeleteInfo(deleteinfo: DeleteInfo){
        ResponseService().DeleteInfo(deleteinfo, object : RetrofitCallback<SignUpResult> {
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }

            override fun onSuccess(code: Int, responseData: SignUpResult) {
                Log.d(TAG, "onSuccess: $responseData")
                Toast.makeText(this@MenuActivity, "회원탈퇴가 정상적으로 이루어졌습니다.", Toast.LENGTH_SHORT).show()
                var intent = Intent(this@MenuActivity, MainActivity::class.java)
                startActivity(intent)
            }

            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }

        })
    }

}
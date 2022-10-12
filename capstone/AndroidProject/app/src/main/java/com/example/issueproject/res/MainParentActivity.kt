package com.example.issueproject.res

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.example.issueproject.R
import com.example.issueproject.databinding.ActivityMainParentNaviBinding
import com.example.issueproject.dto.DeleteInfo
import com.example.issueproject.dto.ParentInfoResult
import com.example.issueproject.dto.SignUpResult
import com.example.issueproject.res.Album.AlbumActivity
import com.example.issueproject.res.Album.AlbumTeacherActivity
import com.example.issueproject.res.Calender.DailyActivity
import com.example.issueproject.res.DayNotic.DayNoticActivity
import com.example.issueproject.res.DayNotic.DayNoticTeacherActivity
import com.example.issueproject.res.Foodlist.FoodlistActivity
import com.example.issueproject.res.Medicine.ParentsMedicineList
import com.example.issueproject.res.Notic.NoticActivity
import com.example.issueproject.res.SchoolManager.SchoolTeacherListActivity
import com.example.issueproject.retrofit.RetrofitBuilder
import com.example.issueproject.retrofit.RetrofitCallback
import com.example.issueproject.service.ResponseService
import com.google.android.material.navigation.NavigationView
import okhttp3.ResponseBody

private const val TAG = "MainParentActivity"
class MainParentActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener{
    private val binding by lazy{
        ActivityMainParentNaviBinding.inflate(layoutInflater)
    }

    var school : String = ""
    var room : String = ""
    var img_url : String = ""
    var position: Int = 0

    lateinit var id : String
    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        position = intent.getStringExtra("position")!!.toInt()
        id = intent.getStringExtra("id")!!

        GetParentInfo(id, position)

        Log.d(TAG, "school: $school")
        Log.d(TAG, "room: $room")
        binding.mainParent.ParentNotic.setOnClickListener{
            var intent = Intent(this, NoticActivity::class.java).apply {
                putExtra("school", school)
                putExtra("room", room)
                putExtra("job", "부모님")
                putExtra("name", binding.mainParent.textViewName.text)
                putExtra("menu", "공지사항")
            }
            startActivity(intent)
        }

        binding.mainParent.ParentAlbum.setOnClickListener {
            var intent = Intent(this, AlbumTeacherActivity::class.java).apply {
                putExtra("school", school)
                putExtra("room", room)
                putExtra("job", "부모님")
                putExtra("id", id)
                putExtra("position", position.toString())
            }
            startActivity(intent)
        }
        binding.mainParent.ParentDaliy.setOnClickListener {
//            var intent = Intent(this, CalenActivity::class.java)
//            startActivity(intent)
        }
        binding.mainParent.ParentDayNotic.setOnClickListener {
            var intent = Intent(this, DayNoticTeacherActivity::class.java).apply {
                putExtra("school", school)
                putExtra("room", room)
                putExtra("job", "부모님")
                putExtra("name", binding.mainParent.textViewName.text)
                putExtra("menu", "알림장")
                putExtra("id", id)
                putExtra("position", position.toString())
            }
            startActivity(intent)
        }
        binding.mainParent.ParentDaliy.setOnClickListener {
            var intent = Intent(this, DailyActivity::class.java).apply {
                putExtra("school", school)
                putExtra("id", id)
                putExtra("job", "원장님")
            }
        }
        binding.mainParent.ParentFoodList.setOnClickListener {
            var intent = Intent(this, FoodlistActivity::class.java).apply {
                putExtra("school", school)
            }
            startActivity(intent)
        }
        binding.mainParent.ParentMedicinemanagement.setOnClickListener {
            var intent = Intent(this, ParentsMedicineList::class.java).apply {
                putExtra("id", id.toString())
                putExtra("cname", binding.mainParent.textViewName.text)
                putExtra("school", school)
                putExtra("room", room)
                putExtra("img_url",img_url)
            }
            startActivity(intent)
        }
        val toolbar = binding.menuAppbarParent.tool // toolBar를 통해 App Bar 생성
        toolbar.setTitle("알림장")
        setSupportActionBar(toolbar) // 툴바 적용
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 드로어를 꺼낼 홈 버튼 활성화
//        supportActionBar?.setHomeAsUpIndicator(R.drawable.menu) // 홈버튼 이미지 변경
        supportActionBar?.setDisplayShowTitleEnabled(true) // 툴바에 타이틀 안보이게

        // 네비게이션 드로어 생성
        drawerLayout = findViewById(R.id.drawer_layout_parent)

        // 네비게이션 드로어 내에있는 화면의 이벤트를 처리하기 위해 생성
        navigationView = findViewById(R.id.nav_view_parent)
        navigationView.setNavigationItemSelectedListener(this) //navigation 리스너
    }
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
            putExtra("job", "부모님")
            putExtra("school", school)
            putExtra("name", binding.mainParent.textViewName.text.toString())
            putExtra("img_url", img_url)
            putExtra("position", position.toString())
        }
        var intent3 = Intent(this, MainActivity::class.java)


        when (item.itemId) {
            R.id.menu_item1 -> startActivity(intent2)
            R.id.menu_item2 -> Toast.makeText(this,"알림", Toast.LENGTH_SHORT).show()
            R.id.menu_item3 -> startActivity(intent3)
            R.id.menu_item4 -> showDialog()
        }
        return false
    }

    fun showDialog(){
        lateinit var dialog: AlertDialog
        val deleteinfo = DeleteInfo(id, "부모님")

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
                Toast.makeText(this@MainParentActivity, "회원탈퇴가 정상적으로 이루어졌습니다.", Toast.LENGTH_SHORT).show()
                var intent = Intent(this@MainParentActivity, MainActivity::class.java)
                startActivity(intent)
            }

            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }

        })
    }
    fun GetParentInfo(id: String, position: Int){
        ResponseService().GetParentInfo(id, object : RetrofitCallback<MutableList<ParentInfoResult>> {
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }

            override fun onSuccess(code: Int, responseData: MutableList<ParentInfoResult>) {
                Log.d(TAG, "onSuccess: $responseData")
                binding.mainParent.textViewSchool.text = responseData[position].school
                binding.mainParent.textViewRoom.text = responseData[position].room
                binding.mainParent.textViewName.text = responseData[position].child_name
                img_url = responseData[position].image_url
                Log.d(TAG, "onSuccess: ${img_url}")
                if(img_url != null){
                    Glide.with(this@MainParentActivity)
                        .load("${RetrofitBuilder.servers}/image/parent/${img_url}")
                        .into(binding.mainParent.imageViewChild)
                }else if(img_url == null || img_url == ""){
                    Glide.with(this@MainParentActivity)
                        .load(R.drawable.user)
                        .into(binding.mainParent.imageViewChild)
                }
                school = binding.mainParent.textViewSchool.text.toString()
                room = binding.mainParent.textViewRoom.text.toString()
            }

            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }
        })
    }
}
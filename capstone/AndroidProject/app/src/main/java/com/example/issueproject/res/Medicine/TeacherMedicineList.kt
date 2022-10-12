package com.example.issueproject.res.Medicine
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.issueproject.Adapterimport.MedicineListAdapter
import com.example.issueproject.databinding.ActivityMedicineListBinding
import com.example.issueproject.dto.GetMedicineManagement
import com.example.issueproject.dto.MedicineManage
import com.example.issueproject.dto.MedicineManagementResult
import com.example.issueproject.res.Medicine.Teacher_MedicineInfo
import com.example.issueproject.retrofit.RetrofitCallback
import com.example.issueproject.service.ResponseService

private const val TAG = "TeacherMedicineList"
class TeacherMedicineList : AppCompatActivity() {
    lateinit var MedicineListAdapter: MedicineListAdapter

    private val binding by lazy {
        ActivityMedicineListBinding.inflate(layoutInflater)
    }

    var img_url : String = ""
    var mo : Boolean = true;
    var lu : Boolean = true;
    var di : Boolean = true;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.medicinelistButtonAdd.visibility = View.INVISIBLE
        setContentView(binding.root)

        val school = intent.getStringExtra("school").toString()
        val room = intent.getStringExtra("room").toString()
        img_url = intent.getStringExtra("img_url").toString()
        //binding.textViewRoomName.text = room
        ShowRecycler(school, room)

        //button click event handler
        binding.buttonMor.setOnClickListener{
            mo = true
            lu = false
            di = false
            ShowRecycler(school, room)
        }
        binding.buttonLun.setOnClickListener{
            mo = false
            lu = true
            di = false
            ShowRecycler(school, room)
        }
        binding.buttonDin.setOnClickListener{
            mo = false
            lu = false
            di = true
            ShowRecycler(school, room)
        }
    }

    private fun initRecycler(list: MutableList<MedicineManagementResult>) {
        MedicineListAdapter = MedicineListAdapter(list)
        binding.RoomMedicineListRV.apply {
            adapter = MedicineListAdapter
            layoutManager = LinearLayoutManager(context)

            MedicineListAdapter.setItemClickListener(object: MedicineListAdapter.OnItemClickListener{
                override fun onClick(v: View, position: Int) {
                    var intent = Intent(this@TeacherMedicineList, Teacher_MedicineInfo::class.java).apply {
                        putExtra("img_url",img_url)
                        putExtra(
                            "id",
                            MedicineListAdapter.MedicineListViewHolder(v).id.toString()
                        )
                        putExtra(
                            "cname",
                            MedicineListAdapter.MedicineListViewHolder(v).cname.toString()
                        )
                        putExtra(
                            "mname",
                            MedicineListAdapter.MedicineListViewHolder(v).mname.toString()
                        )
                    }
                    startActivity(intent)
                }
            })
        }
    }

    private fun ShowRecycler(school:String, room:String) {
        ResponseService().MedicineListShow(
            school, room,
            object : RetrofitCallback<MutableList<MedicineManagementResult>> {
                override fun onError(t: Throwable) {
                    Log.d(TAG, "onError: $t")
                }

                override fun onSuccess(code: Int, responseData: MutableList<MedicineManagementResult>) {
                    Log.d(TAG, "onSuccess: $responseData")
                    val list : MutableList<MedicineManagementResult> = mutableListOf()
                    for(l in responseData)
                    {
                        if((l.morning=="true" && mo) || (l.lunch=="true" && lu) || (l.dinner=="true" && di)) list.add(l)
                    }

                    initRecycler(list)
                }

                override fun onFailure(code: Int) {
                    Log.d(TAG, "onFailure: $code")
                }

            })

    }
}
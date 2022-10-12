package com.example.issueproject.res.Medicine
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.issueproject.Adapterimport.MedicineListAdapter
import com.example.issueproject.databinding.ActivityMedicineListBinding
import com.example.issueproject.dto.MedicineManage
import com.example.issueproject.dto.MedicineManagementResult
import com.example.issueproject.res.Medicine.Parents_MedicineInfo
import com.example.issueproject.retrofit.RetrofitBuilder
import com.example.issueproject.retrofit.RetrofitCallback
import com.example.issueproject.service.ResponseService

private const val TAG = "ParentsMedicineList"
class ParentsMedicineList : AppCompatActivity() {
    lateinit var MedicineListAdapter: MedicineListAdapter

    private val binding by lazy {
        ActivityMedicineListBinding.inflate(layoutInflater)
    }

    var room : String = ""
    var school : String = ""
    var id : String = ""
    var cname : String = ""
    var img_url : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()

        Log.d(TAG, "onCreate: $id")
        Log.d(TAG, "onCreate: $cname")
        ShowRecycler(id, cname)

        binding.medicinelistButtonAdd.setOnClickListener {
            val add : Boolean = true
            var intent = Intent(this@ParentsMedicineList, Parents_MedicineInfo::class.java).apply {
                putExtra("add", add)
                putExtra("id",id)
                putExtra("cname",cname)
                putExtra("mname","NULL")
                putExtra("school", school)
                putExtra("room", room)
                putExtra("img_url",img_url)

            }
            startActivity(intent)
        }
    }

    fun init(){
        id = intent.getStringExtra("id").toString()
        cname = intent.getStringExtra("cname").toString()
        school = intent.getStringExtra("school").toString()
        room = intent.getStringExtra("room").toString()
        img_url = intent.getStringExtra("img_url").toString()


        binding.textViewRoomName.text = school + room
        binding.buttonMor.visibility = View.INVISIBLE
        binding.buttonLun.visibility = View.INVISIBLE
        binding.buttonDin.visibility = View.INVISIBLE
    }
    private fun initRecycler(list: MutableList<MedicineManagementResult>) {
        MedicineListAdapter = MedicineListAdapter(list)
        binding.RoomMedicineListRV.apply {
            adapter = MedicineListAdapter
            layoutManager = LinearLayoutManager(context)

            MedicineListAdapter.setItemClickListener(object: MedicineListAdapter.OnItemClickListener{
                override fun onClick(v: View, position: Int) {
                    val add : Boolean = false
                    var intent = Intent(this@ParentsMedicineList, Parents_MedicineInfo::class.java).apply {
                        putExtra("add", add)
                        putExtra("id",MedicineListAdapter.MedicineListViewHolder(v).id.toString())
                        putExtra("cname", MedicineListAdapter.MedicineListViewHolder(v).cname.toString())
                        putExtra("mname", MedicineListAdapter.MedicineListViewHolder(v).mname.toString())
                        putExtra("school", school)
                        putExtra("room", room)
                        putExtra("img_url",img_url)
                    }
                    startActivity(intent)
                }
            })
        }
    }

    private fun ShowRecycler(id: String, child_name : String) {
        ResponseService().ParentsMedicineListShow(
            id, child_name,
            object : RetrofitCallback<MutableList<MedicineManagementResult>> {
                override fun onError(t: Throwable) {
                    Log.d(TAG, "onError: $t")
                }

                override fun onSuccess(code: Int, responseData: MutableList<MedicineManagementResult>) {
                    Log.d(TAG, "onSuccess: $responseData")
                    initRecycler(responseData)
                }

                override fun onFailure(code: Int) {
                    Log.d(TAG, "onFailure: $code")
                }

            })

    }
}
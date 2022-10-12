package com.example.issueproject.res.Foodlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.issueproject.Adapter.FoodListAdapter
import com.example.issueproject.databinding.ActivityFoodlistBinding
import com.example.issueproject.dto.GetFoodList
import com.example.issueproject.res.Add.FoodAddActivity
import com.example.issueproject.retrofit.RetrofitCallback
import com.example.issueproject.service.ResponseService

private const val TAG = "FoodlistActivity"
class FoodlistActivity : AppCompatActivity() {
    lateinit var FoodListAdapter: FoodListAdapter
    var school :String = ""

    private val binding by lazy{
        ActivityFoodlistBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        school = intent.getStringExtra("school").toString()

        ShowRecycler(school)

        binding.foodListAddBtn.setOnClickListener{
            var intent = Intent(this, FoodAddActivity::class.java).apply {
                putExtra("school", school)
            }
            startActivity(intent)
        }

    }

    private fun initAdapter(lists:MutableList<GetFoodList>){
        FoodListAdapter = FoodListAdapter(lists)

        binding.foodListRvMonth.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            adapter = FoodListAdapter
        }
    }

    private fun ShowRecycler(school: String) {
        ResponseService().GetFoodListInfo(school, object : RetrofitCallback<MutableList<GetFoodList>> {
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }

            override fun onSuccess(code: Int, responseData: MutableList<GetFoodList>) {
                Log.d(TAG, "onSuccess: $responseData")
                for (i in 0..responseData.size-1){
                    initAdapter(responseData)
                }
            }

            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }
        })
    }
}
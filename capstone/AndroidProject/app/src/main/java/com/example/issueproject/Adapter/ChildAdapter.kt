package com.example.issueproject.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.issueproject.R
import com.example.issueproject.dto.GetSchoolManagement
import com.example.issueproject.dto.ParentInfoResult
import com.example.issueproject.dto.RoomChildListResult
import com.example.issueproject.res.MainParentActivity
import com.example.issueproject.retrofit.RetrofitBuilder

private const val TAG = "ChildAdapter"
class ChildAdapter(val context: Context) : RecyclerView.Adapter<ChildAdapter.ChildListViewHolder>() {

    var list: MutableList<ParentInfoResult> = mutableListOf()

    inner class ChildListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val childschool: TextView = itemView.findViewById(R.id.textViewChildItemSchool)
        private val childroom: TextView = itemView.findViewById(R.id.textViewChildItemRoom)
        private val childname: TextView = itemView.findViewById(R.id.textViewChildItemName)
        val childagree: TextView = itemView.findViewById(R.id.textViewChildItemAgree)
        private val childimage: ImageView = itemView.findViewById(R.id.imageViewChildItemImage)
        val deleteChildItem : ImageView = itemView.findViewById(R.id.deleteChildItem)

        val childCon : ConstraintLayout = itemView.findViewById(R.id.childConstraintLayout)

        fun bindinfo(data: ParentInfoResult){
            childschool.text = data.school
            childroom.text = data.room
            childname.text = data.child_name

            if(data.agree == "no"){
                childagree.text = "승인 필요"
                childagree.setTextColor(Color.parseColor("#DC143C"))
            }
            else if(data.agree == "yes"){
                childagree.text = "승인 완료"
                childagree.setTextColor(Color.parseColor("#2054B3"))
            }

            Log.d(TAG, "image_url: ${data.image_url}")
            Log.d(TAG, "bindinfo: ${RetrofitBuilder.servers}/image/parent/${data.image_url}")
            if(data.image_url != "default"){
                Glide.with(childimage.context)
                    .load("${RetrofitBuilder.servers}/image/parent/${data.image_url}")
                    .into(childimage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_child_item,parent,false)
        return ChildListViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ChildListViewHolder, position: Int) {
//        holder.bindinfo(list[position])
        var item = list[position]

        holder.apply {
            bindinfo(item)

            // 컨스트레인트 클릭 시
            childCon.setOnClickListener {
                childConClickListener.onClick(position, item)
            }

            deleteChildItem.setOnClickListener{
                childDeleteClickListener.onClick(position, item)
            }
        }
    }

    interface MenuClickListener {
        fun onClick(position: Int, item : ParentInfoResult)
    }

    private lateinit var childConClickListener : MenuClickListener
    fun setchildConClickListener(modifyClickListener: MenuClickListener) {
        this.childConClickListener = modifyClickListener
    }

    private lateinit var childDeleteClickListener : MenuClickListener
    fun setchildDeleteClickListener(modifyClickListener: MenuClickListener) {
        this.childDeleteClickListener = modifyClickListener
    }
}

package com.example.issueproject.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.issueproject.R
import com.example.issueproject.dto.GetCalenderDetail
import com.example.issueproject.retrofit.RetrofitBuilder
import com.example.issueproject.retrofit.RetrofitCallback
import com.example.issueproject.service.ResponseService

private const val TAG = "DailyDetailAdapter"
class DailyDetailAdapter(val context: Context, var list:MutableList<GetCalenderDetail>) : RecyclerView.Adapter<DailyDetailAdapter.DailyDetailAdapterListHolder>() {

    inner class DailyDetailAdapterListHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val startTime : TextView = itemView.findViewById(R.id.textViewStartTime)
        val endtTime : TextView = itemView.findViewById(R.id.textViewEndTime)
        val title : TextView = itemView.findViewById(R.id.textViewCalenderTitle)
        val content : TextView = itemView.findViewById(R.id.textViewCalenderContent)
        val moreimg : ImageView = itemView.findViewById(R.id.moreCalender_item)

        fun bindinfo(data: GetCalenderDetail){
            startTime.text = data.startTime
            endtTime.text = data.endTime
            title.text = data.title
            content.text = data.content
        }


    }


    override fun onCreateViewHolder(calender: ViewGroup, viewType: Int): DailyDetailAdapterListHolder {
        val view = LayoutInflater.from(calender.context).inflate(R.layout.activity_detail_calender_item,calender,false)
        return DailyDetailAdapterListHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: DailyDetailAdapterListHolder, position: Int) {


        holder.bindinfo(list[position])

        holder.moreimg.setOnClickListener {
            val popup = PopupMenu(context, holder.moreimg)
            MenuInflater(context).inflate(R.menu.popup_menu, popup.menu)

            popup.show()
            popup.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.modify -> {
                        modifyItemClickListener.onClick(position, list[position])
                        return@setOnMenuItemClickListener true
                    }
                    R.id.delete -> {
                        deleteItemClickListener.onClick(position, list[position])
                        return@setOnMenuItemClickListener true
                    }
                    else -> {
                        return@setOnMenuItemClickListener false
                    }
                }
            }
        }
        //holder.btn.setOnClickListener {
        //itemClickListener.onClickbtn(it, position)
        //}
    }
    interface OnItemClickListener {
        fun onClick(position: Int, data: GetCalenderDetail)

        //fun onClickbtn(v: View, position: Int)
    }
    private lateinit var modifyItemClickListener : OnItemClickListener
    fun setModifyItemClickListener(modifyClickListener: OnItemClickListener) {
        this.modifyItemClickListener = modifyClickListener
    }

    private lateinit var deleteItemClickListener : OnItemClickListener
    fun setDeleteItemClickListener(deleteClickListener: OnItemClickListener) {
        this.deleteItemClickListener = deleteClickListener
    }
}
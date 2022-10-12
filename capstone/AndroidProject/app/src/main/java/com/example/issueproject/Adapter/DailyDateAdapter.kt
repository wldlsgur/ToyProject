package com.example.issueproject.Adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.issueproject.Adapterimport.MedicineListAdapter
import com.example.issueproject.R
import com.example.issueproject.dto.CalenderResult
import java.util.*

private const val TAG = "DailyDateAdapter"
class DailyDateAdapter(val tmpMonth:Int, val dayList:MutableList<Date>, val data:MutableList<CalenderResult>) : RecyclerView.Adapter<DailyDateAdapter.DayViewHolder>(){
    val ROW = 6
    inner class DayViewHolder(val layout: View) : RecyclerView.ViewHolder(layout){

        var day : TextView = layout.findViewById<TextView>(R.id.fragment_calender_dayTv)
        var school : String = "shcool"
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.activity_daily_item2,parent,false)
        return DayViewHolder(view)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val title: ArrayList<String>
        val color: ArrayList<String>
        lateinit var date : ArrayList<String>
        date = arrayListOf()
        for(l in data)
        {
            Log.d(TAG, l.date)
            date.add(l.date)
        }

        //holder.day = holder.layout.findViewById<TextView>(R.id.fragment_calender_dayTv)
        holder.day.text = dayList[position].date.toString()
        holder.day.setTextColor(when(position%7){
            0 -> Color.RED
            6-> Color.BLUE
            else -> Color.BLACK
        })
        if(tmpMonth != dayList[position].month){
            holder.day.alpha = 0.4f
        }
        //2022-03-11 2022년 1월 33일
        //추가적으로 일정이 있는지 확인하는 구간
        for(i in 0..date.size-1){
            var month = date[i].substring(5,6).trim()
            Log.d(TAG, month)
            var monthOfday = date[i].substring(9,date[i].length).trim()
            Log.d(TAG, monthOfday)
            var strMonth = (dayList[position].month+1).toString()
            var strDay = holder.day.text.toString()

            if(dayList[position].month.toString().length == 1){
                strMonth = "0${strMonth}"
            }
            if(holder.day.text.toString().length == 1){
                strDay = "0${strDay}"
            }
            var strDate = "${strMonth}월 ${strDay}일"
            var comDate = "${month}월 ${monthOfday}일"
            var checkDay = holder.day.text.toString()
            if(checkDay.length == 1) {
                checkDay = "0${checkDay}"
            }


            Log.d(TAG, holder.day.text.toString())
            var printadapter = DailyItemAdapter(data, holder.day.text.toString())
            holder.layout.findViewById<RecyclerView>(R.id.fragment_calender_itemRv).apply {
                layoutManager = LinearLayoutManager(holder.layout.context)
                adapter = printadapter
            }
/*
            if(checkDay.equals(strDay)){
                if(strDate.equals(comDate)){
//                    holder.itemView.findViewById<ImageView>(R.id.fragment_calendar_point).visibility = View.VISIBLE
                    holder.itemView.findViewById<View>(R.id.calendar_View1).visibility = View.VISIBLE
                }
                else if(strDate.equals(comDate) && holder.itemView.findViewById<View>(R.id.calendar_View1).visibility == View.VISIBLE){
                    holder.itemView.findViewById<View>(R.id.calendar_View2).visibility = View.VISIBLE
                }
                else if(strDate.equals(comDate) && holder.itemView.findViewById<View>(R.id.calendar_View2).visibility == View.VISIBLE){
                    holder.itemView.findViewById<View>(R.id.calendar_View3).visibility = View.VISIBLE
                }
            }
            */

        }
        holder.layout.setOnClickListener{
            itemClickListener.onClick(it, position)
        }
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)

        //fun onClickbtn(v: View, position: Int)
    }
    // (3) 외부에서 클릭 시 이벤트 설정
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    // (4) setItemClickListener로 설정한 함수 실행
    private lateinit var itemClickListener : OnItemClickListener

    override fun getItemCount(): Int {
        return ROW*7
    }

}
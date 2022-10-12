package com.example.issueproject.Adapterimport

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.issueproject.R
import com.example.issueproject.dto.SchoolteacherListResult
import com.example.issueproject.dto.UserInfo
import com.example.issueproject.res.submenu.SubChildMunuActivity
import com.example.issueproject.retrofit.RetrofitBuilder
import com.example.issueproject.retrofit.RetrofitCallback
import com.example.issueproject.service.ResponseService

private const val TAG = "SchoolTeacherAdapter"
class SchoolTeacherListAdapter(val context: Context) : RecyclerView.Adapter<SchoolTeacherListAdapter.SchoolListViewHolder>() {

    var list: MutableList<SchoolteacherListResult> = mutableListOf()

    inner class SchoolListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        //변수 설정 room, number
        val number: TextView = itemView.findViewById(R.id.textViewSchool_teacher_list_item_number)
        val room: TextView = itemView.findViewById(R.id.textViewSchool_teacher_list_item_room)
        val name: TextView = itemView.findViewById(R.id.textViewSchool_teacher_list_item_name)
        private val image: ImageView = itemView.findViewById(R.id.imageViewTeacherImage)

        val chkApproval : ConstraintLayout = itemView.findViewById(R.id.schoolTeacherListItem_clApproval)
        val approval : TextView = itemView.findViewById(R.id.schoolTeacherListItem_btnApproval)
        val cancelApproval : TextView = itemView.findViewById(R.id.schoolTeacherListItem_btnCancelApproval)
//        val moreBtn : ImageView = itemView.findViewById(R.id.schoolTeacherListItem_ivMoreBtn)
        val childbtn : ConstraintLayout = itemView.findViewById(R.id.schoolTeacherListItem_clInfo)

        fun bindInfo(data: SchoolteacherListResult){

            if(data.agree == "yes") {
                chkApproval.visibility = View.GONE
            } else {
                chkApproval.visibility = View.VISIBLE
            }

            room.text = data.room
            number.text = data.number

            GetUserInfo(data.id)

            if(data.image_url != ""){
                Glide.with(image.context)
                    .load("${RetrofitBuilder.servers}/image/teacher/${data.image_url}")
                    .into(image)
            }
        }

        fun GetUserInfo(id: String){
            ResponseService().GetUserInfo(id, object : RetrofitCallback<UserInfo> {
                override fun onError(t: Throwable) {
                    Log.d(TAG, "onError: $t")
                }

                override fun onSuccess(code: Int, responseData: UserInfo) {
                    Log.d(TAG, "onSuccess: $responseData")
                    name.text = responseData.name
                }
                override fun onFailure(code: Int) {
                    Log.d(TAG, "onFailure: $code")
                }
            })
        }
    }

    override fun onCreateViewHolder(teacher: ViewGroup, viewType: Int): SchoolListViewHolder {
        val view = LayoutInflater.from(teacher.context).inflate(R.layout.activity_school_teacher_list_item,teacher,false)
        return SchoolListViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: SchoolListViewHolder, position: Int) {
        val item = list[position]

        holder.apply {

            childbtn.setOnClickListener{
                childbtnClickListener.onClick(position, item)
            }

            bindInfo(item)

            // 승인 버튼 클릭 이벤트
            approval.setOnClickListener {
                approvalClickListener.onClick(position, item)
            }

            // 승인취소 버튼 클릭 이벤트
            cancelApproval.setOnClickListener {
                cancelApprovalItemClickListener.onClick(position, item)
            }

//            // 수정, 삭제 팝업 메뉴 클릭 이벤트
//            moreBtn.setOnClickListener {
//                val popup = PopupMenu(context, moreBtn)
//                MenuInflater(context).inflate(R.menu.popup_menu, popup.menu)
//
//                popup.show()
//                popup.setOnMenuItemClickListener {
//                    when (it.itemId) {
//                        R.id.modify -> {
//                            modifyItemClickListener.onClick(position, item)
//                            return@setOnMenuItemClickListener true
//                        }
//                        R.id.delete -> {
//                            deleteItemClickListener.onClick(position, item)
//                            return@setOnMenuItemClickListener true
//                        }
//                        else -> {
//                            return@setOnMenuItemClickListener false
//                        }
//                    }
//                }
//            }
        }
    }


//    interface OnItemClickListener {
//        fun onClick(v: View, position: Int, item: SchoolteacherListResult)
//    }
//    private lateinit var itemClickListener : OnItemClickListener
//    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
//        this.itemClickListener = onItemClickListener
//    }


    interface MenuClickListener {
        fun onClick(position: Int, item : SchoolteacherListResult)
    }

    private lateinit var childbtnClickListener : MenuClickListener
    fun setChildLayoutClickListener(childbtnClickListener: MenuClickListener){
        this.childbtnClickListener = childbtnClickListener
    }
    private lateinit var modifyItemClickListener : MenuClickListener
    fun setModifyItemClickListener(modifyClickListener: MenuClickListener) {
        this.modifyItemClickListener = modifyClickListener
    }

    private lateinit var deleteItemClickListener : MenuClickListener
    fun setDeleteItemClickListener(deleteClickListener: MenuClickListener) {
        this.deleteItemClickListener = deleteClickListener
    }

    private lateinit var approvalClickListener : MenuClickListener
    fun setApprovalItemClickListener(approvalClickListener: MenuClickListener) {
        this.approvalClickListener = approvalClickListener
    }

    private lateinit var cancelApprovalItemClickListener : MenuClickListener
    fun setCancelApprovalItemClickListener(cancelApprovalItemClickListener : MenuClickListener) {
        this.cancelApprovalItemClickListener  = cancelApprovalItemClickListener
    }
}
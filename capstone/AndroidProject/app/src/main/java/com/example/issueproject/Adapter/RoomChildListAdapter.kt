import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import com.example.issueproject.dto.RoomChildListResult
import com.example.issueproject.dto.SchoolteacherListResult
import com.example.issueproject.retrofit.RetrofitBuilder

private const val TAG = "RoomChildListAdapter"
class RoomChildListAdapter(val context: Context) : RecyclerView.Adapter<RoomChildListAdapter.RoomListViewHolder>() {

    var list:MutableList<RoomChildListResult> = mutableListOf()

    inner class RoomListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val name: TextView = itemView.findViewById(R.id.textViewRoom_child_list_item_name)
        private val age: TextView = itemView.findViewById(R.id.textViewRoom_child_list_item_age)
        private val parentnumber: TextView = itemView.findViewById(R.id.textViewRoom_child_list_item_parentnumber)
        private val spec: TextView = itemView.findViewById(R.id.textViewRoom_child_list_item_spec)
        private val childimage: ImageView = itemView.findViewById(R.id.imageViewChildImage)

        val chkApproval : ConstraintLayout = itemView.findViewById(R.id.roomChildListItem_clApproval)
        val approval : TextView = itemView.findViewById(R.id.roomChildListItem_btnApproval)
        val cancelApproval : TextView = itemView.findViewById(R.id.roomChildListItem_btnCancelApproval)
//        val moreBtn : ImageView = itemView.findViewById(R.id.roomChildListItem_ivMoreBtn)


        fun bindinfo(data: RoomChildListResult){

            if(data.agree == "yes") {
                chkApproval.visibility = View.GONE
            } else {
                chkApproval.visibility = View.VISIBLE
            }

            name.text = data.child_name
            age.text = data.child_age
            parentnumber.text = data.number
            spec.text = data.spec

            if(data.image_url != ""){
                Glide.with(childimage.context)
                    .load("${RetrofitBuilder.servers}/image/parent/${data.image_url}")
                    .into(childimage)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_room_child_list_item,parent,false)
        return RoomListViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RoomListViewHolder, position: Int) {
        val item = list[position]

        holder.apply {
            bindinfo(item)

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


    interface MenuClickListener {
        fun onClick(position: Int, item : RoomChildListResult)
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
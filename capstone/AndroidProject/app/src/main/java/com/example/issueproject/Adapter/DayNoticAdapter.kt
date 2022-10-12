import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.issueproject.R
import com.example.issueproject.dto.AddManagement
import com.example.issueproject.dto.GetSchoolManagement

private const val TAG = "DayNoticAdapter"
class DayNoticAdapter(val context: Context, var job: String) : RecyclerView.Adapter<DayNoticAdapter.NoticeViewHolder>() {

    var list: MutableList<GetSchoolManagement> = mutableListOf()

    inner class NoticeViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        private val date: TextView = itemView.findViewById(R.id.textViewDay_notic_item_date)
        private val title: TextView = itemView.findViewById(R.id.textViewDay_notic_item_title)
        private val content: TextView = itemView.findViewById(R.id.textViewDay_notic_item_content)
        private val writer = itemView.findViewById<TextView>(R.id.textViewDay_notice_writer)

        val moreimg : ImageView = itemView.findViewById(R.id.Day_Notic_Moreimg)

        fun bindinfo(data: GetSchoolManagement, job: String){
            //"${data.year}년 ${data.month}월 ${data.day}일"
            date.text = data.date
            title.text = "제목: " + data.title
            content.text = data.content
            writer.text = data.writer

            Log.d(TAG, "bindinfo: $job")
            if(job == "부모님"){
                moreimg.visibility = View.GONE
            } else {
                moreimg.visibility = View.VISIBLE
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_day_notic_item,parent,false)
        return NoticeViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
//        holder.bindinfo(list[position])

        val item = list[position]

        holder.apply {
            bindinfo(item, job)

            // 수정, 삭제 팝업 메뉴 클릭 이벤트
            moreimg.setOnClickListener {
                val popup = PopupMenu(context, moreimg)
                MenuInflater(context).inflate(R.menu.popup_menu, popup.menu)

                popup.show()
                popup.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.modify -> {
                            modifyItemClickListener.onClick(position, item)
                            return@setOnMenuItemClickListener true
                        }
                        R.id.delete -> {
                            deleteItemClickListener.onClick(position, item)
                            return@setOnMenuItemClickListener true
                        }
                        else -> {
                            return@setOnMenuItemClickListener false
                        }
                    }
                }
            }
        }
    }

    interface MenuClickListener {
        fun onClick(position: Int, item : GetSchoolManagement)
    }

    private lateinit var modifyItemClickListener : MenuClickListener
    fun setModifyItemClickListener(modifyClickListener: MenuClickListener) {
        this.modifyItemClickListener = modifyClickListener
    }

    private lateinit var deleteItemClickListener : MenuClickListener
    fun setDeleteItemClickListener(deleteClickListener: MenuClickListener) {
        this.deleteItemClickListener = deleteClickListener
    }
}
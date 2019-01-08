package concertrip.sopt.com.concertrip.activities.main.fragment.calendar.adapter

import android.content.Context
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.interfaces.OnItemClick
import concertrip.sopt.com.concertrip.list.viewholder.HorizontalViewHolder
import concertrip.sopt.com.concertrip.model.CalendarTab
import kotlinx.android.synthetic.main.item_schedule.view.*
import org.jetbrains.anko.textColor


class CalendarTabListAdapter(
    private val mContext: Context,
    var dataList: ArrayList<CalendarTab>,
    var listener: OnItemClick?,
    var isUnderline: Boolean,
    var tabColorMap : HashMap<String?, Int>?
) : RecyclerView.Adapter<HorizontalViewHolder>() {
    var selected: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalViewHolder {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.li_tag, parent, false)
        return HorizontalViewHolder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: HorizontalViewHolder, position: Int) {

        holder.vUnderline.visibility = if (isUnderline && position == selected) View.VISIBLE else View.GONE

        if(position == selected){
            holder.tvtext.typeface = Typeface.DEFAULT_BOLD
            holder.tvtext.setTextColor(ContextCompat.getColor(mContext,R.color.white))
//            if(position>0) {
//                holder.tvtext.textColor=ContextCompat.getColor(mContext, R.color.textInfo)
//            }
//            else
                holder.tvtext.textColor=ContextCompat.getColor(mContext, R.color.white)

        }else{
            holder.tvtext.typeface = Typeface.DEFAULT
            holder.tvtext.setTextColor(ContextCompat.getColor(mContext, R.color.textInfo))
            holder.tvtext.textColor=ContextCompat.getColor(mContext, R.color.textInfo)
        }

        holder.tvtext.text = dataList[position].name

        holder.itemView.setOnClickListener {
            listener?.onItemClick(this, position)
        }
    }

    fun setSelect(position: Int) {
        this.selected = position
        notifyDataSetChanged()
    }
}
package concertrip.sopt.com.concertrip.list.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import concertrip.sopt.com.concertrip.R
import kotlinx.android.synthetic.main.li_alarm.view.*

class AlarmViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView){
    var ivimg : ImageView = itemView.findViewById(R.id.item_alarm_img)
    var tvtitle : TextView = itemView.findViewById(R.id.item_alarm_title)
    var tvdate : TextView = itemView.findViewById(R.id.item_alarm_date)
}
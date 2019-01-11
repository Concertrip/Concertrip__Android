package concertrip.sopt.com.concertrip.list.viewholder

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.interfaces.BasicListViewHolder

class AlarmViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView), BasicListViewHolder{

    override fun getMainTitle(): TextView =tvtitle

    override fun getSubTitle(): TextView? =tvBody

    override fun getBtn(): View? =null
    override fun getIvIcon(): ImageView= ivImg

    override fun setButton(context : Context, b: Boolean?) {}



    var ivImg : ImageView = itemView.findViewById(R.id.item_alarm_img)
    var tvtitle : TextView = itemView.findViewById(R.id.item_alarm_title)
    var tvBody : TextView = itemView.findViewById(R.id.item_alarm_body)


}
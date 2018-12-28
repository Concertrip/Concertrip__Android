package concertrip.sopt.com.concertrip.list.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import concertrip.sopt.com.concertrip.R


class HorizontalViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    val tvtext : TextView = itemView.findViewById(R.id.tv_text) as TextView
    val vUnderline : View = itemView.findViewById(R.id.v_underline) as View
}
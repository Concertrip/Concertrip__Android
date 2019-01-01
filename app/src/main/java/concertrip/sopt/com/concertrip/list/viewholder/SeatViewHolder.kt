package concertrip.sopt.com.concertrip.list.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import concertrip.sopt.com.concertrip.R
import kotlinx.android.synthetic.main.li_seat.view.*

class SeatViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
    var vBox : View = itemView.findViewById(R.id.v_seat)
    var tvGrade : TextView = itemView.findViewById(R.id.tv_seat_grade)
    var tvPrice : TextView = itemView.findViewById(R.id.tv_seat_price)
}
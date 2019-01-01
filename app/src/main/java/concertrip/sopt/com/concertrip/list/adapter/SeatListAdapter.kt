package concertrip.sopt.com.concertrip.list.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.list.viewholder.SeatViewHolder
import concertrip.sopt.com.concertrip.model.Seat

class SeatListAdapter (val mContext : Context, var dataList: ArrayList<Seat>) : RecyclerView.Adapter<SeatViewHolder>() {
    val seatColor = listOf(R.color.seat_1, R.color.seat_2) // 임의의 좌석 색상

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeatViewHolder {
        return SeatViewHolder(LayoutInflater.from(mContext).inflate(R.layout.li_seat, parent, false))
    }

    override fun getItemCount(): Int  = dataList.size

    override fun onBindViewHolder(holder: SeatViewHolder, position: Int) {
        holder.vBox.setBackgroundResource(seatColor[position%seatColor.size])
        holder.tvGrade.setText(dataList[position].seatGrade)
        holder.tvPrice.setText(dataList[position].seatPrice)
    }

}
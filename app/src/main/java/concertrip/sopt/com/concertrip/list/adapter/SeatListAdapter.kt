package concertrip.sopt.com.concertrip.list.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.list.viewholder.SeatViewHolder
import concertrip.sopt.com.concertrip.model.Seat

class SeatListAdapter (val mContext : Context, var dataList: ArrayList<Seat>) : RecyclerView.Adapter<SeatViewHolder>() {
    //val seatColor = listOf(R.color.seat_1, R.color.seat_2, R.color.seat_3) // 임의의 좌석 색상
    lateinit var seatColor : HashMap<String, Int>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeatViewHolder {
        seatColor = HashMap<String, Int>()
        seatColor["스탠딩"] = R.color.seatStanding
        seatColor["R석"] = R.color.seatR
        seatColor["S석"] = R.color.seatS
        seatColor["A석"] = R.color.seatA

        return SeatViewHolder(LayoutInflater.from(mContext).inflate(R.layout.li_seat, parent, false))
    }

    override fun getItemCount(): Int  = dataList.size

    override fun onBindViewHolder(holder: SeatViewHolder, position: Int) {
        //holder.vBox.setBackgroundResource(seatColor[position%seatColor.size])

        if(seatColor[dataList[position].seatGrade] == null)
            holder.vBox.setBackgroundResource(R.color.seatDefault)
        else holder.vBox.setBackgroundResource(seatColor[dataList[position].seatGrade]!!)

        holder.tvGrade.text = dataList[position].seatGrade
        holder.tvPrice.text = dataList[position].seatPrice
    }

}
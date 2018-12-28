package concertrip.sopt.com.concertrip.list.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.list.viewholder.TicketViewHolder
import concertrip.sopt.com.concertrip.model.Ticket

class TicketListAdapter(val mContext : Context, var dataList : ArrayList<Ticket>)  : RecyclerView.Adapter<TicketViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val view : View = LayoutInflater.from(mContext).inflate(R.layout.li_ticket, parent, false)
        return TicketViewHolder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        holder.title.text = dataList[position].title
        holder.date.text = dataList[position].date
        holder.place.text = dataList[position].place

        holder.itemView.setOnClickListener {

        }
    }


}
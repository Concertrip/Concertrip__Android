package concertrip.sopt.com.concertrip.list.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import com.bumptech.glide.Glide
import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.interfaces.OnFragmentInteractionListener
import concertrip.sopt.com.concertrip.list.viewholder.TicketViewHolder
import concertrip.sopt.com.concertrip.model.Ticket
import concertrip.sopt.com.concertrip.utillity.Constants
import java.text.SimpleDateFormat
import java.util.*

class TicketListAdapter(val mContext : Context, var dataList : ArrayList<Ticket>, var listener: OnFragmentInteractionListener)  : RecyclerView.Adapter<TicketViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val view : View = LayoutInflater.from(mContext).inflate(R.layout.li_ticket, parent, false)
        return TicketViewHolder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        if(position == 0){
            holder.prevTime.visibility = View.INVISIBLE
            holder.nextTime.visibility = View.VISIBLE
        }
        else if(position == itemCount-1){
            holder.nextTime.visibility = View.INVISIBLE
            holder.prevTime.visibility = View.VISIBLE
        }
        else{
            holder.prevTime.visibility = View.VISIBLE
            holder.nextTime.visibility = View.VISIBLE

        }

        //holder.name.text = dataList[position].name
        //holder.date.text = dataListDetail[position].date
        //holder.date.text = dataList[position].date
        //holder.location.text = dataList[position].location

        if(URLUtil.isValidUrl(dataList[position].img)){
            Glide.with(mContext).load(dataList[position].img).into(holder.img)
        }else{
            holder.img.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_ticket_empty))
        }

        holder.itemView.setOnClickListener {
//            listener.changeFragment(Constants.FRAGMENT_TICKET)
        }
    }


}
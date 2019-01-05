package concertrip.sopt.com.concertrip.list.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        holder.name.text = dataList[position].name
        //holder.date.text = dataListDetail[position].date
        holder.date.text = dataList[position].date
        holder.location.text = dataList[position].location

        holder.itemView.setOnClickListener {
            listener.changeFragment(Constants.FRAGMENT_TICKET)
        }
    }

//    val dayNum : List<String> = listOf("일", "월", "화", "수", "목", "금", "토")
//
//    private fun convertDate(input: String?) : String?{
//        var convertedDate = StringBuilder()
//
//            if(input != null){
//                val dateInfoList = input.split("T")
//                val dateFormat = SimpleDateFormat("yyyy-MM-dd").parse(input.split("T")[0])
//                val instance : Calendar = Calendar.getInstance()
//                instance.setTime(dateFormat)
//                val dayNumIdx = instance.get(Calendar.DAY_OF_WEEK)
//
//                val splitedList = dateInfoList[0].split("-")
//
//                convertedDate.append(splitedList[0]+"."+splitedList[1]+"."+splitedList[2]+"("+dayNum[dayNumIdx-1]+")")
//
//                return convertedDate.toString()
//            }
//            else{
//                return convertedDate.toString()
//            }
//    }

}
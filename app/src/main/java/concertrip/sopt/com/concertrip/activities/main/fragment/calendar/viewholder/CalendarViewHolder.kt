package concertrip.sopt.com.concertrip.activities.main.fragment.calendar.viewholder

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import concertrip.sopt.com.concertrip.R
import kotlinx.android.synthetic.main.li_calendar_date.view.*
import org.jetbrains.anko.find
import org.jetbrains.anko.textColor
import kotlin.properties.Delegates

class CalendarViewHolder(itemView : View)  : RecyclerView.ViewHolder(itemView){
     var lySchedule : GridLayout? = itemView.findViewById(R.id.ly_schedule)
     var  tvCalendar : TextView = itemView.findViewById(R.id.tv_calendar)

     fun setToday(mContext: Context, b : Boolean){
          if(b){
               tvCalendar.textColor=mContext.getColor(R.color.mainColorP)
          }else{
//               tvCalendar.textColor=mContext.getColor(R.color.black)

          }

     }
     fun setSelected(mContext: Context, b : Boolean){
          if(b){
               tvCalendar.background=ContextCompat.getDrawable(mContext,R.drawable.ic_circle)
               tvCalendar.textColor=mContext.getColor(R.color.white)
          }else{
               tvCalendar.background=null
               tvCalendar.textColor=mContext.getColor(R.color.black)
          }

     }
}
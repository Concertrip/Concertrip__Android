package concertrip.sopt.com.concertrip.activities.main.fragment.calendar.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import concertrip.sopt.com.concertrip.R
import kotlinx.android.synthetic.main.li_calendar_date.view.*
import org.jetbrains.anko.find
import kotlin.properties.Delegates

class CalendarViewHolder(itemView : View)  : RecyclerView.ViewHolder(itemView){
     var lySchedule : GridLayout? = itemView.findViewById(R.id.ly_schedule)
     var  tvCalendar : TextView = itemView.findViewById(R.id.tv_calendar)
}
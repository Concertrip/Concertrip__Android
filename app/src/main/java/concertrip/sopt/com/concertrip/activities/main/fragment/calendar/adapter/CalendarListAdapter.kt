package concertrip.sopt.com.concertrip.activities.main.fragment.calendar.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.activities.main.fragment.calendar.viewholder.CalendarViewHolder
import concertrip.sopt.com.concertrip.model.Schedule
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.CALENDAT_TYPE_BLANK
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.CALENDAR_TYPE_DATE
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.CALENDAR_TYPE_DAY
import java.util.*
import kotlin.properties.Delegates

class CalendarListAdapter(var mContext: Context, var dataList: ArrayList<String>,var scheduleMap : HashMap<Int,ArrayList<Schedule>>) : RecyclerView.Adapter<CalendarViewHolder>() {


    var onClickListener: View.OnClickListener? = null


    private var inflater: LayoutInflater by Delegates.notNull()


    init {
        this.inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {

        val view = when (viewType) {
            CALENDAR_TYPE_DAY -> {
                LayoutInflater.from(mContext).inflate(R.layout.li_calendar_day, parent, false)
            }
            else -> {
                LayoutInflater.from(mContext).inflate(R.layout.li_calendar_date, parent, false)

            }
        }
        return CalendarViewHolder(view)
    }


    override fun getItemCount(): Int = dataList.size

    override fun getItemViewType(position: Int): Int {
        return when {
            dataList[position].toIntOrNull() != null -> CALENDAR_TYPE_DATE
            dataList[position].isNotBlank() -> CALENDAR_TYPE_DAY
            else -> CALENDAT_TYPE_BLANK
        }
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {



        holder.tvCalendar.text=dataList[position]

        if(getItemViewType(position) == CALENDAR_TYPE_DATE) {
            val date : Int = dataList[position].toInt()

            if(scheduleMap.containsKey(date)){
                scheduleMap[date]?.forEach() { s ->
                    addCalendarItem(holder,s)
                }
            }

            holder.itemView.setOnClickListener {


                    val s = Schedule.getDummy(date)
                    if(scheduleMap[date].isNullOrEmpty()){
                        scheduleMap[date]=ArrayList<Schedule>()
                        scheduleMap[date]?.add(s)
                    }else {
                        scheduleMap[date]?.add(s)
                    }
                    addCalendarItem(holder, s)

            }
        }

    }


    var LIMIT_SCHEDULE_IN_ONE_BLOCK : Int= 70 // item 몇개 추가할지
    private fun addCalendarItem(holder: CalendarViewHolder, schedule: Schedule){



        val cnt =holder.lySchedule?.childCount ?:LIMIT_SCHEDULE_IN_ONE_BLOCK+1
        if(cnt >=LIMIT_SCHEDULE_IN_ONE_BLOCK){
            if(cnt==LIMIT_SCHEDULE_IN_ONE_BLOCK)
                addEllipsis(holder)
            return
        }else {
//        Log.d("$LOG_TAG/addCalendarItem",schedule.text)
            addSchedule(holder,schedule)
        }
    }

    private fun addSchedule(holder : CalendarViewHolder, schedule: Schedule){


        val scheduleView = inflater.inflate(R.layout.item_schedule, holder.lySchedule, false)
        holder.lySchedule?.addView(scheduleView)
    }


    private fun addEllipsis(holder : CalendarViewHolder){

        val scheduleView =inflater.inflate(R.layout.item_ellipsis, holder.lySchedule, false)
        holder.lySchedule?.addView(scheduleView)

    }

}
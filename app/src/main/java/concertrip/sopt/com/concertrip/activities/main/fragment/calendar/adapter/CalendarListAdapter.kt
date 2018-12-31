package concertrip.sopt.com.concertrip.activities.main.fragment.calendar.adapter

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.youtube.player.internal.s
import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.activities.main.fragment.calendar.viewholder.CalendarViewHolder
import concertrip.sopt.com.concertrip.interfaces.ListData
import concertrip.sopt.com.concertrip.interfaces.OnItemClick
import concertrip.sopt.com.concertrip.model.Schedule
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.CALENDAT_TYPE_BLANK
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.CALENDAR_TYPE_DATE
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.CALENDAR_TYPE_DAY
import kotlinx.android.synthetic.main.item_ellipsis.view.*
import kotlinx.android.synthetic.main.item_schedule.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

class CalendarListAdapter(
    private var mContext: Context,
    var dataList: ArrayList<String>,
    var scheduleMap :  HashMap<Int, ArrayList<Schedule>>,
    var listener : OnItemClick
) : RecyclerView.Adapter<CalendarViewHolder>(){

    var selected = -1

    private var inflater: LayoutInflater by Delegates.notNull()

    var resetSchedule = false

    private val colorArray = arrayListOf(R.color.mainColor, R.color.purple, R.color.gray, R.color.black)


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


    private fun getToday(): String {

        val now = System.currentTimeMillis()

        val date = Date(now)

        //연,월,일을 따로 저장

        val curYearFormat = SimpleDateFormat("yyyy", Locale.KOREA)

        val curMonthFormat = SimpleDateFormat("MM", Locale.KOREA)

        val curDayFormat = SimpleDateFormat("dd", Locale.KOREA)

        return curDayFormat.format(date)
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


        holder.tvCalendar.text = dataList[position]

        holder.setSelected(mContext, selected == position)
        holder.setToday(mContext, getToday() == dataList[position] && selected != position)

        if (getItemViewType(position) == CALENDAR_TYPE_DATE) {
            val date: Int = dataList[position].toInt()

            holder.lySchedule?.removeAllViews()
            if (scheduleMap.containsKey(date)) {
                scheduleMap[date]?.forEach() { s ->
                    addCalendarItem(holder, s)
                }
            }

            holder.itemView.setOnClickListener {

                selected = if (selected == position) -1
                else position

                //테스트용
                val s = Schedule.getDummy(date)
                if(scheduleMap[date].isNullOrEmpty()){
                    scheduleMap[date]=ArrayList<Schedule>()
                    scheduleMap[date]?.add(s)
                }else {
                    scheduleMap[date]?.add(s)
                }
                addCalendarItem(holder, s)


                notifyDataSetChanged()


                listener.onItemClick(this,position)

            }
        }

    }


    private var LIMIT_SCHEDULE_IN_ONE_BLOCK: Int = 3 // item 몇개 추가할지


    private fun addCalendarItem(holder: CalendarViewHolder, schedule: Schedule) {


        val cnt = holder.lySchedule?.childCount ?: LIMIT_SCHEDULE_IN_ONE_BLOCK+1
        if (cnt >= LIMIT_SCHEDULE_IN_ONE_BLOCK) {
            if (cnt == LIMIT_SCHEDULE_IN_ONE_BLOCK)
                addEllipsis(holder)
            return
        } else {
//        Log.d("$LOG_TAG/addCalendarItem",schedule.text)
            addSchedule(holder, schedule)
        }
    }

    private fun addSchedule(holder: CalendarViewHolder, schedule: Schedule) {


        val scheduleView = inflater.inflate(R.layout.item_schedule, holder.lySchedule, false)
        val cnt = holder.lySchedule?.childCount ?: 0
        scheduleView.iv_schedule.setColorFilter(ContextCompat.getColor(mContext, colorArray[cnt]))
        holder.lySchedule?.addView(scheduleView)
    }


    private fun addEllipsis(holder: CalendarViewHolder) {

        val cnt = holder.lySchedule?.childCount ?: 0

//        val blank = inflater.inflate(R.layout.item_ellipsis, holder.lySchedule, false)
//        blank.tv_ellipsis.text =""
//        holder.lySchedule?.addView(blank)
//
//
//
//
//        val blank2 = inflater.inflate(R.layout.item_ellipsis, holder.lySchedule, false)
//        blank2.tv_ellipsis.text =""
//        holder.lySchedule?.addView(blank2)
//


        val scheduleView = inflater.inflate(R.layout.item_ellipsis, holder.lySchedule, false)

//        scheduleView.tv_ellipsis.text = ("+$cnt")
        scheduleView.tv_ellipsis.text = "+"


        holder.lySchedule?.addView(scheduleView)
    }



}
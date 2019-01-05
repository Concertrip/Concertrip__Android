package concertrip.sopt.com.concertrip.activities.main.fragment.calendar.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.activities.main.fragment.calendar.viewholder.CalendarViewHolder
import concertrip.sopt.com.concertrip.interfaces.OnItemClick
import concertrip.sopt.com.concertrip.model.Schedule
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.CALENDAR_TYPE_BLANK
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.CALENDAR_TYPE_DATE
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.CALENDAR_TYPE_DAY
import kotlinx.android.synthetic.main.item_ellipsis.view.*
import kotlinx.android.synthetic.main.item_schedule.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap
import kotlin.properties.Delegates

class CalendarListAdapter(
    private var mContext: Context,
    var dataList: ArrayList<String>,
    var scheduleMap :  HashMap<Int, ArrayList<Schedule>>,
    var listener : OnItemClick,

    var tabColorMap : HashMap<String?, Int>?
) : RecyclerView.Adapter<CalendarViewHolder>(){

    //private val colorArray = arrayListOf(R.color.blue, R.color.purple, R.color.gray, R.color.black)

    var selected = -1

    private var inflater: LayoutInflater by Delegates.notNull()
    init {
        this.inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view = when (viewType) {
            CALENDAR_TYPE_DAY ->
                LayoutInflater.from(mContext).inflate(R.layout.li_calendar_day, parent, false)
            else ->
                LayoutInflater.from(mContext).inflate(R.layout.li_calendar_date, parent, false)
        }
        return CalendarViewHolder(view)
    }

    private fun getToday(): String {

        val now = System.currentTimeMillis()
        val date = Date(now)

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
            else -> CALENDAR_TYPE_BLANK
        }
    }


    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {

        holder.tvCalendar.text = dataList[position]

        holder.setSelected(mContext, selected == position)

        if (getItemViewType(position) == CALENDAR_TYPE_DATE) {
            val date: Int = dataList[position].toInt()

            holder.lySchedule?.removeAllViews()
            if (scheduleMap.containsKey(date)) {
                scheduleMap[date]?.forEach() {
                    addCalendarItem(holder, it)
                }
            }

            holder.itemView.setOnClickListener {

                //선택된 아이템 재선택시 선택 취소
                selected = if (selected == position) -1
                else position

                notifyDataSetChanged()

                listener.onItemClick(this,position)

            }
        }

    }


    private var LIMIT_SCHEDULE_IN_ONE_BLOCK: Int = 3 // item 몇개 추가할지

    private fun addCalendarItem(holder: CalendarViewHolder, schedule: Schedule) {

        val cnt = holder.lySchedule?.childCount ?: LIMIT_SCHEDULE_IN_ONE_BLOCK+1
        when {
            cnt >= LIMIT_SCHEDULE_IN_ONE_BLOCK -> return
            cnt == LIMIT_SCHEDULE_IN_ONE_BLOCK -> addEllipsis(holder)
            else -> addSchedule(holder, schedule)
        }
    }

    private fun addSchedule(holder: CalendarViewHolder, schedule: Schedule) {
        val scheduleView = inflater.inflate(R.layout.item_schedule, holder.lySchedule, false)
        val cnt = holder.lySchedule?.childCount ?: 0
        scheduleView.iv_schedule.setColorFilter(ContextCompat.getColor(mContext, tabColorMap?.get(schedule.tabId)?:R.color.tab_except))
        holder.lySchedule?.addView(scheduleView)
    }


    private fun addEllipsis(holder: CalendarViewHolder) {
        val scheduleView = inflater.inflate(R.layout.item_ellipsis, holder.lySchedule, false)
        scheduleView.tv_ellipsis.text = "+"
        holder.lySchedule?.addView(scheduleView)
    }
}
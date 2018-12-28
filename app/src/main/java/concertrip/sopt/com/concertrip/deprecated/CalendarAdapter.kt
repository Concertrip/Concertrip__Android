package concertrip.sopt.com.concertrip.deprecated


/**

 @Deprecated

 *

 */

//class CalendarAdapter(var activity : Activity,var scheduleMap : HashMap<Int,ArrayList<Schedule>>) : BaseAdapter() {

//    private val CALENDAR_TYPE_DAY = 0
//    private val CALENDAT_TYPE_BLANK = 1
//    private val CALENDAR_TYPE_DATE = 2
//
//    private val LOG_TAG : String = this::class.java.simpleName
//
//    private val mActivity: WeakReference<Activity> = WeakReference<Activity>(activity)
//
//    private var dayList = ArrayList<String>()
//
//    private var inflater: LayoutInflater by Delegates.notNull()
//    private var mCal: Calendar by Delegates.notNull()
//
//    private var gridView : GridView by Delegates.notNull()
//
//    private val viewHolderList = arrayOfNulls<CalendarViewHolder>(50)
//
//
//    /**
//     * 생성자
//     *
//     * @param activity
//     */
//
//    init{
//
//        val mContext = activity.applicationContext
//
//        this.inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//
//        val now = System.currentTimeMillis()
//
//        val date = Date(now)
//
//        //연,월,일을 따로 저장
//
//        val curYearFormat = SimpleDateFormat("yyyy", Locale.KOREA)
//
//        val curMonthFormat = SimpleDateFormat("MM", Locale.KOREA)
//
//        val curDayFormat = SimpleDateFormat("dd", Locale.KOREA)
//
//
//
//        mActivity.get()?.apply {
//            tv_year.text  = curYearFormat.format(date)
//            tv_month.text=curMonthFormat.format(date)
////            gridView = gv_calendar
//        }
//
//
//
//
//        //gridview 요일 표시
//
//        dayList = ArrayList()
//
//        dayList.add("일")
//        dayList.add("월")
//        dayList.add("화")
//        dayList.add("수")
//        dayList.add("목")
//        dayList.add("금")
//        dayList.add("토")
//
//        mCal = Calendar.getInstance()
//
//
//        //이번달 1일 무슨요일인지 판단 mCal.set(Year,Month,Day)
//
//        mCal.set(Integer.parseInt(curYearFormat.format(date)), Integer.parseInt(curMonthFormat.format(date)) - 1, 1)
//
//        val dayNum = mCal.get(Calendar.DAY_OF_WEEK)
//
//        //1일 - 요일 매칭 시키기 위해 공백 add
//
//        for (i in 1 until dayNum) {
//            dayList.add("")
//        }
//
//        setCalendarDate(mCal.get(Calendar.MONTH) + 1)
//
//
//
//
//
//    }
//
//
//    override fun getItem(position: Int): Any = dayList[position]
//
//    override fun getItemId(position: Int): Long =position.toLong()
//
//    override fun getCount(): Int = dayList.size
//
//    override fun getItemViewType(position: Int): Int {
//        return when {
//            dayList[position].toIntOrNull() != null -> CALENDAR_TYPE_DATE
//            dayList[position].isNotBlank() -> CALENDAR_TYPE_DAY
//            else -> CALENDAT_TYPE_BLANK
//        }
////            return super.getItemViewType(position)
//    }
//
//    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
//
//
//        Log.d("$LOG_TAG/getView()","position : $position")
//
//        val holder: CalendarViewHolder?;
//
//
//        val convertView: View
//        if (view == null || viewHolderList[position]==null) {
//            convertView= makeGridItem(position,parent)
//            holder= convertView.tag as CalendarViewHolder
//
//        } else {
//            convertView = viewHolderList[position]!!.itemView
//            holder = convertView.tag as CalendarViewHolder;
//
//        }
//
//        holder.tvCalendar.text =  getItem(position).toString();
//
//        //해당 날짜 텍스트 컬러,배경 변경
//
//        mCal = Calendar.getInstance();
//
//        //오늘 day 가져옴
//
//        val today: Int = mCal.get(Calendar.DAY_OF_MONTH);
//
//        val sToday: String = today.toString()
//
//
//        if (sToday == getItem(position)) { //오늘 date 텍스트 컬러 변경
//            holder.tvCalendar.setTextColor(Color.WHITE)
//            holder.itemView.setBackgroundColor(Color.RED)
//        }
//
//        return convertView;
//    }
//    //position : 만들어질 View의 Index
//    private fun makeGridItem(position: Int,parent: ViewGroup?) : View{
//
//
//        Log.d("$LOG_TAG/makeGridItem","---position : $position")
//
//        viewHolderList[position]?.let {
//
//            Log.d("$LOG_TAG/makeGridItem","already created")
//            return it.itemView
//        }
//
//        Log.d("$LOG_TAG/makeGridItem","dayList[position] : ${getItem(position)}")
//        Log.d("$LOG_TAG/makeGridItem","viewType : ${getItemViewType(position)}")
//        Log.d("$LOG_TAG/makeGridItem","create now")
//
//
//        val viewType = getItemViewType(position)
//        var date : Int? = null
//        val convertView = when(viewType){
//           CALENDAR_TYPE_DAY->{
//               inflater.inflate(R.layout.item_calendar_day,parent, false)
//           }
//            CALENDAR_TYPE_DATE->{
//                date = getItem(position).toString().toInt()
//                inflater.inflate(R.layout.item_calendar, parent, false)
//            }
//            else->{
//                inflater.inflate(R.layout.item_calendar, parent, false)
//            }
//        }
//        val holder = CalendarViewHolder(convertView)
//        viewHolderList[position]=holder
//
//
//        date?.let { it ->
//            if(scheduleMap.containsKey(it)){
//                scheduleMap[it]?.forEach() { s ->
//                    addCalendarItem(position,s)
//                }
//            }
//
//            convertView.setOnItemClickListener { _ ->
//                val s = Schedule.getDummy(date)
//                if(scheduleMap[it].isNullOrEmpty()){
//                    scheduleMap[it]=ArrayList<Schedule>()
//                    scheduleMap[it]?.add(s)
//                }else {
//                    scheduleMap[it]?.add(s)
//                }
//                addCalendarItem(position, s)
//            }
//
//        }
//
//
//        convertView.tag = holder
//        return convertView
//
//    }
//
//    /**
//
//     * 해당 월에 표시할 일 수 구함
//
//     *
//
//     * @param month
//
//     */
//
//    private fun setCalendarDate(month : Int) {
//        mCal.set(Calendar.MONTH, month - 1);
//        for ( i  in 0 until mCal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
//            dayList.add("" + (i + 1));
//        }
//    }
//
//
//    var LIMIT_SCHEDULE_IN_ONE_BLOCK : Int= 7
//    private fun addCalendarItem(position : Int, schedule: Schedule){
//
//
//
//        val cnt =viewHolderList[position]?.lySchedule?.childCount ?:LIMIT_SCHEDULE_IN_ONE_BLOCK+1
//        if(cnt >=LIMIT_SCHEDULE_IN_ONE_BLOCK){
//            if(cnt==LIMIT_SCHEDULE_IN_ONE_BLOCK)
//                addEllipsis(position)
//            return
//        }else {
////        Log.d("$LOG_TAG/addCalendarItem",schedule.text)
//            addSchedule(position,schedule)
//        }
//    }
//
//    private fun addSchedule(position : Int, schedule: Schedule){
//
//        val holder = viewHolderList[position] as CalendarViewHolder
//
//        val scheduleView = inflater.inflate(R.layout.item_schedule, holder.lySchedule, false)
//        schedule.position=position
//        holder.lySchedule?.addView(scheduleView)
//    }
//
//
//    private fun addEllipsis(position : Int){
//
//        val holder = viewHolderList[position] as CalendarViewHolder
//
//        val scheduleView =inflater.inflate(R.layout.item_ellipsis, holder.lySchedule, false)
//        holder.lySchedule?.addView(scheduleView)
//
//    }
//

//}



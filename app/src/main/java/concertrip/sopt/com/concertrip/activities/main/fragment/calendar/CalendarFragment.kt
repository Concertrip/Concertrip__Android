package concertrip.sopt.com.concertrip.activities.main.fragment.calendar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import concertrip.sopt.com.concertrip.activities.AlarmActivity
import concertrip.sopt.com.concertrip.activities.main.fragment.calendar.adapter.CalendarListAdapter
import concertrip.sopt.com.concertrip.interfaces.OnFragmentInteractionListener
import concertrip.sopt.com.concertrip.interfaces.OnItemClick
import concertrip.sopt.com.concertrip.list.adapter.BasicListAdapter
import concertrip.sopt.com.concertrip.model.Concert
import concertrip.sopt.com.concertrip.model.Schedule
import kotlin.properties.Delegates
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.interfaces.ListData
import android.util.Log
import concertrip.sopt.com.concertrip.interfaces.OnResponse
import concertrip.sopt.com.concertrip.activities.main.fragment.calendar.adapter.CalendarTabListAdapter
import concertrip.sopt.com.concertrip.model.CalendarTab
import concertrip.sopt.com.concertrip.network.ApplicationController
import concertrip.sopt.com.concertrip.network.NetworkService
import concertrip.sopt.com.concertrip.network.response.GetCalendarResponse
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel
import concertrip.sopt.com.concertrip.network.response.GetCalendarTabResponse
import concertrip.sopt.com.concertrip.network.response.TabData
import concertrip.sopt.com.concertrip.utillity.Constants
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.TYPE_CONCERT
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.USER_TOKEN
import concertrip.sopt.com.concertrip.utillity.NetworkUtil
import concertrip.sopt.com.concertrip.utillity.Secret
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class CalendarFragment : Fragment(), OnItemClick, OnResponse {

    var year: Int  by Delegates.notNull()
    var month: Int by Delegates.notNull()


//    private val monthImgList = listOf<Int>(
//        R.drawable.m_1, R.drawable.m_2, R.drawable.m_3,
//        R.drawable.m_4, R.drawable.m_5, R.drawable.m_6,
//        R.drawable.m_7, R.drawable.m_8, R.drawable.m_9,
//        R.drawable.m_10, R.drawable.m_11, R.drawable.m_12
//    )

    private var dataListTag = ArrayList<CalendarTab>()

    private lateinit var dayList: ArrayList<String>
    private var scheduleMap: HashMap<Int, ArrayList<Schedule>> by Delegates.notNull()
    private var tabColorMap: HashMap<String?, Int> by Delegates.notNull()

    private var dataListDetail = arrayListOf<ListData>()

    private lateinit var tabAdapter: CalendarTabListAdapter
    private lateinit var calendarAdapter: CalendarListAdapter
    private lateinit var detailAdapter: BasicListAdapter


    private var listener: OnFragmentInteractionListener? = null

    private val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    //private val tabColor = listOf(R.color.tab_1, R.color.tab_2, R.color.tab_3, R.color.tab_4, R.color.tab_5,R.color.tab_6, R.color.tab_7, R.color.tab_8, R.color.tab_9, R.color.tab_10)


    override fun onItemClick(root: RecyclerView.Adapter<out RecyclerView.ViewHolder>, position: Int) {
        activity?.progress_bar?.visibility=View.VISIBLE
        if (root is CalendarTabListAdapter) {

            activity?.let {
            tv_detail?.text="날짜를 선택해주세요"

            }
            clearDetailList()

            tabAdapter.setSelect(position)


            NetworkUtil.getCalendarList(
                networkService,
                this,
                dataListTag[position].type,
                dataListTag[position]._id,
                year.toString(),
                month.toString(),
                null
            )

        } else if (root is CalendarListAdapter) {
            if(dataListTag.size==0){
                activity?.toast("정보를 받아오는 중입니다.")
                return
            }
            if (calendarAdapter.selected == -1) {
//                clearDetailList()
                recycler_view_calendar_detail.visibility = View.GONE
                tv_detail.text="날짜를 선택해주세요"
            } else {


                NetworkUtil.getCalendarList(
                    networkService,
                    this,
                    dataListTag[tabAdapter.selected].type,
                    dataListTag[tabAdapter.selected]._id,
                    year.toString(),
                    month.toString(),
                    dayList[position]
                )
            }

        }
    }


    override fun onSuccess(obj: BaseModel, position: Int?) {
        activity?.progress_bar?.visibility=View.GONE
        if (obj is GetCalendarResponse) {
            when (position) {
                Constants.TYPE_MONTH -> {

                    tv_detail?.text="날짜를 선택해주세요"
                    clearDetailList()

                    val map = obj.toScheduleMap()
                    calendarAdapter.scheduleMap.clear()
                    calendarAdapter.scheduleMap.putAll(map)

                    calendarAdapter.notifyDataSetChanged()
                }
                Constants.TYPE_DAY -> {
                    updateCalendarDetail(obj.toConcertArray())
                }
            }
        }
    }

    override fun onFail(status: Int) {
        activity?.progress_bar?.visibility=View.GONE
//        when(status){
//            Secret.NETWORK_NO_DATA->{
//            }
//        }
                emptyResult()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialUI()
    }


    private fun initialUI() {
        btn_notification.setOnClickListener {
            startActivity(Intent(activity, AlarmActivity::class.java))
        }

        activity?.let {

            scheduleMap =  HashMap<Int, ArrayList<Schedule>>()
            tabColorMap = HashMap<String?, Int>()

            calendarAdapter = CalendarListAdapter(it.applicationContext, makeDayList(), scheduleMap, this/*, tabColorMap*/)
            recycler_view_calendar.layoutManager = GridLayoutManager(it.applicationContext, 7)
            recycler_view_calendar.adapter = calendarAdapter

            detailAdapter = BasicListAdapter(it.applicationContext, dataListDetail, this)
            detailAdapter.mode = TYPE_CONCERT
            recycler_view_calendar_detail.adapter = detailAdapter


            tabAdapter =
                    CalendarTabListAdapter(
                        it.applicationContext,
                        dataListTag,
                        this,
                        false,
                        tabColorMap
                    )
            recycler_view_filter.adapter = tabAdapter

            connectRequestTabData()

            NetworkUtil.getCalendarList(
                networkService,
                this,
                "all",
                "",
                year.toString(),
                month.toString(),
                null
            )

        }


    }

    private var mCal: Calendar by Delegates.notNull()

    private fun setCalendarUI(year: String, month: String) {
        //iv_month.setImageResource(monthImgList[month.toInt() - 1])

        tv_month.setText(month.toString()+"월")
    }

    private fun makeDayList(): ArrayList<String> {

        //        this.inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val now = System.currentTimeMillis()

        val date = Date(now)

        //연,월,일을 따로 저장

        val curYearFormat = SimpleDateFormat("yyyy", Locale.KOREA)

        val curMonthFormat = SimpleDateFormat("MM", Locale.KOREA)

        val curDayFormat = SimpleDateFormat("dd", Locale.KOREA)

        year = curYearFormat.format(date).toInt()

        month = curMonthFormat.format(date).toInt()
        setCalendarUI(year.toString(), month.toString())

        //gridview 요일 표시

        dayList = ArrayList<String>()

        dayList.add("일")
        dayList.add("월")
        dayList.add("화")
        dayList.add("수")
        dayList.add("목")
        dayList.add("금")
        dayList.add("토")

        mCal = Calendar.getInstance()

        //이번달 1일 무슨요일인지 판단 mCal.set(Year,Month,Day)

        mCal.set(Integer.parseInt(curYearFormat.format(date)), Integer.parseInt(curMonthFormat.format(date)) - 1, 1)

        val dayNum = mCal.get(Calendar.DAY_OF_WEEK)

        //1일 - 요일 매칭 시키기 위해 공백 add

        for (i in 1 until dayNum) {
            dayList.add("")
        }

        setCalendarDate(dayList, mCal.get(Calendar.MONTH) + 1)


        return dayList

    }

    private fun setCalendarDate(dayList: ArrayList<String>, month: Int) {
        mCal.set(Calendar.MONTH, month - 1);
        for (i in 0 until mCal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            dayList.add("" + (i + 1))
        }
    }

    private fun updateCalendarDetail(list: ArrayList<Concert>) {
        if(list.isEmpty()){
            emptyResult()
            return
        }
        recycler_view_calendar_detail.visibility = View.VISIBLE
        dataListDetail.clear()
        dataListDetail.addAll(list)
        detailAdapter.notifyDataSetChanged()

    }

    private fun emptyResult(){
        recycler_view_calendar_detail.visibility = View.GONE
        tv_detail?.text="아직 아무 일정이 없습니다."
    }
    private fun clearDetailList(){

        calendarAdapter.selected=-1
        calendarAdapter.notifyDataSetChanged()

        dataListDetail.clear()
        detailAdapter.notifyDataSetChanged()
    }



    private var LOG_CALENDAR_TAB = "/api/calendar/tab"
    private fun connectRequestTabData() {


        activity?.progress_bar?.visibility=View.VISIBLE

        Log.d(Constants.LOG_NETWORK, "$LOG_CALENDAR_TAB GET, token : ${USER_TOKEN}")
        val getCalendarTabResponse: Call<GetCalendarTabResponse> = networkService.getCalendarTabList(USER_TOKEN)

        getCalendarTabResponse.enqueue(object : Callback<GetCalendarTabResponse> {
            override fun onFailure(call: Call<GetCalendarTabResponse>?, t: Throwable?) {
                Log.e(Constants.LOG_NETWORK, "$LOG_CALENDAR_TAB  $t")
            }

            override fun onResponse(call: Call<GetCalendarTabResponse>?, response: Response<GetCalendarTabResponse>?) {
                response?.let { res ->
                    if (res.body()?.status == Secret.NETWORK_SUCCESS) {
                        Log.d(Constants.LOG_NETWORK, "$LOG_CALENDAR_TAB :${response.body().toString()}")
                        res.body()!!.data?.let {
                            updateTabList(ArrayList(res.body()?.data))
                        }
                    } else {
                        Log.d(Constants.LOG_NETWORK, "$LOG_CALENDAR_TAB : fail ${response.body()?.message}")
//                        Log.v("test0102", "getGenreResponse in " + response.body()?.status.toString())
                    }
                }

            }
        })
    }


    fun updateTabList(list: ArrayList<TabData>) {

        activity?.progress_bar?.visibility=View.GONE

        var idx : Int = 0
        dataListTag.clear()
        //tabColorMap.clear()
        list.forEach {
            dataListTag.add(it.toCalendarTag())
//            tabColorMap[it.name] = tabColor[idx++%tabColor.size]
//            Log.d("updateTabList~~~", "index : $idx, name : ${it.name}, color : ${tabColor[idx%tabColor.size]}")
        }
        tabAdapter.notifyDataSetChanged()
        //calendarAdapter.notifyDataSetChanged()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


}

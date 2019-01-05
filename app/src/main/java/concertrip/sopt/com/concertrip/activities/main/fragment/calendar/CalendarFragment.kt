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
import concertrip.sopt.com.concertrip.list.adapter.CalendarTagListAdapter
import concertrip.sopt.com.concertrip.model.CalendarTab
import concertrip.sopt.com.concertrip.network.ApplicationController
import concertrip.sopt.com.concertrip.network.NetworkService
import concertrip.sopt.com.concertrip.network.response.GetCalendarResponse
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel
import concertrip.sopt.com.concertrip.network.response.GetCalendarTabResponse
import concertrip.sopt.com.concertrip.network.response.TabData
import concertrip.sopt.com.concertrip.utillity.Constants
import concertrip.sopt.com.concertrip.utillity.NetworkUtil
import concertrip.sopt.com.concertrip.utillity.Secret
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CalendarFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CalendarFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class CalendarFragment : Fragment(), OnItemClick, OnResponse {

    var year: Int  by Delegates.notNull()
    var month: Int by Delegates.notNull()

    var dataListDetail = arrayListOf<ListData>()
    var dataListTag = CalendarTab.instanceArray()

    private var scheduleMap: HashMap<Int, ArrayList<Schedule>> by Delegates.notNull()


    private lateinit var dayList: ArrayList<String>
    private val monthImgList = listOf<Int>(
        R.drawable.m_1, R.drawable.m_2, R.drawable.m_3,
        R.drawable.m_4, R.drawable.m_5, R.drawable.m_6,
        R.drawable.m_7, R.drawable.m_8, R.drawable.m_9,
        R.drawable.m_10, R.drawable.m_11, R.drawable.m_12
    )

    lateinit var calendarListAdapter: CalendarListAdapter
    // 날짜 > date객체(스트링으로 넘어옴)

    lateinit var calendarDetailAdapter: BasicListAdapter
    lateinit var tagAdapter: CalendarTagListAdapter

    /*TODO
    * have to make interface which contains schedule list
    * + adapter
    * + hash map key=day value=interface()*/

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }


    override fun onItemClick(root: RecyclerView.Adapter<out RecyclerView.ViewHolder>, position: Int) {
        /*TODO have to implement it*/
        // 태그 중 하나를 클릭하면 서버에서 그 태그에 알맞는 일정을 받아오기 위한 함수!
        // 여기서 사용하는 HorixzontalListAdapter에서 사용하며
        // holder.itemView.setOnClickListener에 달아뒀음!
        // 클릭된 아이템의 position 값이 parameter로 전달됨!

        if (root is CalendarTagListAdapter) {

            clearDetailList()
            tagAdapter.setSelect(position)
            NetworkUtil.getCalendarList(
                networkService,
                this,
                dataListTag[position].type,
                dataListTag[position]._id,
                year.toString(),
                month.toString(),
                null
            )
            updateCalendar(position)
        } else if (root is CalendarListAdapter) {
            if (calendarListAdapter.selected == -1) {

                clearDetailList()
                recycler_view_calendar_detail.visibility = View.GONE

            } else {
                recycler_view_calendar_detail.visibility = View.VISIBLE
                NetworkUtil.getCalendarList(
                    networkService,
                    this,
                    dataListTag[tagAdapter.selected].type,
                    dataListTag[tagAdapter.selected].name,
                    year.toString(),
                    month.toString(),
                    dayList[position]
                )
            }

        }
    }


    override fun onSuccess(obj: BaseModel, position: Int?) {
        if (obj is GetCalendarResponse) {
            when (position) {
                Constants.TYPE_MONTH -> {
                    val map = obj.toScheduleMap()
                    calendarListAdapter.scheduleMap.clear()
                    calendarListAdapter.scheduleMap.putAll(map)

                    clearDetailList()

                }
                Constants.TYPE_DAY -> {
                    updateCalendarDetail(obj.toConcertArray())
                }
            }
        }
    }

    override fun onFail(status: Int) {

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialUI()
        updateUI()

    }


    private fun initialUI() {
        btn_notification.setOnClickListener {
            startActivity(Intent(activity, AlarmActivity::class.java))
        }

        activity?.let {

            scheduleMap =  HashMap<Int, ArrayList<Schedule>>()
            calendarListAdapter = CalendarListAdapter(it.applicationContext, makeDayList(), scheduleMap, this)
            recycler_view_calendar.layoutManager = GridLayoutManager(it.applicationContext, 7)
            recycler_view_calendar.adapter = calendarListAdapter

            dataListDetail.addAll(Concert.getDummyArray())
//            dataListOrigin.addAll(Concert.getDummyArray())
            calendarDetailAdapter = BasicListAdapter(it.applicationContext, dataListDetail, this)
            recycler_view_calendar_detail.adapter = calendarDetailAdapter


            tagAdapter = CalendarTagListAdapter(it.applicationContext, dataListTag, this, false)
            recycler_view_filter.adapter = tagAdapter

//        dataListDetail = Concert.getDummyArray()
//        dataListOrigin.addAll(Concert.getDummyArray())
//        calendarDetailAdapter = BasicListAdapter(it.applicationContext, dataListDetail, this)
//        recycler_view_calendar_detail.adapter = calendarDetailAdapter
//
//        tagAdapter = HorizontalListAdapter(it.applicationContext, dataListTag, this, false)
//        recycler_view_filter.adapter = tagAdapter
//


            NetworkUtil.getCalendarList(
                networkService,
                this,
                Constants.REQUEST_ALL,
                "",
                year.toString(),
                month.toString(),
                null
            )

        }

        connectRequestTabData()


//        val textShader = LinearGradient(
//            0f, 0f, 0f, 20f,
//            intArrayOf(R.color.white, R.color.black),
//            floatArrayOf(1f, 1f), TileMode.CLAMP
//        )
//        tv_calendar.paint.shader = textShader

    }

    private fun updateUI() {
        if (dataListDetail.size == 0) {
            recycler_view_calendar_detail.visibility = View.GONE
            rl_select_date_view.visibility = View.VISIBLE
        } else {
            recycler_view_calendar_detail.visibility = View.VISIBLE
            rl_select_date_view.visibility = View.GONE
        }
    }

    private var mCal: Calendar by Delegates.notNull()

    private fun setCalendarUI(year: String, month: String) {
        iv_month.setImageResource(monthImgList[month.toInt() - 1])
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
            dayList.add("" + (i + 1));
        }
    }

    private fun updateCalendar(idx: Int) {

    }
    private fun updateCalendarDetail(list: ArrayList<Concert>) {
        dataListDetail.clear()
        list.forEach {
            dataListDetail.add(it)
        }
        calendarDetailAdapter.notifyDataSetChanged()
        updateUI()
    }

    private fun clearDetailList(){
        calendarListAdapter.selected=-1
        calendarListAdapter.notifyDataSetChanged()

        dataListDetail.clear()
        calendarDetailAdapter.notifyDataSetChanged()
        updateUI()
    }


    //Deprecated This function
//    private fun updateCalendarDetail(date: Int) {
//
//        val list = scheduleMap[date]
//
//        dataListDetail.clear()
//        list?.forEach {
//            dataListDetail.add(it.toConcert())
//        }
//        calendarDetailAdapter.notifyDataSetChanged()
//
//    }


//    private fun connectRequestCalendar(type: String, _id: String?) {
//
//        val LOG_CALENDAR_TYPE = "/api/calendar/type"
//
//
//        val id= if(type== Constants.REQUEST_ALL || type== Constants.REQUEST_MVP) null
//        else _id
//        Log.d(Constants.LOG_NETWORK, "$LOG_CALENDAR_TYPE, GET ? type=$type, id=$id, year = $year, month = $month")
//
//
//        val search: Call<GetCalendarTypeResponse> =
//            networkService.getCalendarType(2, type, id, year, month)
//        search.enqueue(object : Callback<GetCalendarTypeResponse> {
//
//            override fun onFailure(call: Call<GetCalendarTypeResponse>, t: Throwable) {
//                Log.e(Constants.LOG_NETWORK, t.toString())
//                onFail(Secret.NETWORK_UNKNOWN)
//            }
//
//            //통신 성공 시 수행되는 메소드
//            override fun onResponse(call: Call<GetCalendarTypeResponse>, response: Response<GetCalendarTypeResponse>) {
//                Log.d(Constants.LOG_NETWORK, response.errorBody()?.string() ?: response.message())
//
//                if (response.isSuccessful) {
//                    Log.d(Constants.LOG_NETWORK, "$LOG_CALENDAR_TYPE :${response.body()?.status}")
//                    response.body()?.let {
//                        if (it.status == Secret.NETWORK_SUCCESS) {
//                            Log.d(Constants.LOG_NETWORK, "$LOG_CALENDAR_TYPE :${response.body().toString()}")
//                            onSuccess(response.body() as BaseModel, null)
//                        } else {
//                            Log.d(Constants.LOG_NETWORK, "$LOG_CALENDAR_TYPE: fail  ${response.body()?.message}")
//                            onFail(response.body()?.status ?: Secret.NETWORK_UNKNOWN)
//                        }
//                    }
//
//                } else {
//                    onFail(Secret.NETWORK_UNKNOWN)
//
//                }
//            }
//        })
//
//
//    }


    private const val LOG_CALENDAR_TAB = "/api/calendar/tab"
    private fun connectRequestTabData() {
        Log.d(Constants.LOG_NETWORK, "GET")
        val getCalendarTabResponse: Call<GetCalendarTabResponse> = networkService.getCalendarTabList(1)

        getCalendarTabResponse.enqueue(object : Callback<GetCalendarTabResponse> {
            override fun onFailure(call: Call<GetCalendarTabResponse>?, t: Throwable?) {
                Log.e(Constants.LOG_NETWORK, " $t")
            }

            override fun onResponse(call: Call<GetCalendarTabResponse>?, response: Response<GetCalendarTabResponse>?) {
                response?.let { res ->
                    if (res.body()?.status == Secret.NETWORK_SUCCESS) {
                        Log.d(Constants.LOG_NETWORK, ":${response.body().toString()}")
                        res.body()!!.data?.let {
                            updateTagList(ArrayList(res.body()?.data))
                        }
                    } else {
                        Log.d(Constants.LOG_NETWORK, ": fail ${response.body()?.message}")
//                        Log.v("test0102", "getGenreResponse in " + response.body()?.status.toString())
                    }
                }

            }
        })
    }


    fun updateTagList(list: ArrayList<TabData>) {
        dataListTag.clear()
//        dataListTag.addAll(CalendarTag.instanceArray())
        list.forEach {
            dataListTag.add(it.toCalendarTag())
        }

        tagAdapter.notifyDataSetChanged()
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


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CalendarFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CalendarFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

package concertrip.sopt.com.concertrip.activities.main.fragment.calendar

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import concertrip.sopt.com.concertrip.list.adapter.HorizontalListAdapter
import concertrip.sopt.com.concertrip.model.Artist
import concertrip.sopt.com.concertrip.model.Concert
import concertrip.sopt.com.concertrip.model.Schedule
import kotlin.properties.Delegates
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.interfaces.ListData


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
class CalendarFragment : Fragment(), OnItemClick {

    var dataListArtist = arrayListOf<Artist>()
    var dataListConcert = arrayListOf<Concert>()
    var dataListOrigin = arrayListOf<ListData>()
    var dataListTag = arrayListOf<String>("모두","테마","걸그룹","보이그룹","힙합","발라드")

    private var scheduleMap : HashMap<Int,ArrayList<Schedule>> by Delegates.notNull()


    private lateinit var dayList : ArrayList<String>
    private val monthImgList = listOf<Int>(
        R.drawable.ic_account_circle, R.drawable.ic_account_circle, R.drawable.ic_account_circle,
        R.drawable.ic_account_circle, R.drawable.ic_account_circle, R.drawable.ic_account_circle,
        R.drawable.ic_account_circle, R.drawable.ic_account_circle, R.drawable.ic_account_circle,
        R.drawable.ic_account_circle, R.drawable.ic_account_circle, R.drawable.ic_account_circle
    )

    lateinit var calendarListAdapter: CalendarListAdapter
    // 날짜 > date객체(스트링으로 넘어옴)

    lateinit var calendarDetailAdapter: BasicListAdapter
    lateinit var tagAdapter: HorizontalListAdapter

    /*TODO
    * have to make interface which contains schedule list
    * + adapter
    * + hash map key=day value=interface()*/

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null


    override fun onItemClick(root : RecyclerView.Adapter<out RecyclerView.ViewHolder>, position: Int) {
        /*TODO have to implement it*/
        // 태그 중 하나를 클릭하면 서버에서 그 태그에 알맞는 일정을 받아오기 위한 함수!
        // 여기서 사용하는 HorixzontalListAdapter에서 사용하며
        // holder.itemView.setOnClickListener에 달아뒀음!
        // 클릭된 아이템의 position 값이 parameter로 전달됨!

        if(root is HorizontalListAdapter) {
            tagAdapter.setSelect(position)
            updateCalendar()
        }

        else if(root is CalendarListAdapter){
            updateCalendarDetail(dayList[position].toInt())
        }
    }

    fun artistToCal(artist: Artist) {
        /*TODO have to implement it*/
    }

    fun concertToCal(concert: Concert) {
        /*TODO have to implement it*/
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

    private fun updateUI(){
        if(dataListConcert.size == 0)
        {recycler_view_calendar_detail.visibility = View.GONE
            rl_select_date_view.visibility = View.VISIBLE}
        else
        { recycler_view_calendar_detail.visibility = View.VISIBLE
            rl_select_date_view.visibility = View.GONE}
    }

    private var mCal: Calendar by Delegates.notNull()

    private fun setCalendarUI(year : String, month : String){
        iv_month.setImageResource(monthImgList[month.toInt()-1])
    }

    private fun makeDayList()  : ArrayList<String>{

        //        this.inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val now = System.currentTimeMillis()

        val date = Date(now)

        //연,월,일을 따로 저장

        val curYearFormat = SimpleDateFormat("yyyy", Locale.KOREA)

        val curMonthFormat = SimpleDateFormat("MM", Locale.KOREA)

        val curDayFormat = SimpleDateFormat("dd", Locale.KOREA)


        setCalendarUI(curYearFormat.format(date),curMonthFormat.format(date))

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

        setCalendarDate(dayList,mCal.get(Calendar.MONTH) + 1)


        return dayList

    }

    private fun setCalendarDate(dayList: ArrayList<String>,month : Int) {
        mCal.set(Calendar.MONTH, month - 1);
        for ( i  in 0 until mCal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            dayList.add("" + (i + 1));
        }
    }

    private fun updateCalendar(){

    }

    private fun updateCalendarDetail(date : Int){

        val list = scheduleMap[date]

        dataListConcert.clear()
        list?.forEach {
            dataListConcert.add(it.toConcert())
        }
        calendarDetailAdapter.notifyDataSetChanged()

    }



    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

//    fun changeFragment() {
//        listener?.changeFragment(Constants.FRAGMENT_NOTIFICATION)
//    }



    private fun initialUI() {
        btn_notification.setOnClickListener {
            startActivity(Intent(activity, AlarmActivity::class.java))
        }

        activity?.let {

            scheduleMap= Schedule.getDummyMap()
            calendarListAdapter = CalendarListAdapter(it.applicationContext,makeDayList(),scheduleMap,this)
            recycler_view_calendar.layoutManager=GridLayoutManager(it.applicationContext,7)
            recycler_view_calendar.adapter=calendarListAdapter

            dataListConcert = Concert.getDummyArray()
            dataListOrigin.addAll(Concert.getDummyArray())
            calendarDetailAdapter = BasicListAdapter(it.applicationContext,dataListConcert,this)
            recycler_view_calendar_detail.adapter = calendarDetailAdapter


            tagAdapter = HorizontalListAdapter(it.applicationContext,dataListTag,this)
            recycler_view_filter.adapter=tagAdapter


        }

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

package concertrip.sopt.com.concertrip.activities.main.adapter

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentManager
import android.util.Log
import android.view.View
import android.widget.ImageView
import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.activities.main.fragment.calendar.CalendarFragment
import concertrip.sopt.com.concertrip.activities.main.fragment.mypage.MyPageFragment
import concertrip.sopt.com.concertrip.activities.main.fragment.mypage.ticket.TicketListFragment
import concertrip.sopt.com.concertrip.activities.main.fragment.search.SearchFragment
import concertrip.sopt.com.concertrip.activities.main.fragment.liked.LikedFragment
import concertrip.sopt.com.concertrip.activities.main.fragment.mypage.SettingFragment
import concertrip.sopt.com.concertrip.activities.main.fragment.mypage.ticket.TicketDetailFragment
import concertrip.sopt.com.concertrip.activities.main.fragment.search.ExplorerFragment
import concertrip.sopt.com.concertrip.utillity.Constants
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.FRAGMENT_CALENDAR
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.TAB_CALENDAR
import kotlin.properties.Delegates

class MainFragmentAdapter(val fragmentManager: FragmentManager, val mainTab: TabLayout) {
    private val LOG_TAG = this::class.java.simpleName

    var curFragmentId : Int by Delegates.notNull()
    var curTabId : Int by Delegates.notNull()

    private val setIcons = arrayOf(
            R.drawable.ic_calendar_selected, R.drawable.ic_explore_selected,
            R.drawable.ic_liked_selected, R.drawable.ic_mypage_selected
    )
    private val unsetIcons = arrayOf(
        R.drawable.ic_calendar, R.drawable.ic_explore,
        R.drawable.ic_liked, R.drawable.ic_mypage
    )

    private val fragments = arrayOf(
        CalendarFragment(), ExplorerFragment(), LikedFragment(), MyPageFragment(),TicketListFragment(),
        SearchFragment(),TicketDetailFragment(),SettingFragment()
    )


    init {
        curFragmentId = FRAGMENT_CALENDAR
        curTabId= TAB_CALENDAR

        val fragment = fragments[curFragmentId]
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_down)
        fragmentTransaction.add(R.id.container ,fragment)
        fragmentTransaction.commit()
        mainTab.getTabAt(curTabId)?.customView?.findViewById<ImageView>(R.id.iv_tab)?.setImageResource(setIcons[curTabId])
//        mainTab.getTabAt(curTabId)?.setIcon(setIcons[curTabId])


        mainTab.getTabAt(curTabId)?.customView?.findViewById<ImageView>(R.id.iv_tab)?.setImageResource(setIcons[curTabId])
//        mainTab.getTabAt(curTabId)?.setIcon(setIcons[curTabId])

    }


    //선택된 Tab의 색상과 아이콘을 바꾸어줌
    fun setTab(what : Int){
        if(what==-1) return
        //TODO 여기서 더 손못대겠음!
       // mainTab.getTabAt(curTabId)?.setCustomView(unsetIcons[curTabId])
        mainTab.getTabAt(curTabId)?.customView?.findViewById<ImageView>(R.id.iv_tab)?.setImageResource(unsetIcons[curTabId])
//        mainTab.getTabAt(curTabId)?.setIcon(unsetIcons[curTabId])
        curTabId = what
        mainTab.getTabAt(curTabId)?.customView?.findViewById<ImageView>(R.id.iv_tab)?.setImageResource(setIcons[curTabId])
//        mainTab.getTabAt(curTabId)?.setIcon(setIcons[curTabId])
        mainTab.getTabAt(curTabId)?.select()

    }

    //프레그먼트 바꾸기
    fun setFragment(what : Int) = setFragment(what,null)
    fun setFragment(what : Int,bundle: Bundle?){

        val curTab = when(what){
            Constants.FRAGMENT_CALENDAR->
                Constants.TAB_CALENDAR
            Constants.FRAGMENT_SEARCH, Constants.FRAGMENT_EXPLORER->
                Constants.TAB_SEARCH
            Constants.FRAGMENT_LIKED->
                Constants.TAB_LIKED
            Constants.FRAGMENT_MY_PAGE,Constants.FRAGMENT_TICKET,Constants.FRAGMENT_TICKET_LIST->
                Constants.TAB_MY_PAGE
            else->
                -1
        }

        setTab(curTab)

        curFragmentId=what
//        Log.d("$LOG_TAG : curFragmentId", curFragmentId.toString())

        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = fragments[curFragmentId]


        //bundle이 있을경우에는 fragment를 새로 만들어 준다,
//        bundle?.let {
//            when(what){
//                Constants.FRAGMENT_SEARCH->{
//                    fragment= SearchFragment.newInstance("아직","구현안함")
//                    fragments[curFragmentId]=fragment
//                }
//
//                Constants.FRAGMENT_EXPLORER->{
//                    fragment= ExplorerFragment.newInstance("아직","구현안함")
//                    fragments[curFragmentId]=fragment
//                }
//
//                Constants.FRAGMENT_TICKET->{
//                    fragment= TicketFragment.newInstance("아직","구현안함")
//                    fragments[curFragmentId]=fragment
//                }
//
//                Constants.FRAGMENT_TICKET_LIST->{
//                    fragment= TicketListFragment.newInstance("아직","구현안함")
//                    fragments[curFragmentId]=fragment
//                }
//
//            }
//        }

        //fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_down)
        fragmentTransaction.replace(R.id.container ,fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.setBreadCrumbShortTitle(curFragmentId)
        fragmentTransaction.commit()
    }


}
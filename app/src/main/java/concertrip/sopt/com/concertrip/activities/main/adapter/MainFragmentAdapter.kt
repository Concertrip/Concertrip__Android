package concertrip.sopt.com.concertrip.activities.main.adapter

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentManager
import android.util.Log
import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.activities.main.fragment.calendar.NotificationFragment
import concertrip.sopt.com.concertrip.activities.main.fragment.calendar.CalendarFragment
import concertrip.sopt.com.concertrip.activities.main.fragment.mypage.MyPageFragment
import concertrip.sopt.com.concertrip.activities.main.fragment.mypage.ticket.TicketFragment
import concertrip.sopt.com.concertrip.activities.main.fragment.mypage.ticket.TicketListFragment
import concertrip.sopt.com.concertrip.activities.main.fragment.search.SearchFragment
import concertrip.sopt.com.concertrip.activities.main.fragment.liked.LikedFragment
import concertrip.sopt.com.concertrip.activities.main.fragment.mypage.SettingFragment
import concertrip.sopt.com.concertrip.activities.main.fragment.search.ExplorerFragment
import concertrip.sopt.com.concertrip.utillity.Constants
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.FRAGMENT_CALENDAR
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.TAB_CALENDAR
import kotlin.properties.Delegates

class MainFragmentAdapter(val fragmentManager: FragmentManager, val mainTab: TabLayout) {
    private val LOG_TAG = this::class.java.simpleName
//    var fragment : Fragment by Delegates.notNull()
    //var fragmentTransaction : FragmentTransaction by Delegates.notNull()

    var curFragmentId : Int by Delegates.notNull()
    var curTabId : Int by Delegates.notNull()

    /*TODO have to edit its icons when we get the icons from design team*/

    private val setIcons = arrayOf(
            R.drawable.ic_tab, R.drawable.ic_tab,
            R.drawable.ic_tab, R.drawable.ic_tab
    )
    private val unsetIcons = arrayOf(
        R.drawable.ic_account_circle, R.drawable.ic_account_circle,
        R.drawable.ic_account_circle, R.drawable.ic_account_circle
    )

    private val fragments = arrayOf(
        CalendarFragment(), ExplorerFragment(), LikedFragment(), MyPageFragment(),TicketFragment(),
        NotificationFragment(),SearchFragment(),TicketFragment(),SettingFragment()
    //TicketListFragment는 아직 안만듬. UI확정나면 작업할 예정 임시로 TIcktFragment로 함
    //SearchFragmnt가 없어지고 SearchResultFragment로 임시 대처함
    )


    init {

        //fragment 초기화 해주기
        curFragmentId = FRAGMENT_CALENDAR
        curTabId= TAB_CALENDAR

        val fragment = fragments[curFragmentId]
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.container ,fragment)
        fragmentTransaction.commit()

        mainTab.getTabAt(curTabId)!!.setIcon(setIcons[curTabId])
    }

    fun setTab(what : Int){
        if(what==-1) return

        mainTab.getTabAt(curTabId)?.setIcon(unsetIcons[curTabId])
        curTabId = what
        mainTab.getTabAt(curTabId)?.setIcon(setIcons[curTabId])
        mainTab.getTabAt(curTabId)?.select()



    }

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
        Log.d("$LOG_TAG : curFragmentId", curFragmentId.toString())

        val fragmentTransaction = fragmentManager.beginTransaction()
        var fragment = fragments[curFragmentId]

        bundle?.let {

            //bundle이 있을경우에는 fragment를 새로 만들어 준다,
            when(what){
                Constants.FRAGMENT_SEARCH->{
                    fragment= SearchFragment.newInstance("아직","구현안함")
                    fragments[curFragmentId]=fragment
                }

                Constants.FRAGMENT_EXPLORER->{
                    fragment= ExplorerFragment.newInstance("아직","구현안함")
                    fragments[curFragmentId]=fragment
                }

                Constants.FRAGMENT_TICKET->{
                    fragment= TicketFragment.newInstance("아직","구현안함")
                    fragments[curFragmentId]=fragment
                }

                Constants.FRAGMENT_TICKET_LIST->{
                    fragment= TicketListFragment.newInstance("아직","구현안함")
                    fragments[curFragmentId]=fragment
                }

            }
        }
        //if(fragment.isAdded) return
        fragmentTransaction.replace(R.id.container ,fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.setBreadCrumbShortTitle(curFragmentId);

        fragmentTransaction.commit()
    }


}
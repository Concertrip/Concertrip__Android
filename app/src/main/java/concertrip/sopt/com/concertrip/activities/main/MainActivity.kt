package concertrip.sopt.com.concertrip.activities.main

import android.os.Bundle
import android.os.Handler
import android.support.design.widget.TabLayout
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import concertrip.sopt.com.concertrip.interfaces.OnFragmentInteractionListener
import concertrip.sopt.com.concertrip.utillity.Constants

import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates
import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.activities.main.adapter.MainFragmentAdapter
import concertrip.sopt.com.concertrip.dialog.ColorToast
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.FRAGMENT_CALENDAR
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.FRAGMENT_EXPLORER
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.FRAGMENT_LIKED
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.FRAGMENT_MY_PAGE
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.TAB_CALENDAR
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.TAB_LIKED
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.TAB_MY_PAGE
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.TAB_SEARCH


class MainActivity : AppCompatActivity() , OnFragmentInteractionListener {
    override fun changeFragment(what: Int) {
        fragmentAdapter.setFragment(what)
    }


    var fragmentAdapter : MainFragmentAdapter by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//      setSupportActionBar(toolbar)

        main_tab.addTab(main_tab.newTab().setCustomView(R.layout.tab_calendar))
        main_tab.addTab(main_tab.newTab().setCustomView(R.layout.tab_explorer))
        main_tab.addTab(main_tab.newTab().setCustomView(R.layout.tab_liked))
        main_tab.addTab(main_tab.newTab().setCustomView(R.layout.tab_my_page))

        main_tab.getTabAt(0)?.select()

        fragmentAdapter = MainFragmentAdapter(supportFragmentManager, main_tab)
        main_tab.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position) {
                    Constants.TAB_CALENDAR->{
                        fragmentAdapter.setFragment(FRAGMENT_CALENDAR)
                    }
                    Constants.TAB_SEARCH->{
                        fragmentAdapter.setFragment(FRAGMENT_EXPLORER)
                    }
                    Constants.TAB_LIKED->{
                        fragmentAdapter.setFragment(FRAGMENT_LIKED)
                    }
                    Constants.TAB_MY_PAGE->{
                        fragmentAdapter.setFragment(FRAGMENT_MY_PAGE)
                    }

                }
            }
        })

//        fragmentAdapter.fragmentManager.addOnBackStackChangedListener {
//
//            val i : Int = supportFragmentManager.backStackEntryCount;
//
//
//            if(i==0)  fragmentAdapter.setTab(TAB_CALENDAR)
//            else if (i >0) {
//                val tt: FragmentManager.BackStackEntry = supportFragmentManager.getBackStackEntryAt(i -1)
//
//                when (tt.breadCrumbShortTitleRes) {
//                    Constants.FRAGMENT_CALENDAR-> {
//                        fragmentAdapter.setTab(TAB_CALENDAR)
//                    }
//                    Constants.FRAGMENT_EXPLORER, Constants.FRAGMENT_SEARCH -> {
//                        fragmentAdapter.setTab(TAB_SEARCH)
//                    }
//                    Constants.TAB_LIKED -> {
//                        fragmentAdapter.setTab(TAB_LIKED)
//                    }
//                    else -> {
//                        fragmentAdapter.setTab(TAB_MY_PAGE)
//                    }
//
//                }
//            }
//        }


    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        Log.d(" : curFragment Count", fragmentAdapter.fragmentManager.backStackEntryCount.toString())
        if(fragmentAdapter.fragmentManager.backStackEntryCount>0)
            super.onBackPressed()
        else {
            if (doubleBackToExitPressedOnce) {
                ActivityCompat.finishAffinity(this)
                return
            }

            this.doubleBackToExitPressedOnce = true
            ColorToast(this,getString(R.string.message_double_back_exit))

            Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
        }
    }
}


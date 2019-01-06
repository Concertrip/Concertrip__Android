package concertrip.sopt.com.concertrip.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.activities.main.MainActivity
import concertrip.sopt.com.concertrip.interfaces.ListData
import concertrip.sopt.com.concertrip.list.adapter.BasicListAdapter
import concertrip.sopt.com.concertrip.model.Alarm
import kotlinx.android.synthetic.main.activity_alram.*
import kotlinx.android.synthetic.main.fragment_calendar.*

class AlarmActivity : AppCompatActivity(){

    var dataList  = arrayListOf<ListData>()
    lateinit  var adapter : BasicListAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_alram)


        initialUI()
        connectRequestData()
    }

    override fun onResume() {
        super.onResume()

        connectRequestData()
    }

    private fun initialUI(){
        btn_alarm_back.setOnClickListener {
            finish()
        }

        if(dataList.size == 0){
            tv_alarm.visibility = View.VISIBLE
            recycler_view_alarm.visibility = View.GONE
        }else{
            adapter = BasicListAdapter(this, dataList)
            recycler_view_alarm.adapter=adapter
        }
    }

    private fun updateList(list : ArrayList<out ListData>) {
        dataList.clear()
        dataList.addAll(list)
        adapter.notifyDataSetChanged()
    }

    private fun connectRequestData() {
    }
}
package concertrip.sopt.com.concertrip.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.interfaces.ListData
import concertrip.sopt.com.concertrip.interfaces.OnResponse
import concertrip.sopt.com.concertrip.list.adapter.BasicListAdapter
import concertrip.sopt.com.concertrip.model.Alarm
import concertrip.sopt.com.concertrip.network.ApplicationController
import concertrip.sopt.com.concertrip.network.NetworkService
import concertrip.sopt.com.concertrip.network.response.data.AlarmData
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel
import concertrip.sopt.com.concertrip.utillity.Constants
import concertrip.sopt.com.concertrip.utillity.NetworkUtil
import concertrip.sopt.com.concertrip.utillity.Secret.Companion.USER_TOKEN
import kotlinx.android.synthetic.main.activity_alram.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlarmActivity : AppCompatActivity(){

    var dataList  = arrayListOf<ListData>()
    lateinit  var adapter : BasicListAdapter

    private val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_alram)

        initialUI()
        getAlarmList()
        //NetworkUtil.getAlarmList(networkService, this)
    }

    override fun onResume() {
        super.onResume()
        //connectRequestData()
    }

    private fun initialUI(){
        btn_alarm_back.setOnClickListener {
            finish()
        }

        adapter = BasicListAdapter(this, dataList, Constants.TYPE_ALARM)
        recycler_view_alarm.adapter=adapter

    }

    private val LOG_ALARM = "/api/fcm/list"

    private fun getAlarmList() {
        val getAlarmListResponse: Call<List<AlarmData>> = networkService.getAlarmList(USER_TOKEN) // _id

        getAlarmListResponse.enqueue(object : Callback<List<AlarmData>> {

            override fun onFailure(call: Call<List<AlarmData>>, t: Throwable) {
                Log.d(Constants.LOG_NETWORK, t.toString())
            }

            override fun onResponse(call: Call<List<AlarmData>>, response: Response<List<AlarmData>>) {
                response.body()?.let {
                    updateList(ArrayList(toAlarmList(it)))
                    Log.d(Constants.LOG_NETWORK, "${LOG_ALARM} :${response.body().toString()}")
                }
            }

        })
    }

    fun toAlarmList(data : List<AlarmData>):List<Alarm> {
        var list = ArrayList<Alarm>()

        data.forEach {
            list.add(it.toAlarm())
        }
        return list
    }

    private fun updateList(list : ArrayList<out ListData>) {
        dataList.clear()
        dataList.addAll(list)
        adapter.notifyDataSetChanged()

        if(dataList.size == 0){
            tv_alarm.visibility = View.VISIBLE
            recycler_view_alarm.visibility = View.GONE
        }
        else{
            recycler_view_alarm.visibility = View.VISIBLE
            tv_alarm.visibility = View.GONE
        }
    }

//    private fun connectRequestData() {
//        getAlarmList()
//    }
}
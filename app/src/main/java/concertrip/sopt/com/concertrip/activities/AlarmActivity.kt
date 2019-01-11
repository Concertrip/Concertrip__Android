package concertrip.sopt.com.concertrip.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.interfaces.ListData
import concertrip.sopt.com.concertrip.list.adapter.BasicListAdapter
import concertrip.sopt.com.concertrip.network.ApplicationController
import concertrip.sopt.com.concertrip.network.NetworkService
import concertrip.sopt.com.concertrip.network.response.GetAlarmListResponse
import concertrip.sopt.com.concertrip.utillity.Constants
import concertrip.sopt.com.concertrip.utillity.Secret
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

        Log.d("!!!!", "onCreate")

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

        adapter = BasicListAdapter(this, dataList, Constants.TYPE_ALARM)
        recycler_view_alarm.adapter=adapter

        Log.d("!!!!", "initialUI")

        //postAlarmList()

//        if(dataList.size == 0){
//            tv_alarm.visibility = View.VISIBLE
//            recycler_view_alarm.visibility = View.GONE
//        }else{
//            adapter = BasicListAdapter(this, dataList, Constants.TYPE_ALARM)
//            recycler_view_alarm.adapter=adapter
//        }
    }

    private val LOG_ALARM = "/api/fcm/list"

    fun postAlarmList() {
        Log.d("!!!!", "postAlarmList")

        val getAlarmListResponse: Call<GetAlarmListResponse> = networkService.postAlarmList(USER_TOKEN) // _id

        getAlarmListResponse.enqueue(object : Callback<GetAlarmListResponse> {

            override fun onFailure(call: Call<GetAlarmListResponse>, t: Throwable) {
                Log.e(Constants.LOG_NETWORK, t.toString())
            }

            override fun onResponse(call: Call<GetAlarmListResponse>, response: Response<GetAlarmListResponse>) {
                response.body()?.let {
                    if (it.status == Secret.NETWORK_SUCCESS) {
                        updateList(ArrayList(it.toAlarmList()))
                        Log.d(Constants.LOG_NETWORK, "${LOG_ALARM} :${response.body().toString()}")
                    } else {
                        Log.d(Constants.LOG_NETWORK, "${LOG_ALARM}: fail ${response.body()?.message}")
                    }
                }
            }

        })
    }

    private fun updateList(list : ArrayList<out ListData>) {
        dataList.clear()
        dataList.addAll(list)
        adapter.notifyDataSetChanged()

        if(dataList.size == 0){
            tv_alarm.visibility = View.VISIBLE
            recycler_view_alarm.visibility = View.GONE
        }
    }

    private fun connectRequestData() {
        Log.d("!!!!", "connectRequestData")
        postAlarmList()
    }
}
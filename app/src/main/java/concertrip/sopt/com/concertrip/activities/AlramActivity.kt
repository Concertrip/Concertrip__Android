package concertrip.sopt.com.concertrip.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.interfaces.ListData
import concertrip.sopt.com.concertrip.list.adapter.BasicListAdapter
import concertrip.sopt.com.concertrip.model.Alarm
import kotlinx.android.synthetic.main.fragment_notification.*

class AlramActivity : AppCompatActivity(){

    var dataList  = arrayListOf<ListData>()
    lateinit  var adapter : BasicListAdapter


    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_alram)

//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }

        initialUI()
        connectRequestData()
    }

    override fun onResume() {
        super.onResume()

        connectRequestData()
    }

    private fun initialUI(){
        adapter = BasicListAdapter(this, dataList)
        recycler_view.adapter=adapter
    }

    fun updateList(list : ArrayList<out ListData>) {
        dataList.clear()
        dataList.addAll(list)
        adapter.notifyDataSetChanged()
    }

    private fun connectRequestData() {
        updateList(Alarm.getDummyArray())
    }
}
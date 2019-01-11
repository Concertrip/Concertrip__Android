package concertrip.sopt.com.concertrip.network.response

import concertrip.sopt.com.concertrip.network.response.data.AlarmData
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel

data class GetAlarmResponse(
    var data : ArrayList<AlarmData?>?
): BaseModel(){
    override fun toString(): String =data?.toString()?:"null"
}


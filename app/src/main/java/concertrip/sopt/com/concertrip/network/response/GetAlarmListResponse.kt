package concertrip.sopt.com.concertrip.network.response

import concertrip.sopt.com.concertrip.model.Alarm
import concertrip.sopt.com.concertrip.network.response.data.AlarmData
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel

data class GetAlarmListResponse(
    var data : List<AlarmData?>
){
    fun toAlarmList():List<Alarm> {
        val list = ArrayList<Alarm>()

        data.forEach {
            if(it!=null)
                list.add(it.toAlarm())
        }
        return list
    }
}
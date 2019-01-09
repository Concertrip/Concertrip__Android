package concertrip.sopt.com.concertrip.network.response

import concertrip.sopt.com.concertrip.model.Alarm
import concertrip.sopt.com.concertrip.network.response.data.AlarmData
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel

data class GetAlarmListResponse(
    var data : List<AlarmData>
):BaseModel(){
    fun toAlarmList():List<Alarm> {
        var list = ArrayList<Alarm>()

        data.forEach {
            list.add(it.toAlarm())
        }
        return list
    }
}
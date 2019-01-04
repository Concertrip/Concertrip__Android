package concertrip.sopt.com.concertrip.network.response

import com.google.android.youtube.player.internal.d
import concertrip.sopt.com.concertrip.model.Schedule
import concertrip.sopt.com.concertrip.network.response.data.CalendarData
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel


data class GetCalendarTypeResponse(
    var data : ArrayList<CalendarData>?
): BaseModel(){
    fun toScheduleMap() : HashMap<Int, ArrayList<Schedule>>{
        val map = HashMap<Int, ArrayList<Schedule>>()
        data?.forEach {
            it.date?.forEach { dateStr->
                val d = dateStr.substring(8,10).toInt()
                val s = Schedule(d,"")
                if(map.containsKey(d) && map[d] != null){
                    map[d]!!.add(s)
                }else{
                    map[d]= ArrayList<Schedule>()
                    map[d]!!.add(s)
                }
            }
        }
        return map

    }
    override fun toString(): String {
        var result = ""
        data?.forEach {
            result = result.plus(it)+","
        }
        return result
    }
}


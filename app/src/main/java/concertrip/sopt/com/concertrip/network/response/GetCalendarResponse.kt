package concertrip.sopt.com.concertrip.network.response

import com.google.gson.annotations.SerializedName
import concertrip.sopt.com.concertrip.model.Concert
import concertrip.sopt.com.concertrip.model.Schedule
import concertrip.sopt.com.concertrip.network.response.data.CalendarDate
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel

data class GetCalendarResponse (
    @SerializedName("data")
    var data : List<CalendarDate>?
):BaseModel(){
    fun toScheduleMap() : HashMap<Int, ArrayList<Schedule>>{
        val map = HashMap<Int, ArrayList<Schedule>>()
        data?.forEach {
            it.date.forEach { dateStr->
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
    fun toConcertArray() :  ArrayList<Concert>{
        val list = ArrayList<Concert>()
        data?.forEach {
            list.add(it.toConcert())
        }
        return list

    }
    override fun toString(): String {
        var result = "{"
        data?.forEach {
            result = result.plus(it)+","
        }
        return "$result}"
    }
}
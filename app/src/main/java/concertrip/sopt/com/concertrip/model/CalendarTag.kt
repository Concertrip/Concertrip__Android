package concertrip.sopt.com.concertrip.model

import concertrip.sopt.com.concertrip.interfaces.ListData
import concertrip.sopt.com.concertrip.utillity.Constants

data class CalendarTag(var _id : String, var type : String, var name: String){
    companion object {
        fun instanceArray(): ArrayList<CalendarTag>{
            val list = ArrayList<CalendarTag>()
            list.add(CalendarTag("", Constants.REQUEST_ALL,"모두"))
            list.add(CalendarTag("", Constants.REQUEST_MVP,"내공연"))
            return list
        }
    }
}
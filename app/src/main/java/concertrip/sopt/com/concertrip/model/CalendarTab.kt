package concertrip.sopt.com.concertrip.model

import concertrip.sopt.com.concertrip.utillity.Constants

data class CalendarTab(var _id : String, var type : String, var name: String){
    companion object {
        fun instanceArray(): ArrayList<CalendarTab>{
            val list = ArrayList<CalendarTab>()
            list.add(CalendarTab("", Constants.REQUEST_ALL,"모두"))
            list.add(CalendarTab("", Constants.REQUEST_MVP,"내공연"))
            return list
        }
    }
}
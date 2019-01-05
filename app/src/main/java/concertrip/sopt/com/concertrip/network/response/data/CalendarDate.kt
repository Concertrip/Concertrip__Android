package concertrip.sopt.com.concertrip.network.response.data

import concertrip.sopt.com.concertrip.model.Concert
import concertrip.sopt.com.concertrip.model.Schedule

data class CalendarDate(
    var _id : String,
    var name : String,
    var profileImg : String,
    var date : List<String>,
    var tag : String?,
    var subscribe : Boolean
){
    fun toConcert() : Concert=Concert(_id = _id, name = name, profileImg = profileImg, date = date, tag = tag?:"", subscribe = subscribe)
}
package concertrip.sopt.com.concertrip.network.response.data

import concertrip.sopt.com.concertrip.model.Concert

data class SimpleConcertData(
    var _id : String,
    var title : String,
    var location : String,
    var date : String,
    var tag : String
){
    fun toConcert() : Concert {
        val c =  Concert(_id = _id)
        c.title=title
        c.location = location
        c.date= arrayListOf(date)
        c.tag=tag
        return c
    }
}
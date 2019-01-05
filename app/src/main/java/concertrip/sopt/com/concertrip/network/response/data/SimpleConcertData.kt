package concertrip.sopt.com.concertrip.network.response.data

import concertrip.sopt.com.concertrip.model.Concert
import concertrip.sopt.com.concertrip.model.Genre

data class SimpleConcertData(
    var _id : String,
    var name : String,
    var profileImg : String,
//    var date : String, // list아닌가?
//    var location : String
    var subscribe: Boolean,
    var tag : String,
    var group : Boolean
){
    fun toConcert() : Concert {
        val c =  Concert(_id = _id)
        c.title=name
//        c.location = location
        c.location = ""
        c.profileImg=profileImg
        c.date= arrayListOf()
        c.subscribe= subscribe
        return c
    }


    override fun toString(): String ="SimpleConcertData{_id : $_id, name = $name, profileImg = $profileImg, subscribe = $subscribe}"

}
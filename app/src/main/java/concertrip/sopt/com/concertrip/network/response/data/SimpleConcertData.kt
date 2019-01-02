package concertrip.sopt.com.concertrip.network.response.data

import concertrip.sopt.com.concertrip.model.Concert

data class SimpleConcertData(
    var _id : String,
    var name : String,
    var profileImg : String,
//    var date : String, // list아닌가?
//    var location : String
    var subscribe: Boolean
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

    companion object {
        fun getDummy() : SimpleConcertData = SimpleConcertData("", "힙합 페스티벌", "종합경기장",
            false)//"2018-12-20", "힙합")

        fun getDummyList() : List<SimpleConcertData> {
            val list = listOf(SimpleConcertData("", "힙합 페스티벌", "종합경기장", false),//"2018-12-20", "힙합"),
                SimpleConcertData("", "SM타운 콘서트", "잠실주경기장", false),//"2018-12-17", "보이그룹"),
                SimpleConcertData("", "휘성 콘서트", "체조 경기장", false) )//"2018-12-29","발라드"))
            return list
        }
    }
}
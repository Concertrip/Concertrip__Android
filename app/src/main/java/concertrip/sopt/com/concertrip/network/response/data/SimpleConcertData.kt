package concertrip.sopt.com.concertrip.network.response.data

import concertrip.sopt.com.concertrip.model.Concert

data class SimpleConcertData(
    var _id : String,
    var name : String,
    var profileImg : String,
    //var date : String, // list아닌가?
    var location : String
){
    fun toConcert() : Concert {
        val c =  Concert(_id = _id)
        c.title=name
        c.location = location
        //c.date= arrayListOf(date)
        return c
    }

    companion object {
        fun getDummy() : SimpleConcertData = SimpleConcertData("", "힙합 페스티벌","", "종합경기장")

        fun getDummyList() : List<SimpleConcertData> {
            val list = listOf(SimpleConcertData("", "힙합 페스티벌", "","종합경기장"),
                SimpleConcertData("", "SM타운 콘서트", "","종합경기장"),
                SimpleConcertData("", "휘성 콘서트", "","종합경기장"))
            return list
        }
    }
}
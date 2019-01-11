package concertrip.sopt.com.concertrip.network.response.data

import concertrip.sopt.com.concertrip.model.Concert
import concertrip.sopt.com.concertrip.model.Genre

data class GenreData(
    var _id : String?,
    var profileImg : String?,
    var backImg : String?,
    var name : String?,
    var subscribe : Boolean?,
    var subscribeNum : Int?,
    var youtubeUrl : String?,
    var eventList : List<SimpleConcertData?>?
){
    fun toGenre() : Genre {
        val a =  Genre(_id = _id?:"0")

        a.profileImg =  profileImg?:""
        a.backImg = backImg?:""
        a.name = name?:"서버 에서 안왔습니다."
        a.subscribe = subscribe?:false
        a.subscribeNum = subscribeNum?:0
        a.youtubeUrl = youtubeUrl?:""

        val cList = ArrayList<Concert>()
        eventList?.forEach{
            if(it!=null)
                cList.add(it.toConcert())
        }
        a.concertList = cList

        return a
    }



    override fun toString(): String ="GenreData{\n" +
            "_id : $_id\n" +
            "profileImg : $profileImg\n" +
            "backImg : $backImg\n " +
            "name : $name\n" +
            "subscribe : $subscribe\n" +
            "subscribeNum : $subscribeNum\n" +
            "youtubeUrl : $youtubeUrl\n" +
            "eventList : $eventList\n" +
            "}"
}

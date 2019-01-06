package concertrip.sopt.com.concertrip.network.response.data

import concertrip.sopt.com.concertrip.model.Artist
import concertrip.sopt.com.concertrip.model.Concert

data class ArtistData(
    var _id : String?,
    var profileImg : String?,
    var backImg : String?,
    var name : String?,
    var subscribeNum : Int?,
    var youtubeUrl : String?,
    var memberList : List<MemberData?>?,
    var eventsList : List<SimpleConcertData?>?,
    var subscribe : Boolean?
){
    fun toArtist() : Artist {
        val a =  Artist(_id = _id?:"0")

        a.profileImg =  profileImg?:""
        a.backImg = backImg?:""
        a.name = name?:"서버 에서 안왔습니다."
        a.subscribeNum = subscribeNum?:0
        a.youtubeUrl = youtubeUrl?:""

        val aList = ArrayList<Artist>()
        memberList?.forEach {
            if(it!=null)
                aList.add(it.toArtist())
        }
        a.memberList = aList

        val cList = ArrayList<Concert>()
        eventsList?.forEach{
            if(it!=null)
                cList.add(it.toConcert())
        }
        a.concertList = cList

        a.subscribe = subscribe?:false

        return a
    }



    override fun toString(): String ="ArtistData{\n" +
        "_id : $_id\n" +
        "profileImg : $profileImg\n" +
        "backImg : $backImg\n " +
        "name : $name\n" +
        "subscribeNum : $subscribeNum\n" +
        "youtubeUrl : $youtubeUrl\n" +
        "memberList : $memberList\n" +//member는 forEach 돌면서 보내져야함
        "eventList : $eventsList\n" +
        "subscribe : $subscribe" +
            "}"


}

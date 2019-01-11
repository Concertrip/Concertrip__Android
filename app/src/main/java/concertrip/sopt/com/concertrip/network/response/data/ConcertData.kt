package concertrip.sopt.com.concertrip.network.response.data

import com.google.gson.annotations.SerializedName
import concertrip.sopt.com.concertrip.model.Artist
import concertrip.sopt.com.concertrip.model.Caution
import concertrip.sopt.com.concertrip.model.Concert
import concertrip.sopt.com.concertrip.model.Seat

data class ConcertData(
    var _id : String?,
    var subscribe : Boolean?,
    var profileImg : String?,
    var backImg : String?,
    var name : String?,
    var subscribeNum : Int?,
    var youtubeUrl : String?,
    var location : String?,

    @SerializedName("memberList")
    var memberList : List<MemberData?>?,

    var date : List<String>?,
    var seatName : List<String>?,
    var seatPrice : List<String>?,

    @SerializedName("precautionList")
    var precautionList : List<PrecautionData?>?,

    var eventInfoImg : String?
){
    fun toConcert() : Concert {
        val c =  Concert(_id = _id?:"0")
        c.profileImg = profileImg?:""
        c.backImg=backImg?:""
        c.title=name?:""
        c.subscribeNum = subscribeNum?:0
        c.youtubeUrl = youtubeUrl?:""
        c.location = location?:""

        val artistList = ArrayList<Artist>()
        memberList?.forEach {
            if(it!=null) {
                artistList.add(it.toArtist())
            }

        }
        c.artistList=artistList

        c.date = date?.toList()

        val seatList = ArrayList<Seat>()
        seatName?.let{
            for(i in 0 until it.size){
                seatList.add(Seat(seatGrade = it[i], seatPrice = seatPrice?.get(i)?:"가격 미정"))
            }
        }
        c.seatList = seatList

        val cautionList = ArrayList<Caution>()
        precautionList?.forEach {
            if(it !=null){
                cautionList.add(it.toCaution())
            }
        }
        c.precaution=cautionList

        c.eventInfoImg = eventInfoImg?:""
        c.subscribe = subscribe?:false


        return c
    }
//    @SerializedName("memberList")
//    var memberList : List<MemberData>,
//
//    var date : List<String>,
//    var seatName : List<String>,
//    var seatPrice : List<String>,
//
//    @SerializedName("precautionList")
//    var precautionList : List<PrecautionData>,
//
//    var eventInfoImg : String,
//    var subscribe : Boolean

    override fun toString(): String ="ConcertData{\n" +
            "_id : $_id\n" +
            "profileImg : $profileImg\n" +
            "backImg : $backImg\n " +
            "name : $name\n" +
            "subscribeNum : $subscribeNum\n" +
            "youtubeUrl : $youtubeUrl\n" +
            "location : $location\n"+
            "memberList : $memberList\n" +
            "date : $date\n" +
            "seatName : $seatName\n" +
            "seatPrice : $seatPrice\n" +
            "precautionList : $precautionList\n" +
            "eventInfoImg : $eventInfoImg\n" +
            "subscribe : $subscribe" +
            "}"

}

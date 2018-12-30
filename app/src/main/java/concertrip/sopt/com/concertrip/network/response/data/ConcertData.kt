package concertrip.sopt.com.concertrip.network.response.data

import com.google.gson.annotations.SerializedName
import concertrip.sopt.com.concertrip.model.Artist
import concertrip.sopt.com.concertrip.model.Caution
import concertrip.sopt.com.concertrip.model.Concert

data class ConcertData(
    var _id : String,
    var profileImg : String,
    var backImg : String,
    var name : String,
    var subscribeNum : Int,
    var youtubeUrl : String,
    var location : String,

    @SerializedName("memberList")
    var memberList : List<MemberData>,

    var date : List<String>,
    var seatName : List<String>,
    var seatPrice : List<String>,

    @SerializedName("precautionList")
    var precautionList : List<PrecautionData>,

    var eventInfoImg : String,
    var subscribe : Boolean
){
    fun toConcert() : Concert {
        val c =  Concert(_id = _id)
        c.profileImg = profileImg
        c.backImg=backImg
        c.title=name//왜 TITLE과 name이 다르지 !!!! TODO SERVER에서 고쳐야하 할것
        c.subscribeNum = subscribeNum
        c.youtubeUrl = youtubeUrl
        c.location = location

        val artistList = ArrayList<Artist>()
        memberList.forEach {
            artistList.add(it.toArtist())
        }
        c.artistList=artistList

        c.date = date.toMutableList()
        c.seatName = seatName.toMutableList()
        c.seatPrice = seatPrice.toMutableList()

        val cautionList = ArrayList<Caution>()
        precautionList.forEach {
            cautionList.add(it.toCaution())
        }
        c.precaution=cautionList

        c.eventInfoImg = eventInfoImg
        c.subscribe = subscribe

        return c
    }

    companion object {
        fun getDummy() : ConcertData=ConcertData("","https://search.pstatic.net/common?type=a&size=120x150&quality=95&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fpeople%2Fportrait%2F201801%2F20180108113919887.jpg",
            "", "지코",
            0,"ZHoLaLlL5lA","",ArrayList<MemberData>(),ArrayList<String>(),ArrayList<String>(),ArrayList<String>(),ArrayList<PrecautionData>(),
            "http://tenasia.hankyung.com/webwp_kr/wp-content/uploads/2018/07/2018071709244719489-540x734.jpg",false)
        fun getDummyArray() : List<ConcertData>{
            val list = ArrayList<ConcertData>()
            list.add(getDummy())
            list.add(getDummy())
            list.add(getDummy())
            list.add(getDummy())
            return list
        }
    }
}

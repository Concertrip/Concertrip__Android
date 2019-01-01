package concertrip.sopt.com.concertrip.network.response.data

import com.google.gson.annotations.SerializedName
import concertrip.sopt.com.concertrip.model.Artist
import concertrip.sopt.com.concertrip.model.Caution
import concertrip.sopt.com.concertrip.model.Concert
import concertrip.sopt.com.concertrip.model.Seat

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
//        c.seatName = seatName.toMutableList()
//        c.seatPrice = seatPrice.toMutableList()

        val seatList = ArrayList<Seat>()
//        seatName.forEach {
//            seatList.add(Seat(it, ))
//        }

        for(i in 0 until seatName.size){
            seatList.add(Seat(seatGrade = seatName[i], seatPrice = seatPrice[i]))
        }
        c.seatList = seatList

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
            0,"https://www.youtube.com/watch?v=YfQzz00Oc_M","",ArrayList<MemberData>(),ArrayList<String>(), listOf("VIP", "R", "스탠딩"),listOf("120,000", "80,000", "70,000"),ArrayList<PrecautionData>(),
            "http://tenasia.hankyung.com/webwp_kr/wp-content/uploads/2018/07/2018071709244719489-540x734.jpg",false)

        fun getDummy1() : ConcertData=ConcertData("","https://search.pstatic.net/common?type=a&size=120x150&quality=95&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fpeople%2Fportrait%2F201801%2F20180108113919887.jpg","https://img.huffingtonpost.com/asset/5ba482b82400003100546bc3.jpeg","힙합페스티벌",
            1000,"https://www.youtube.com/watch?v=rsvJOrI2GfE","",ArrayList<MemberData>(),ArrayList<String>(),ArrayList<String>(),ArrayList<String>(),ArrayList<PrecautionData>(),""
            ,false)
        fun getDummy2() : ConcertData=ConcertData("","https://search.pstatic.net/common?type=a&size=120x150&quality=95&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fpeople%2Fportrait%2F201801%2F20180108113919887.jpg","http://blogfiles.naver.net/20150115_278/jimin1226_1421248926310a8IkG_JPEG/vsbspvs.jpg","SM타운 콘서트",
            1050,"https://www.youtube.com/watch?v=eelfrHtmk68","",ArrayList<MemberData>(),ArrayList<String>(),ArrayList<String>(),ArrayList<String>(),ArrayList<PrecautionData>(),""
            ,false)
        fun getDummy3() : ConcertData=ConcertData("","https://search.pstatic.net/common?type=a&size=120x150&quality=95&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fpeople%2Fportrait%2F201801%2F20180108113919887.jpg","http://imgnews.naver.net/image/213/2016/07/20/20160720_1468977905_09715900_1_99_20160720103907.jpg","휘성 콘서트",
            1100,"https://www.youtube.com/watch?v=YXiLkrSft1w","",ArrayList<MemberData>(),ArrayList<String>(),ArrayList<String>(),ArrayList<String>(),ArrayList<PrecautionData>(),""
            ,false)

        fun getDummyArray() : List<ConcertData>{
            val list = ArrayList<ConcertData>()
            list.add(getDummy1())
            list.add(getDummy2())
            list.add(getDummy3())
            return list
        }
    }
}

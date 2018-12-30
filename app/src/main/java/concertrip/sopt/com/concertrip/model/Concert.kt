package concertrip.sopt.com.concertrip.model

import concertrip.sopt.com.concertrip.interfaces.ListData
import concertrip.sopt.com.concertrip.network.response.data.PrecautionData
import concertrip.sopt.com.concertrip.utillity.Constants

data class Concert(

    var _id : String,
    var title : String,
    var profileImg : String,
    var backImg : String,
    var location : String,
    var station: String,
    var genre: String,
    var cast : String,
    var date : List<String>,
    var seatPrice : List<String>,
    var seatName : List<String>,
    var precaution : List<PrecautionData>,
    var eventInfoImg : String,
    var youtubeUrl : String,
    var subscribeNum : Int,
    var artistList : List<Artist>,
    var subscribe : Boolean

) : ListData {

    var tag  : String? = null

    constructor(_id : String) : this(_id = _id, title = "", genre = "", youtubeUrl = "",backImg = "", eventInfoImg = "",
        profileImg = "",cast = "", date = ArrayList<String>(),artistList = ArrayList<Artist>(),location = "",station = "",
        seatName = ArrayList<String>(), precaution=ArrayList<PrecautionData>(),seatPrice=ArrayList<String>() ,  subscribeNum = 0, subscribe = false)


    private fun makeTag() : String ="#$genre #$genre"
    override fun getType(): Int = Constants.TYPE_CONCERT

    override fun getId(): String = _id

    override fun getMainTitle(): String =title

    override fun getSubTitle(): String{
        tag?.let {
            return it
        }
        return  makeTag()

    }

    override fun getImageUrl(): String =profileImg


    companion object {
        fun getDummyArray() : ArrayList<Concert>{
            val list = ArrayList<Concert>()
            list.add(Concert.getDummy(1.toString()))
            list.add(Concert.getDummy_1(2.toString()))
            list.add(Concert.getDummy_2(3.toString()))
            return list

        }

        fun getDummy(_id : String)= Concert(_id = _id, title = "힙합 페스티발", genre = "힙합", youtubeUrl = "https://www.youtube.com/watch?v=Vl1kO9hObpA",backImg = "https://img.huffingtonpost.com/asset/5ba482b82400003100546bc3.jpeg",
eventInfoImg = "",

            profileImg = "https://search.pstatic.net/common?type=a&size=120x150&quality=95&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fpeople%2Fportrait%2F201801%2F20180108113919887.jpg"
            ,cast = "지코", date = listOf("2018-12-20", "2018-12-21"),artistList = ArrayList<Artist>(),location = "",station = "",
            seatName = listOf("R", "VIP"), seatPrice = listOf("80,000", "120,000") , precaution = ArrayList<PrecautionData>(),  subscribeNum = 0, subscribe = false)


        fun getDummy_1(_id : String)= Concert(_id = _id, title = "SM콘서트", genre = "보이그룹", youtubeUrl = "https://www.youtube.com/watch?v=_cAskH1PtmQ",backImg = "http://blogfiles.naver.net/20150115_278/jimin1226_1421248926310a8IkG_JPEG/vsbspvs.jpg",
 eventInfoImg = "",
            profileImg = "https://search.pstatic.net/common?type=a&size=120x150&quality=95&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fpeople%2Fportrait%2F201805%2F20180530151238597-2777067.jpg"
            ,cast = "태민", date = listOf("2018-12-16", "2018-12-17"),artistList = ArrayList<Artist>(),location = "",station = "",
            seatName = listOf("플로어", "3층"), seatPrice = listOf("110,000", "120,000") ,  precaution = ArrayList<PrecautionData>(),  subscribeNum = 0, subscribe = false)


        fun getDummy_2(_id : String)= Concert(_id = _id, title = "휘성 콘서트", genre = "#발라드", youtubeUrl = "https://www.youtube.com/watch?v=YXiLkrSft1w",backImg = "http://imgnews.naver.net/image/213/2016/07/20/20160720_1468977905_09715900_1_99_20160720103907.jpg",
 eventInfoImg = "",
            profileImg = "https://search.pstatic.net/common?type=a&size=120x150&quality=95&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fpeople%2F185%2F201709151548017101.jpg"
            ,cast = "휘성", date = listOf("2018-12-28", "2018-12-29"),artistList = ArrayList<Artist>(),location = "",station = "",
            seatName = listOf("R", "VIP"), seatPrice = listOf("70,000", "80,000") ,  precaution = ArrayList<PrecautionData>(),  subscribeNum = 0, subscribe = false)
    }
}
package concertrip.sopt.com.concertrip.model

import concertrip.sopt.com.concertrip.interfaces.ListData
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
    var youtubeUrl : String,
    var subscribeNum : Int,
    var artistList : List<Artist>,
    var isSubscribe: Boolean

) : ListData {

    var tag  : String? = null

    constructor(_id : String) : this(_id = _id, title = "", genre = "", youtubeUrl = "",backImg = "",
        profileImg = "",cast = "", date = ArrayList(),artistList = ArrayList<Artist>(),location = "",station = "",
        seatName = ArrayList(), seatPrice=ArrayList() ,  subscribeNum = 0,isSubscribe = false)


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
    override fun isSubscribe(): Boolean? =isSubscribe


    companion object {
        fun getDummyArray() : ArrayList<Concert>{
            val list = ArrayList<Concert>()
            for(i in 0..5) {
                val c = Concert.getDummy(i.toString())
                list.add(c)
            }
            return list

        }

        fun getDummy(_id : String)= Concert(_id = _id, title = "힙합 페스티발", genre = "#힙합", youtubeUrl = "",backImg = "https://img.huffingtonpost.com/asset/5ba482b82400003100546bc3.jpeg",
            profileImg = "https://search.pstatic.net/common?type=a&size=120x150&quality=95&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fpeople%2Fportrait%2F201801%2F20180108113919887.jpg"
            ,cast = "지코", date = listOf("2018-12-20", "2018-12-21"),artistList = ArrayList<Artist>(),location = "",station = "",
            seatName = listOf("R", "VIP"), seatPrice = listOf("80,000", "120,000") ,  subscribeNum = 0,isSubscribe = false)
    }
}
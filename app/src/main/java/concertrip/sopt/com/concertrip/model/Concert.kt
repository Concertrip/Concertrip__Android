package concertrip.sopt.com.concertrip.model

import concertrip.sopt.com.concertrip.interfaces.ListData
import concertrip.sopt.com.concertrip.utillity.Constants

<<<<<<< HEAD
data class Concert(

    var idx: Int,
    var title : String,
    var profileImg : String,
    var backImg : String?,
    var location : String?,
    var station : String?,
    var genre : String?,
    var date : String?,
    var price : String?,
    var youtubeUrl : String?,
    var subscribeNum : Int,
    var artistList : ArrayList<Artist>

) : ListData{
    constructor(idx : Int, title : String, profileImg : String)
            : this(idx, title, profileImg, null, null, null, null,
        null, null, null, 0, ArrayList())

    constructor(idx : Int)
            : this(idx, "힙합 페스티벌", "https://search.pstatic.net/common?type=a&size=120x150&quality=95&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fpeople%2Fportrait%2F201801%2F20180108113919887.jpg",
        "https://img.huffingtonpost.com/asset/5ba482b82400003100546bc3.jpeg", null, null, null,
        "2018-12-23", null, null, 0, ArrayList())


    fun getTag() : String ="#$genre #$genre"
=======
class Concert(var _id : String, var title : String, var profileImg : String,
              var backImg : String, var location : String, var station: String,var genre: String,
              var cast : String, var date : String,var price : String, var youtubeUrl : String,var  subscribeNum : Int, var artistList : ArrayList<Artist>) : ListData {

//    TODO 바꿔야 할것
//    var date : List<String>,
//    var seatName : List<String>,
//    var seatPrice : List<String>,

    var tag  : String? = null

    constructor(_id : String) : this(_id = _id, title = "", genre = "", youtubeUrl = "",backImg = "",
        profileImg = "",cast = "", date = "",artistList = ArrayList<Artist>(),location = "",station = "",price="" ,  subscribeNum = 0)


    private fun makeTag() : String ="#$genre #$genre"
>>>>>>> upstream/develop

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
            for(i in 0..5) {
<<<<<<< HEAD
                val c = Concert(i)
=======
                val c = Concert.getDummy(i.toString())
>>>>>>> upstream/develop
                list.add(c)
            }
            return list

        }

        fun getDummy(_id : String)= Concert(_id = _id, title = "힙합 페스티발", genre = "#힙합", youtubeUrl = "",backImg = "https://img.huffingtonpost.com/asset/5ba482b82400003100546bc3.jpeg",
            profileImg = "https://search.pstatic.net/common?type=a&size=120x150&quality=95&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fpeople%2Fportrait%2F201801%2F20180108113919887.jpg"
            ,cast = "지코", date = "2018-12-20",artistList = ArrayList<Artist>(),location = "",station = "",price="" ,  subscribeNum = 0)
    }
}
package concertrip.sopt.com.concertrip.model

import concertrip.sopt.com.concertrip.interfaces.ListData
import concertrip.sopt.com.concertrip.utillity.Constants

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

    override fun getType(): Int = Constants.TYPE_CONCERT

    override fun getIndex(): Int = idx

    override fun getMainTitle(): String =title

    override fun getSubTitle(): String =getTag()

    override fun getImageUrl(): String =profileImg


    companion object {
        fun getDummyArray() : ArrayList<Concert>{
            val list = ArrayList<Concert>()
            for(i in 0..5) {
                val c = Concert(i)
                list.add(c)
            }
            return list

        }
    }
}
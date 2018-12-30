package concertrip.sopt.com.concertrip.model

import concertrip.sopt.com.concertrip.interfaces.ListData
import concertrip.sopt.com.concertrip.utillity.Constants

data class Artist(
    var _id : String,
    var profileImg : String,
    var backImg : String?,
    var name : String,
    var genre : String?,
    var youtubeUrl : String?,
    var subscribeNum : Int
//, var concertList : List<Concert>
) : ListData{


    var tag : String?=null

    constructor(_id : String, name  :String, profileImg : String) : this(_id,profileImg,null,name,null,null,0)
    constructor(_id: String): this(_id,"", "", "", "", "", 0)


    private fun makeTag() : String="#$genre #$genre"
    override fun getType(): Int = Constants.TYPE_ARTIST
    override fun getId(): String = _id
    override fun getMainTitle(): String =name
    override fun getSubTitle(): String{
        tag?.let {
            return it
        }
        return makeTag()
    }
    override fun getImageUrl(): String=profileImg

    companion object {

        @JvmStatic fun getDummyArray() : ArrayList<Artist>{
            val list = ArrayList<Artist>()
            for(i in 0..5) {
                val a = Artist.getDummy(i.toString())
                list.add(a)
            }
            return list

        }

        fun getDummy(_id : String) : Artist = Artist(_id,"https://search.pstatic.net/common?type=a&size=120x150&quality=95&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fpeople%2Fportrait%2F201801%2F20180108113919887.jpg",
            "https://img.huffingtonpost.com/asset/5ba482b82400003100546bc3.jpeg",
            "지코",
            "#힙합",
            "https://www.youtube.com/watch?v=Vl1kO9hObpA",
            1000)

    }


}
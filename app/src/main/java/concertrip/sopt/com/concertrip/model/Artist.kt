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
    var subscribeNum : Int,
    var concertList : List<Concert>,
  var isSubscribe: Boolean
) : ListData{

    var tag : String?=null


    constructor(_id : String,  profileImg : String,backImg: String?,name  :String, genre:String,youtubeUrl:String,subscribeNum :Int,concertList: List<Concert>)
            : this(_id,profileImg,null,name,genre,youtubeUrl,subscribeNum, listOf(),false)
    constructor(_id : String, name  :String, profileImg : String) : this(_id,profileImg,null,name,null,null,0, listOf(),false)
    constructor(_id: String): this(_id,"", "", "", "", "", 0, listOf(),false)


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
    override fun isSubscribe(): Boolean? = isSubscribe

    companion object {

        @JvmStatic fun getDummyArray() : ArrayList<Artist>{
            val list = ArrayList<Artist>()
            list.add(Artist.getDummy(1.toString()))
            list.add(Artist.getDummy_2(2.toString()))
            list.add(Artist.getDummy_3(3.toString()))
            /*for(i in 0..5) {
                val a = Artist.getDummy(i.toString())
                list.add(a)
            }*/
            return list

        }

        @JvmStatic fun getDummyArray2() : ArrayList<Artist>{
            val list = ArrayList<Artist>()
            list.add(Artist.getDummy2(1.toString()))
            list.add(Artist.getDummy2_2(2.toString()))
            list.add(Artist.getDummy2_3(3.toString()))
            return list
        }


        fun getDummy(_id : String) : Artist = Artist(_id,"https://s3.namuwikiusercontent.com/s/ffb9632dd81ca99329391af0017f4e3026ffeaa5cb062c5c91543bbf09a3221bbbecfced0d650c449fb88241d91c8fb97b7ba055dbe4072f52af5cf6400760a175878e3646144264919e9a7cebab6106d626b92ffb27b77d479d30426e512829",
            "data:image/jpeg;base64,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",
            "송지은",
            "kPOP",
            "https://www.youtube.com/watch?v=Njttm0_n9Bw",

            1000, ArrayList())
        fun getDummy4(_id : String) : Artist = Artist(_id,"https://search.pstatic.net/common?type=a&size=120x150&quality=95&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fpeople%2Fportrait%2F201801%2F20180108113919887.jpg",
            "https://img.huffingtonpost.com/asset/5ba482b82400003100546bc3.jpeg",
            "지코",
            "힙합",
            "https://www.youtube.com/watch?v=Vl1kO9hObpA",

            1000, ArrayList())
        fun getDummy_2(_id : String) : Artist = Artist(_id,"https://search.pstatic.net/common?type=a&size=120x150&quality=95&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fpeople%2Fportrait%2F201805%2F20180530151238597-2777067.jpg",
            "http://blogfiles.naver.net/20150115_278/jimin1226_1421248926310a8IkG_JPEG/vsbspvs.jpg",
            "태민",
            "보이그룹",
            "https://www.youtube.com/watch?v=_cAskH1PtmQ",
            1050,  ArrayList())
        fun getDummy_3(_id : String) : Artist = Artist(_id,"https://search.pstatic.net/common?type=a&size=120x150&quality=95&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fpeople%2F185%2F201709151548017101.jpg",
            "http://imgnews.naver.net/image/213/2016/07/20/20160720_1468977905_09715900_1_99_20160720103907.jpg",
            "휘성",
            "발라드",
            "https://www.youtube.com/watch?v=YXiLkrSft1w",
            1100,  ArrayList())


        fun getDummy2(_id: String) : Artist = Artist(_id, "http://blogfiles.naver.net/20160930_80/ykoe_1475207138993ImiEq_PNG/5.PNG",
            "http://post.phinf.naver.net/20161011_80/1476173057511gPMvq_JPEG/IWqDsLibSzRK3x-QwcHAjBkIFABY.jpg", "레드벨벳", "걸그룹",
            "https://www.youtube.com/watch?v=IWJUPY-2EIM", 2000,  ArrayList())

        fun getDummy2_2(_id: String) : Artist = Artist(_id, "https://search.pstatic.net/common?type=a&size=225x150&quality=95&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fpeople%2F198%2F201812281441121753.png",
            "http://imgnews.naver.net/image/052/2018/05/02/201805020914542071_d_20180502091605815.jpg", "여자친구", "걸그룹",
            "https://www.youtube.com/watch?v=_XyBa8QsVQU", 2050,  ArrayList())

        fun getDummy2_3(_id: String) : Artist = Artist(_id, "https://search.pstatic.net/common?type=a&size=225x150&quality=95&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fpeople%2FportraitGroup%2F201809%2F20180903031230220-7065202.jpg",
            "http://imgnews.naver.net/image/079/2016/08/05/20160805181049464887_99_20160805182804.jpg", "오마이걸", "걸그룹",
            "https://www.youtube.com/watch?v=RrvdjyIL0fA", 2100,  ArrayList())
    }


}
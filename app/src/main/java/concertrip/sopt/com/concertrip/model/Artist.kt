package concertrip.sopt.com.concertrip.model

import concertrip.sopt.com.concertrip.interfaces.ListData
import concertrip.sopt.com.concertrip.utillity.Constants

data class Artist(
    var _id: String,
    var profileImg: String,
    var backImg: String?,
    var name: String,
    var genre: String?,
    var youtubeUrl: String?,
    var subscribeNum: Int,
    var memberList : List<Artist>?,
    var concertList: List<Concert>?,
    var subscribe: Boolean
) : ListData {

    var tag: String? = null


    constructor(
        _id: String,
        profileImg: String,
        backImg: String?,
        name: String,
        genre: String,
        youtubeUrl: String,
        subscribeNum: Int,
        concertList: List<Concert>
    )
            : this(_id, profileImg, null, name, genre, youtubeUrl, subscribeNum, listOf(), listOf(),false)

    constructor(_id: String, name: String, profileImg: String) : this(
        _id,
        profileImg,
        null,
        name,
        null,
        null,
        0,
        listOf(),
        listOf(),
        false
    )

    constructor(_id: String) : this(_id, "", "", "", "", "", 0, listOf(), listOf(),false)


    private fun makeTag(): String = "#$genre #$genre"
    override fun getType(): Int = Constants.TYPE_ARTIST
    override fun getId(): String = _id
    override fun getMainTitle(): String = name
    override fun getSubTitle(): String {
        tag?.let {
            return it
        }
        return makeTag()
    }

    override fun getImageUrl(): String = profileImg
    override fun isSubscribe(): Boolean? = subscribe

    companion object {

        @JvmStatic
        fun getDummyArray(): ArrayList<Artist> {
            val list = ArrayList<Artist>()
//            list.add(Artist.getDummy(1.toString()))
//            list.add(Artist.getDummy_2(2.toString()))
//            list.add(Artist.getDummy_3(3.toString()))
//            /*for(i in 0..5) {
//                val a = Artist.getDummy(i.toString())
//                list.add(a)
//            }*/
            return list

        }

        @JvmStatic
        fun getDummyArray2(): ArrayList<Artist> {
            val list = ArrayList<Artist>()
            list.add(Artist.getDummy2(1.toString()))
            list.add(Artist.getDummy2_2(2.toString()))
            list.add(Artist.getDummy2_3(3.toString()))
            return list
        }


        fun getDummy(_id: String): Artist = Artist(
            _id,
            "https://s3.namuwikiusercontent.com/s/ffb9632dd81ca99329391af0017f4e3026ffeaa5cb062c5c91543bbf09a3221bbbecfced0d650c449fb88241d91c8fb97b7ba055dbe4072f52af5cf6400760a175878e3646144264919e9a7cebab6106d626b92ffb27b77d479d30426e512829",
            "",
            "송지은",
            "kPOP",
            "https://www.youtube.com/watch?v=Njttm0_n9Bw",

            1000,
            ArrayList()
        )

        fun getDummy4(_id: String): Artist = Artist(
            _id,
            "https://search.pstatic.net/common?type=a&size=120x150&quality=95&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fpeople%2Fportrait%2F201801%2F20180108113919887.jpg",
            "https://img.huffingtonpost.com/asset/5ba482b82400003100546bc3.jpeg",
            "지코",
            "힙합",
            "https://www.youtube.com/watch?v=Vl1kO9hObpA",

            1000,
            ArrayList()
        )

        fun getDummy_2(_id: String): Artist = Artist(
            _id,
            "https://search.pstatic.net/common?type=a&size=120x150&quality=95&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fpeople%2Fportrait%2F201805%2F20180530151238597-2777067.jpg",
            "http://blogfiles.naver.net/20150115_278/jimin1226_1421248926310a8IkG_JPEG/vsbspvs.jpg",
            "태민",
            "보이그룹",
            "https://www.youtube.com/watch?v=_cAskH1PtmQ",
            1050,
            ArrayList()
        )

        fun getDummy_3(_id: String): Artist = Artist(
            _id,
            "https://search.pstatic.net/common?type=a&size=120x150&quality=95&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fpeople%2F185%2F201709151548017101.jpg",
            "http://imgnews.naver.net/image/213/2016/07/20/20160720_1468977905_09715900_1_99_20160720103907.jpg",
            "휘성",
            "발라드",
            "https://www.youtube.com/watch?v=YXiLkrSft1w",
            1100,
            ArrayList()
        )


        fun getDummy2(_id: String): Artist = Artist(
            _id,
            "http://blogfiles.naver.net/20160930_80/ykoe_1475207138993ImiEq_PNG/5.PNG",
            "http://post.phinf.naver.net/20161011_80/1476173057511gPMvq_JPEG/IWqDsLibSzRK3x-QwcHAjBkIFABY.jpg",
            "레드벨벳",
            "걸그룹",
            "https://www.youtube.com/watch?v=IWJUPY-2EIM",
            2000,
            ArrayList()
        )

        fun getDummy2_2(_id: String): Artist = Artist(
            _id,
            "https://search.pstatic.net/common?type=a&size=225x150&quality=95&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fpeople%2F198%2F201812281441121753.png",
            "http://imgnews.naver.net/image/052/2018/05/02/201805020914542071_d_20180502091605815.jpg",
            "여자친구",
            "걸그룹",
            "https://www.youtube.com/watch?v=_XyBa8QsVQU",
            2050,
            ArrayList()
        )

        fun getDummy2_3(_id: String): Artist = Artist(
            _id,
            "https://search.pstatic.net/common?type=a&size=225x150&quality=95&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fpeople%2FportraitGroup%2F201809%2F20180903031230220-7065202.jpg",
            "http://imgnews.naver.net/image/079/2016/08/05/20160805181049464887_99_20160805182804.jpg",
            "오마이걸",
            "걸그룹",
            "https://www.youtube.com/watch?v=RrvdjyIL0fA",
            2100,
            ArrayList()
        )
    }


}
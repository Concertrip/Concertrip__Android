package concertrip.sopt.com.concertrip.model

import concertrip.sopt.com.concertrip.utillity.Constants


class Genre(_id: String,
                 profileImg: String,
                 backImg: String?,
                 name: String,
                 genre: String?,
                 youtubeUrl: String?,
                 subscribeNum: Int,
                 memberList : List<Artist>?,
                 concertList: List<Concert>?,
                 subscribe: Boolean) : Artist(_id,profileImg,backImg,name,genre,youtubeUrl,subscribeNum,memberList ,concertList,subscribe){

    constructor(_id : String) : this(_id,"",null,"",
        null,null,0,null,null,false)

    override fun getType(): Int = Constants.TYPE_GENRE
}

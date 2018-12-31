package concertrip.sopt.com.concertrip.network.response.data

import concertrip.sopt.com.concertrip.model.Artist
import concertrip.sopt.com.concertrip.model.Concert

data class ArtistData(
    var _id : String,
    var subscribeNum : Int,
    var name : String,
    var profileImg : String,
    var backImg : String,
    var tag : String,
    var youtubeUrl : String,
    var concertList : List<Concert>
){
    fun toArtist() : Artist {
        val a =  Artist(_id = _id)
        a.name = name
        a.profileImg =  profileImg
        a.backImg = backImg
        a.tag = tag
        a.youtubeUrl = youtubeUrl

        val list = ArrayList<Concert>()
        concertList.forEach{
            list.add(it)
        }
        a.concertList = list

        return a
    }
  
    companion object {
        fun getDummy() : ArtistData{
            return ArtistData("",0,"송지은","https://s3.namuwikiusercontent.com/s/ffb9632dd81ca99329391af0017f4e3026ffeaa5cb062c5c91543bbf09a3221bbbecfced0d650c449fb88241d91c8fb97b7ba055dbe4072f52af5cf6400760a175878e3646144264919e9a7cebab6106d626b92ffb27b77d479d30426e512829",
                "https://i.ytimg.com/vi/lB-GvZY6lTU/hqdefault.jpg",
                "K-POP","https://www.youtube.com/watch?v=Njttm0_n9Bw",
                Concert.getDummyArray())
        }


        fun getDummyArray(): ArrayList<ArtistData>{
            val list = ArrayList<ArtistData>()
            list.add(ArtistData("",1000, "지코", "https://search.pstatic.net/common?type=a&size=120x150&quality=95&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fpeople%2Fportrait%2F201801%2F20180108113919887.jpg",
              "https://img.huffingtonpost.com/asset/5ba482b82400003100546bc3.jpeg", "힙합","https://www.youtube.com/watch?v=Vl1kO9hObpA",
                listOf()))
            list.add(ArtistData("", 1050, "태민","https://search.pstatic.net/common?type=a&size=120x150&quality=95&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fpeople%2Fportrait%2F201805%2F20180530151238597-2777067.jpg",
                "http://blogfiles.naver.net/20150115_278/jimin1226_1421248926310a8IkG_JPEG/vsbspvs.jpg","보이그룹","https://www.youtube.com/watch?v=_cAskH1PtmQ",listOf()))
            list.add(ArtistData("",1100,"휘성","https://search.pstatic.net/common?type=a&size=120x150&quality=95&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fpeople%2F185%2F201709151548017101.jpg",
                "http://imgnews.naver.net/image/213/2016/07/20/20160720_1468977905_09715900_1_99_20160720103907.jpg","발라드","https://www.youtube.com/watch?v=YXiLkrSft1w",listOf()))

            return list
        }
    }
}

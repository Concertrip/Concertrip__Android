package concertrip.sopt.com.concertrip.network.response.data

import concertrip.sopt.com.concertrip.model.Artist

data class ArtistData(
    var _id : String,
    var subscribeNum : Int,
    var name : String,
    var profileImg : String,
    var backImg : String,
    var tag : String,
    var youtubeUrl : String
){
    fun toArtist(): Artist = Artist(_id = _id,profileImg = profileImg,backImg = backImg,name = name, genre = "" ,youtubeUrl = youtubeUrl,subscribeNum = subscribeNum)
    companion object {
        fun getDummy() : ArtistData{
            return ArtistData("",0,"","","","","")
        }
        fun getDummyArray(): ArrayList<ArtistData>{
            val list = ArrayList<ArtistData>()
            list.add(getDummy())
            list.add(getDummy())
            list.add(getDummy())
            list.add(getDummy())
            list.add(getDummy())
            return list
        }
    }
}

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
    var youtubeUrl : String
){
    fun toArtist() : Artist {
        val a =  Artist(_id = _id)
        a.name = name
        a.profileImg =  profileImg
        a.backImg = backImg
        a.tag = tag
        a.youtubeUrl = youtubeUrl

//        val list = ArrayList<Concert>()
//        concertList.forEach{
//            list.add(it.toConcert())
//        }
//        c.concertList = list

        return a
    }
  
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

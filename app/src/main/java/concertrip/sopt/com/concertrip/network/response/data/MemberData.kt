package concertrip.sopt.com.concertrip.network.response.data

import concertrip.sopt.com.concertrip.model.Artist

data class MemberData(
    var _id : String,
    var name : String,
    var profileImg : String,
//    var filter : List<String>,
    var subscribe : Boolean
){
    fun toArtist(): Artist {
        val artist = Artist(_id)

        artist.name = name
        artist.profileImg = profileImg
        artist.subscribe = subscribe

        return artist
    }


    override fun toString(): String ="MemberData{\n" +
            "_id : $_id\n" +
            "name : $name\n" +
            "profileImg : $profileImg\n" +

            /*"filter : $filter\n" + */

            "subscribe : $subscribe}"
}
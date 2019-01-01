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
        return Artist(_id, name,profileImg)
    }
}
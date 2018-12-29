package concertrip.sopt.com.concertrip.network.response.data

import concertrip.sopt.com.concertrip.model.Artist

data class ConcertMemberData(
    var _id : String,
    var name : String,
    var profileImg : String,
    var filter : String?
){
    fun toArtist(): Artist {
        return Artist(_id, name,profileImg)
    }
}
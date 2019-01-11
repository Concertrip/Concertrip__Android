package concertrip.sopt.com.concertrip.network.response.data

import concertrip.sopt.com.concertrip.model.Artist
import concertrip.sopt.com.concertrip.model.Genre

data class SimpleGenreData(
    var _id : String?,
    var name : String?,
    var profileImg : String?,
    var subscribe : Boolean?
){

    fun toGenre() : Genre {
        val a = Genre( _id?:"")
        a.name = name?:""
        a.profileImg=profileImg?:""
        a.subscribe=subscribe?:false

        return a
    }

    override fun toString(): String ="SimpleGenreData{_id : $_id, name = $name, profileImg = $profileImg, subscribe = $subscribe}"


}

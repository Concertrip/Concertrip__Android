package concertrip.sopt.com.concertrip.network.response.data

import concertrip.sopt.com.concertrip.model.Artist

data class SimpleArtistData(
    var _id : String?,
    var name : String?,
    var profileImg : String?,
    var subscribe : Boolean?,
    var group : Boolean?
){
    fun toArtist() : Artist {
        val a = Artist(_id = _id?:"")
        a.name = name?:""
        a.profileImg=profileImg?:""
        a.subscribe=subscribe?:false

        return a
    }
    override fun toString(): String ="SimpleArtistData{_id : $_id, name = $name, profileImg = $profileImg, subscribe = $subscribe , group = $group}"

}
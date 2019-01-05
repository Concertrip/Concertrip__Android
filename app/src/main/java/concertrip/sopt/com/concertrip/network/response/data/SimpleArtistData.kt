package concertrip.sopt.com.concertrip.network.response.data

import concertrip.sopt.com.concertrip.model.Artist

data class SimpleArtistData(
    var _id : String,
    var name : String,
    var tag : String
){
    fun toArtist() : Artist {
        val a = Artist(_id = _id)
        a.name = name
        a.tag = tag

        return a
    }
    override fun toString(): String ="SimpleArtistData{_id : $_id, name = $name, tag = $tag}"

}

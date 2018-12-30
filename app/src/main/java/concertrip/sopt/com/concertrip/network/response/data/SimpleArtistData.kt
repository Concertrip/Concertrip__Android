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

    companion object {
        fun getDummy() : SimpleArtistData = SimpleArtistData("", "지코", "힙합")


        fun getDummyList() : List<SimpleArtistData> {
            val list = listOf(SimpleArtistData("", "지코", "힙합"), SimpleArtistData("", "태민", "보이그룹"),
                SimpleArtistData("", "휘성", "발라드"))
            return list
        }
    }
}

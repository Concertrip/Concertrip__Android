package concertrip.sopt.com.concertrip.network.response.data

import concertrip.sopt.com.concertrip.model.Artist
import concertrip.sopt.com.concertrip.model.Genre

data class SimpleGenreData(
    var _id : String,
    var name : String,
    var profileImg : String,
    var subscribe : Boolean
){

    fun toGenre() : Genre {
        val a = Genre( _id)
        a.name = name
        a.profileImg=profileImg
        a.subscribe=subscribe

        return a
    }

    override fun toString(): String ="SimpleGenreData{_id : $_id, name = $name, profileImg = $profileImg, subscribe = $subscribe}"

    companion object {
        fun getDummy() : SimpleGenreData = SimpleGenreData("", "지코", "힙합",false)


        fun getDummyList() : List<SimpleGenreData> {
            val list = listOf(SimpleGenreData("", "지코", "힙합",false), SimpleGenreData("", "태민", "보이그룹",false),
                SimpleGenreData("", "휘성", "발라드",false))
            return list
        }
    }
}

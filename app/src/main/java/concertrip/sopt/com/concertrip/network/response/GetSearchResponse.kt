package concertrip.sopt.com.concertrip.network.response

import com.google.gson.annotations.SerializedName
import concertrip.sopt.com.concertrip.model.Artist
import concertrip.sopt.com.concertrip.model.Concert
import concertrip.sopt.com.concertrip.network.response.data.SimpleArtistData
import concertrip.sopt.com.concertrip.network.response.data.SimpleConcertData
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel


data class GetSearchResponse (
    @SerializedName("data")
    var concertDataList : List<SimpleConcertData>,
    var artistDataList : List<SimpleArtistData>
): BaseModel(){
    fun toConcertList() : ArrayList<Concert>{
        val list = ArrayList<Concert>()
        concertDataList.forEach {
            list.add(it.toConcert())
        }
        return list
    }

    fun toArtistList() : ArrayList<Artist>{
        val list = ArrayList<Artist>()
        artistDataList.forEach {
            list.add(it.toArtist())
        }
        return list
    }
}
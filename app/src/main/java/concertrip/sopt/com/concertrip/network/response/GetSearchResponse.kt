package concertrip.sopt.com.concertrip.network.response

import com.google.gson.annotations.SerializedName
import concertrip.sopt.com.concertrip.model.Artist
import concertrip.sopt.com.concertrip.model.Concert
import concertrip.sopt.com.concertrip.model.Genre
import concertrip.sopt.com.concertrip.network.response.data.SearchData
import concertrip.sopt.com.concertrip.network.response.data.SimpleArtistData
import concertrip.sopt.com.concertrip.network.response.data.SimpleConcertData
import concertrip.sopt.com.concertrip.network.response.data.SimpleGenreData
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel


data class GetSearchResponse (
    @SerializedName("data")
    var data : SearchData?
): BaseModel(){
    fun toConcertList() : ArrayList<Concert>{
        val list = ArrayList<Concert>()
        data?.concerts?.forEach {
            list.add(it.toConcert())
        }
        return list
    }

    fun toArtistList() : ArrayList<Artist>{
        val list = ArrayList<Artist>()
        data?.artists?.forEach {
            list.add(it.toArtist())
        }
        return list
    }

    fun toGenreList() : ArrayList<Genre>{
        val list = ArrayList<Genre>()
        data?.genres?.forEach {
            list.add(it.toGenre())
        }
        return list
    }

    override fun toString(): String = data?.toString()?:"null"
}

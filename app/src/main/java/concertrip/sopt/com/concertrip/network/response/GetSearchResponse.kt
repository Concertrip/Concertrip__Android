package concertrip.sopt.com.concertrip.network.response

import com.google.gson.annotations.SerializedName
import concertrip.sopt.com.concertrip.model.Artist
import concertrip.sopt.com.concertrip.model.Concert
import concertrip.sopt.com.concertrip.network.response.data.SimpleArtistData
import concertrip.sopt.com.concertrip.network.response.data.SimpleConcertData
import concertrip.sopt.com.concertrip.network.response.data.SimpleGenreData
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel


data class GetSearchResponse (
    @SerializedName("data")
    var data : SearchData
): BaseModel(){
    fun toConcertList() : ArrayList<Concert>{
        val list = ArrayList<Concert>()
        data.concerts?.forEach {
            list.add(it.toConcert())
        }
        return list
    }

    fun toArtistList() : ArrayList<Artist>{
        val list = ArrayList<Artist>()
        data.artists?.forEach {
            list.add(it.toArtist())
        }
        return list
    }

    override fun toString(): String {
        var result : String =""
        result = result.plus("\nconcerts:[")
        data.concerts?.forEach {
            result = result.plus("\n\t{ $it }")
        }
        result = result.plus("]\nartists:[")
        data.artists?.forEach {
            result = result.plus("\n\t{ $it }")
        }
        result = result.plus("]\ngenres:[")
        data.genres?.forEach {
            result = result.plus("\n\t{ $it }")
        }
        result = result.plus("]\n")

        return result
    }
}
data class SearchData(
    var concerts : List<SimpleConcertData>?,
    var artists : List<SimpleArtistData>?,
    var genres : List<SimpleGenreData>?
)
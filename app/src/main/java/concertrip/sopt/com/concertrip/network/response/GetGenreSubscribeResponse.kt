package concertrip.sopt.com.concertrip.network.response

import com.google.gson.annotations.SerializedName
import concertrip.sopt.com.concertrip.model.Artist
import concertrip.sopt.com.concertrip.network.response.data.ArtistData
import concertrip.sopt.com.concertrip.network.response.data.ConcertData
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel

data class GetGenreSubscribeResponse (
    @SerializedName("data")
    var data : List<ArtistData>
): BaseModel(){

    fun getArtistList() : ArrayList<Artist>{
        val list = ArrayList<Artist>()
        data.forEach {
            list.add(it.toArtist())
        }
        return list
    }
}





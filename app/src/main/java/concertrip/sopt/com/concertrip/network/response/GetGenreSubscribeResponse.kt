package concertrip.sopt.com.concertrip.network.response

import com.google.gson.annotations.SerializedName
import concertrip.sopt.com.concertrip.model.Artist
import concertrip.sopt.com.concertrip.network.response.data.ArtistData
import concertrip.sopt.com.concertrip.network.response.data.ConcertData
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel

data class GetGenreSubscribeResponse (
    @SerializedName("data")
    var data : List<ArtistData?>?
): BaseModel(){

    fun getArtistList() : ArrayList<Artist>{
        val list = ArrayList<Artist>()
        data?.forEach {
            if(it!=null)
                list.add(it.toArtist())
        }
        return list
    }

    override fun toString(): String {
        var result : String = ""
        data?.forEach {
            result=result.plus(it)+","
        }
        return result
    }
}






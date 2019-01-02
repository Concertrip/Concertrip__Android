package concertrip.sopt.com.concertrip.network.response

import android.support.design.bottomappbar.BottomAppBarTopEdgeTreatment
import com.google.gson.annotations.SerializedName
import concertrip.sopt.com.concertrip.model.Artist
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel

data class GetSubscribedResponse(
    @SerializedName("data")
    var data : List<SubscribedData>
):BaseModel(){
    fun toArtistList() : ArrayList<Artist>{
        val list = ArrayList<Artist>()

        data.forEach {
            list.add(it.toArtist())
        }

        return list
    }
}

data class SubscribedData(
    var _id : String,
    var name : String,
    var profileImg : String,
    var subscribe : Boolean
){
    fun toArtist() : Artist {
        val a = Artist(_id)

        a.name = name
        a.profileImg = profileImg
        a.subscribe = subscribe

        return a
    }
}
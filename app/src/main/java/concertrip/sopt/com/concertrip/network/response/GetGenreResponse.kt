package concertrip.sopt.com.concertrip.network.response

import com.google.gson.annotations.SerializedName
import concertrip.sopt.com.concertrip.network.response.data.ArtistData
import concertrip.sopt.com.concertrip.network.response.data.GenreData
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel

data class GetGenreResponse (
    @SerializedName("data")
    var data : GenreData?
): BaseModel(){
    override fun toString(): String =data?.toString()?:"null"
}
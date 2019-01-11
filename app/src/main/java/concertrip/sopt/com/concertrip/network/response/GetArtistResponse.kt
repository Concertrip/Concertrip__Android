package concertrip.sopt.com.concertrip.network.response


import com.google.gson.annotations.SerializedName
import concertrip.sopt.com.concertrip.model.Artist
import concertrip.sopt.com.concertrip.network.response.data.ArtistData
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel

data class GetArtistResponse (
    @SerializedName("data")
    var data : ArtistData?
): BaseModel(){
    override fun toString(): String =data?.toString()?:"null"
}

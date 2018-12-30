package concertrip.sopt.com.concertrip.network.response

import com.google.gson.annotations.SerializedName
import concertrip.sopt.com.concertrip.network.response.data.SimpleArtistData
import concertrip.sopt.com.concertrip.network.response.data.SimpleConcertData
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel


data class GetSearchResponse (
    @SerializedName("data")
    var concertDataList : List<SimpleConcertData>,
    var artistDataList : List<SimpleArtistData>
): BaseModel()




package concertrip.sopt.com.concertrip.network.response

import com.google.gson.annotations.SerializedName


data class GetSearchResponse (
    @SerializedName("data")
    var concertDataList : List<SimpleConcertData>,
    var artistDataList : List<SimpleArtistData>
): BaseModel()

data class SimpleConcertData(
    var _id : String,
    var title : String,
    var location : String,
    var date : String,
    var tag : String
)

data class SimpleArtistData(
    var _id : String,
    var name : String,
    var tag : String
)

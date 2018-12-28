package concertrip.sopt.com.concertrip.network.response

import com.google.gson.annotations.SerializedName

data class GetConcertReponse (
    @SerializedName("data")
    var data : ConcertData
): BaseModel()

data class ConcertData(
    var _id : String,
    var subscribeNum : Int,
    var title : String,
    var profileImg : String,
    var backImg : String,
    var location : String,
    var tag : String,
    var cast : String,
    var date : String,
    var price : String,
    var youtubeUrl : String
)
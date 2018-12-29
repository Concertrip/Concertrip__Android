package concertrip.sopt.com.concertrip.network.response

import com.google.gson.annotations.SerializedName

data class GetConcertReponse (
    @SerializedName("data")
    var data : ConcertData
): BaseModel()

data class ConcertData(
    var _id : String,
    var profileImg : String,
    var backImg : String,
    var name : String,
    var subscribeNum : Int,
    var youtubeUrl : String,
    var location : String,
    var member : List<String>,
    var memberImg : List<String>,
    var date : List<String>,
    var seatName : List<String>,
    var seatPrice : List<String>,

    @SerializedName("precautionList")
    var precautionList : List<ConcertPrecautionList>,

    var eventInfoImg : String,
    var subscribe : Boolean
){

    fun toConcert
}

data class ConcertPrecautionList(
    var code : Int,
    var name : String,
    var img : String
)
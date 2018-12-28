package concertrip.sopt.com.concertrip.network.response


import com.google.gson.annotations.SerializedName

data class GetArtistResponse (
    @SerializedName("data")
    var data : ArtistData
): BaseModel()

 data class ArtistData(
    var _id : String,
    var subscribeNum : Int,
    var name : String,
    var profileImg : String,
    var backImg : String,
    var tag : String,
    var youtubeUrl : String
)

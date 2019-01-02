package concertrip.sopt.com.concertrip.network.response

import com.google.gson.annotations.SerializedName
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel

data class GetSubscribedResponse(
    @SerializedName("data")
    var data : List<SubscribedData>
):BaseModel()

data class SubscribedData(
    var _id : String,
    var name : String,
    var profileImg : String,
    var subscrive : Boolean
)
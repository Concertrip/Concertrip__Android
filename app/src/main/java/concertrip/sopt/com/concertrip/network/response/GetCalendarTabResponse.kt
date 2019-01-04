package concertrip.sopt.com.concertrip.network.response

import com.google.gson.annotations.SerializedName
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel

data class GetCalendarTabResponse (
    @SerializedName("data")
    var data : List<Tab>?
): BaseModel()

data class Tab(
    var _id : String,
    var type : String,
    var name : String
)
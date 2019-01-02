package concertrip.sopt.com.concertrip.network.response

import com.google.gson.annotations.SerializedName
import concertrip.sopt.com.concertrip.network.response.data.SimpleTicketData
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel

data class GetTicketDetailResponse(
    @SerializedName("data")
    var data : SimpleTicketData
) : BaseModel(){
    override fun toString(): String = data.toString()
}
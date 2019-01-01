package concertrip.sopt.com.concertrip.network.response

import com.google.gson.annotations.SerializedName
import concertrip.sopt.com.concertrip.network.response.data.TicketData
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel

data class GetTicketListResponse(
    @SerializedName("data")
    var data: List<TicketData>
) : BaseModel(){
    override fun toString(): String {
        var result = ""
        data.forEach {
            result=result.plus(it)+","
        }
        return result
    }
}
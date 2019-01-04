package concertrip.sopt.com.concertrip.network.response

import com.google.gson.annotations.SerializedName
import concertrip.sopt.com.concertrip.network.response.data.ConcertData
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel

data class GetConcertResponse (
    @SerializedName("data")
    var data : ConcertData?
): BaseModel(){
    override fun toString(): String  = data?.toString()?:"null"
}





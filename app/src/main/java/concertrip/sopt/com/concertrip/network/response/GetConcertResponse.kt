package concertrip.sopt.com.concertrip.network.response

import com.google.gson.annotations.SerializedName
import concertrip.sopt.com.concertrip.model.Artist
import concertrip.sopt.com.concertrip.network.response.data.ConcertData
import concertrip.sopt.com.concertrip.network.response.data.ConcertMemberData
import concertrip.sopt.com.concertrip.network.response.data.ConcertPrecautionData
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel

data class GetConcertReponse (
    @SerializedName("data")
    var data : ConcertData
): BaseModel()





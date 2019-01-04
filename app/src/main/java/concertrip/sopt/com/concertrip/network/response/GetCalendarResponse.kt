package concertrip.sopt.com.concertrip.network.response

import com.google.gson.annotations.SerializedName
import concertrip.sopt.com.concertrip.network.response.data.CalendarDate
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel

data class GetCalendarResponse (
    @SerializedName("data")
    var data : List<CalendarDate>?
):BaseModel()
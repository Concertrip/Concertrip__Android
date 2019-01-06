package concertrip.sopt.com.concertrip.network.response

import com.google.gson.annotations.SerializedName
import concertrip.sopt.com.concertrip.model.CalendarTab
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel
import concertrip.sopt.com.concertrip.utillity.Constants

data class GetCalendarTabResponse (
    @SerializedName("data")
    var data : List<TabData>?
): BaseModel()

data class TabData(
    var _id : String?,
    var type : String?,
    var name : String?
){

    fun toCalendarTag() : CalendarTab= CalendarTab(_id?:"",type?: "all",name?:"")
}
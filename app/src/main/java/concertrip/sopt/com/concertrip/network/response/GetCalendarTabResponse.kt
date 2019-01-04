package concertrip.sopt.com.concertrip.network.response

import android.app.DownloadManager
import com.google.gson.annotations.SerializedName
import concertrip.sopt.com.concertrip.model.CalendarTag
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

    fun toCalendarTag() : CalendarTag= CalendarTag(_id?:"",type?: Constants.REQUEST_ALL,name?:"")
}
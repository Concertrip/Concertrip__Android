package concertrip.sopt.com.concertrip.network.response.data

data class CalendarDate(
    var _id : String,
    var name : String,
    var profileImg : String,
    var date : List<String>,
    var tag : String?,
    var subscribe : Boolean
)
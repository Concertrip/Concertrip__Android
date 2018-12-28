package concertrip.sopt.com.concertrip.network.response

data class GetAlarmReponse(
    var data : ArrayList<AlarmData>
)

data class AlarmData(
    var txt : String,
    var data : String
)

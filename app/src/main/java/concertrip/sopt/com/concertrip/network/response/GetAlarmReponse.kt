package concertrip.sopt.com.concertrip.network.response

data class GetAlarmReponse(
    var data : ArrayList<AlarmData>
):BaseModel()

data class AlarmData(
    var txt : String,
    var data : String
)

package concertrip.sopt.com.concertrip.network.response

data class GetSearchTagResponse(
    var status : String,
    var message : String,
    var data : ArrayList<Any>
)
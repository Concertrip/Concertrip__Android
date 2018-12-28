package concertrip.sopt.com.concertrip.network.response

import org.json.JSONObject

data class MessageResponse(
    var data : JSONObject
):BaseModel()

//    {
//        "status" : 200,
//        "message" : "구독하기/취소 성공",
//        "data" : null
//    }
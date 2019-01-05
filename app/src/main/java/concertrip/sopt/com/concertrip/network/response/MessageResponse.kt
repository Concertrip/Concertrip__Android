package concertrip.sopt.com.concertrip.network.response

import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel
import org.json.JSONObject

data class MessageResponse(
    var data : JSONObject?
): BaseModel(){
    override fun toString(): String =data?.toString()?:message?:"null"
}

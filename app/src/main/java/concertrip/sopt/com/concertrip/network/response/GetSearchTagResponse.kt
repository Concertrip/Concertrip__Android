package concertrip.sopt.com.concertrip.network.response

import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel


data class GetSearchTagResponse(
    var data : ArrayList<Any>?
): BaseModel(){
    override fun toString(): String {
        var result = ""
        data?.forEach {
            result = result.plus(it)+","
        }
        return result
    }
}


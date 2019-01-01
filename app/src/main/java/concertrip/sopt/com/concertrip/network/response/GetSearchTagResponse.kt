package concertrip.sopt.com.concertrip.network.response

import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel


data class GetSearchTagResponse(
    var data : ArrayList<Any>
): BaseModel()


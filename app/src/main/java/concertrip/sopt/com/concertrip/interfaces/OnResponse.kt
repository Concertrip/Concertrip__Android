package concertrip.sopt.com.concertrip.interfaces

import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel


interface OnResponse {
    fun onSuccess(obj : BaseModel,position : Int?)
    fun onFail(status : Int)
}
package concertrip.sopt.com.concertrip.network.response

import com.google.gson.annotations.SerializedName
import concertrip.sopt.com.concertrip.model.Concert
import concertrip.sopt.com.concertrip.network.response.data.ConcertData
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel

data class GetConcertSubscribeResponse (
    @SerializedName("data")
    var data : List<ConcertData>
): BaseModel(){
    fun getConcertList() : ArrayList<Concert>{
        val list = ArrayList<Concert>()
        data.forEach {
            list.add(it.toConcert())
        }
        return list
    }
}





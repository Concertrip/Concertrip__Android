package concertrip.sopt.com.concertrip.network.response

import com.google.gson.annotations.SerializedName
import concertrip.sopt.com.concertrip.model.Ticket
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel

data class GetTicketListResponse(
    @SerializedName("data")
    var data: List<String?>?
) : BaseModel() {


    fun toTicketList(): ArrayList<Ticket> {
        val list = ArrayList<Ticket>()
        data?.forEach {
            if(it!=null)
                list.add(Ticket(it))
        }

        return list
    }

    override fun toString(): String {
        var result = ""
        data?.forEach {
            result = result.plus(it) + ","
        }
        return result
    }
}

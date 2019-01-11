package concertrip.sopt.com.concertrip.network.response

import com.google.gson.annotations.SerializedName
import concertrip.sopt.com.concertrip.model.Ticket
import concertrip.sopt.com.concertrip.network.response.data.TicketData
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel

data class GetTicket_ListResponse(
    @SerializedName("data")
    var data: TicketList?
) : BaseModel() {


    fun toTicketList(): ArrayList<Ticket> {
        val list = ArrayList<Ticket>()
        data?.ticket?.forEach {
            list.add(it.toTicket())
        }

        return list
    }

    override fun toString(): String {
        var result = ""
        data?.ticket?.forEach {
            result = result.plus(it) + ","
        }


        return result
    }
}

data class TicketList(
    var ticket: List<TicketData>?
)
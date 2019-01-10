package concertrip.sopt.com.concertrip.network.response

import com.google.gson.annotations.SerializedName
import concertrip.sopt.com.concertrip.model.Ticket
import concertrip.sopt.com.concertrip.network.response.data.SimpleTicketData
import concertrip.sopt.com.concertrip.network.response.data.TicketData
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel

data class GetTicketListResponse(
    @SerializedName("data")
    var data: TicketList?
) : BaseModel() {


    fun toTicketList(): ArrayList<Ticket> {
        val list = ArrayList<Ticket>()
        data?.ticketcomming?.forEach {
            list.add(it.toTicket())
        }
        data?.ticketcomplete?.forEach {
            list.add(it.toTicket())
        }
        return list
    }

    override fun toString(): String {
        var result = ""
        data?.ticketcomming?.forEach {
            result = result.plus(it) + ","
        }
        data?.ticketcomplete?.forEach {
            result = result.plus(it) + ","
        }

        return result
    }
}

data class TicketList(
    var ticketcomming: List<SimpleTicketData>?,
    var ticketcomplete: List<SimpleTicketData>?
)





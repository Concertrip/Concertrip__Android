package concertrip.sopt.com.concertrip.network.response.data

import concertrip.sopt.com.concertrip.model.Ticket

data class TicketData(var img : String?) {

    fun toTicket() : Ticket = Ticket(img = img)

    override fun toString(): String ="TicketData{\n" +
            "eventId : $img}"
}
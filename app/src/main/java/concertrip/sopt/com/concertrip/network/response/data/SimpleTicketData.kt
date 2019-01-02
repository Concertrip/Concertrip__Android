package concertrip.sopt.com.concertrip.network.response.data

import concertrip.sopt.com.concertrip.model.Ticket

data class SimpleTicketData(var _id : Int,
                            var name: String,
                            var location: String,
                            var date: String,
                            var seat: String,
                            var userIdx: Int,
                            var eventId : String){
    fun toTicket() : Ticket = Ticket(_id = _id, name = name, location = location, date = date,seat = seat, userIdx = userIdx, eventId = eventId)
}

package concertrip.sopt.com.concertrip.network.response.data

import concertrip.sopt.com.concertrip.model.Ticket

data class TicketData(var _id : Int?,
                      var name: String?,
                      var location: String?,
                      var date: String?,
                      var seat: String?,
                      var userIdx: Int?,
                      var eventId : String?) {

    fun toTicket() : Ticket = Ticket(_id = _id?:0, name = name?:"", location = location?:"", date = date,seat = seat?:"", userIdx = userIdx?:0, eventId = eventId?:"")

    override fun toString(): String ="TicketData{\n" +
            "id : $_id \n" +
            "name : $name\n" +
            "location : $location\n" +
            "date : $date\n" +
            "seat : $seat\n" +
            "userIdx : $userIdx\n" +
            "eventId : $eventId}"
}
package concertrip.sopt.com.concertrip.network.response.data

import concertrip.sopt.com.concertrip.model.Ticket
import java.text.SimpleDateFormat
import java.util.*

data class SimpleTicketData(var _id : Int?,
                            var name: String?,
                            var location: String?,
                            var date: String?,
                            var seat: String?,
                            var userIdx: Int?,
                            var eventId : String?){

    private fun convertDate(input: String?) : String?{
        val dayNum : List<String> = listOf("일", "월", "화", "수", "목", "금", "토")

        val convertedDate = StringBuilder()

        if(input != null){
            val dateInfoList = input.split("T")
            val dateFormat = SimpleDateFormat("yyyy-MM-dd").parse(input.split("T")[0])
            val instance : Calendar = Calendar.getInstance()
            instance.setTime(dateFormat)
            val dayNumIdx = instance.get(Calendar.DAY_OF_WEEK)

            val splitedList = dateInfoList[0].split("-")

            convertedDate.append(splitedList[0]+"."+splitedList[1]+"."+splitedList[2]+"("+dayNum[dayNumIdx-1]+")")

            return convertedDate.toString()
        }
        else{
            return convertedDate.toString()
        }
    }
}

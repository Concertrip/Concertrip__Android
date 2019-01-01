package concertrip.sopt.com.concertrip.model

import concertrip.sopt.com.concertrip.interfaces.ListData
import concertrip.sopt.com.concertrip.utillity.Constants

data class Ticket(var _id : Int, var name : String, var location: String,
                  var date: String, var seat:String, var userIdx: Int,
                  var eventId : String) : ListData{

    override fun getType(): Int = Constants.TYPE_TICKET

    override fun getId(): String=0.toString()

    override fun getMainTitle(): String = ""

    override fun getSubTitle(): String =""

    override fun getImageUrl(): String  =""
    override fun isSubscribe(): Boolean? =null




    companion object {

        @JvmStatic fun getDummyArray() : ArrayList<Ticket>{
            val list = ArrayList<Ticket>()
            for(i in 0..10) {
                val a = Ticket(1, "","","","",0,"")
                a.name="더 몬스터 페스티벌"
                a.location = "성남아트센터"
                a.date="2018.12.23 (월)"
                list.add(a)
            }
            return list



        }


    }


}
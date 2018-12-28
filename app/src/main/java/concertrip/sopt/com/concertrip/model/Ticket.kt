package concertrip.sopt.com.concertrip.model

import concertrip.sopt.com.concertrip.interfaces.ListData
import concertrip.sopt.com.concertrip.utillity.Constants

class Ticket : ListData{
    override fun getType(): Int = Constants.TYPE_TICKET

    override fun getIndex(): Int=0

    override fun getMainTitle(): String = ""

    override fun getSubTitle(): String =""

    override fun getImageUrl(): String  =""
    var title : String = ""
    var place : String = ""
    var date : String = ""



    companion object {

        @JvmStatic fun getDummyArray() : ArrayList<Ticket>{
            val list = ArrayList<Ticket>()
            for(i in 0..10) {
                val a = Ticket()
                a.title="더 몬스터 페스티벌"
                a.place = "성남아트센터"
                a.date="2018.12.23 (월)"
                list.add(a)
            }
            return list

        }


    }


}
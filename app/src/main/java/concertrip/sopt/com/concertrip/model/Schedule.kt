package concertrip.sopt.com.concertrip.model

import concertrip.sopt.com.concertrip.interfaces.ListData
import concertrip.sopt.com.concertrip.utillity.Constants
import kotlin.properties.Delegates

data class Schedule(var _id : String,  var name : String,var  profileImg : String,var  date: Int,var tag : String, var subscribe: Boolean) : ListData{

    constructor(date : Int, name : String) :this("",name,"",date,"",false)


    override fun getType(): Int = Constants.TYPE_SCHEDULE

    override fun getId(): String =""

    override fun getMainTitle(): String=name

    override fun getSubTitle(): String =""

    override fun getImageUrl(): String =""

    override fun isSubscribe(): Boolean? =null



    var position : Int by Delegates.notNull() //dayList 상에서 몇번째 위치했는지

    fun toConcert() : Concert {
        val c = Concert("test")

        c.date= listOf(date.toString())
        c.title=name
        return c
    }
    companion object {

        fun getDummy(date: Int): Schedule = Schedule(date, "지코")
        fun getDummyList(): ArrayList<Schedule> {

            val list = ArrayList<Schedule>()
            list.add(Schedule(1, "지코"))
            list.add(Schedule(1, "지코"))

            list.add(Schedule(3, "피오"))
            list.add(Schedule(3, "피오"))


            list.add(Schedule(7, "지코"))
            list.add(Schedule(7, "지코"))

            list.add(Schedule(12, "피오"))


            list.add(Schedule(28, "윤딴딴"))
            list.add(Schedule(28, "윤딴딴"))


            list.add(Schedule(29, "마인드유"))
            list.add(Schedule(29, "마인드유"))
            list.add(Schedule(29, "마인드유"))
            list.add(Schedule(29, "마인드유"))
            list.add(Schedule(29, "마인드유"))
            list.add(Schedule(29, "마인드유"))
            list.add(Schedule(29, "마인드유"))
            return list


        }
        fun getDummyMap() : HashMap<Int, ArrayList<Schedule>>{
            return toMap(getDummyList())
        }
        fun toMap(list : ArrayList<Schedule>) : HashMap<Int, ArrayList<Schedule>>{
            val  map  = HashMap<Int, ArrayList<Schedule>>()
//            list.forEach {
//                if(!map.containsKey(it.date))
//                    map[it.date]= ArrayList<Schedule>()
//                map[it.date]?.add(it)
//            }
            return map
        }
    }

}
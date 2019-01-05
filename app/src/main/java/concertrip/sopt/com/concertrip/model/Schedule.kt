package concertrip.sopt.com.concertrip.model

import concertrip.sopt.com.concertrip.interfaces.ListData
import concertrip.sopt.com.concertrip.utillity.Constants
import kotlin.properties.Delegates

data class Schedule(var _id : String,  var tabId : String, var name : String,var  profileImg : String,var  date: Int,var tag : String, var subscribe: Boolean) : ListData{

    constructor(date : Int, name : String) :this("","",name,"",date,"",false)


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

}
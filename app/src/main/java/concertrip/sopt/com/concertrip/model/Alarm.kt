package concertrip.sopt.com.concertrip.model


import concertrip.sopt.com.concertrip.interfaces.ListData
import concertrip.sopt.com.concertrip.utillity.Constants

class Alarm(var title:String, var body:String, var imgUrl:String)  : ListData{


    override fun getType(): Int = Constants.TYPE_ALARM

    override fun getId(): String =""

    override fun getMainTitle(): String =title

    override fun getSubTitle(): String  =body

    override fun getImageUrl(): String =imgUrl
    override fun isSubscribe(): Boolean? =null

}
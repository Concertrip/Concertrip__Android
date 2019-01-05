package concertrip.sopt.com.concertrip.model

import concertrip.sopt.com.concertrip.interfaces.ListData
import concertrip.sopt.com.concertrip.utillity.Constants

data class Caution(
    var img : String,
    var code : Int,
    var content : String
) : ListData {


    override fun getType(): Int = Constants.TYPE_CAUTION

    override fun getId(): String = 0.toString()

    override fun getMainTitle(): String = content

    override fun getSubTitle(): String =""

    override fun getImageUrl(): String = img
    override fun isSubscribe(): Boolean? =null



}
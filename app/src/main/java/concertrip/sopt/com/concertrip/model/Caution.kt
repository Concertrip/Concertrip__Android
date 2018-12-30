package concertrip.sopt.com.concertrip.model

import concertrip.sopt.com.concertrip.interfaces.ListData
import concertrip.sopt.com.concertrip.utillity.Constants

data class Caution(var img : String, var code : Int, var content : String) : ListData {



    override fun getType(): Int = Constants.TYPE_CAUTION

    override fun getId(): String = 0.toString()

    override fun getMainTitle(): String = content

    override fun getSubTitle(): String =""

    override fun getImageUrl(): String = img
 //   var content : String = ""
 //   var img : String = ""
 //   var code : Int = 0



    companion object {

        @JvmStatic fun getDummyArray() : ArrayList<Caution>{
            val list = ArrayList<Caution>()
            for(i in 0..5) {
                val a = Caution("",0,"")
                a.content="만 7세 이상"
                a.code=i
                a.img="https://img.huffingtonpost.com/asset/5ba482b82400003100546bc3.jpeg"
                list.add(a)
            }
            return list

        }

    }}
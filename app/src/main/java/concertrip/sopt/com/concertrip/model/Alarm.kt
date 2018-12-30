package concertrip.sopt.com.concertrip.model


import concertrip.sopt.com.concertrip.interfaces.ListData
import concertrip.sopt.com.concertrip.utillity.Constants

class Alarm(var title:String, var date:String, var imgUrl:String)  : ListData{


    override fun getType(): Int = Constants.TYPE_ALARM

    override fun getId(): String =""

    override fun getMainTitle(): String =title

    override fun getSubTitle(): String  =date

    override fun getImageUrl(): String =imgUrl
    override fun isSubscribe(): Boolean? =null


    companion object {
        fun getDummyArray() : ArrayList<Alarm>{
            val list = ArrayList<Alarm>()
            list.add(Alarm.getDummy(1.toString()))
            list.add(Alarm.getDummy_2(2.toString()))
            list.add(Alarm.getDummy_3(3.toString()))
            return list

        }

        fun getDummy(_id : String)= Alarm("힙합", "https://search.pstatic.net/common?type=a&size=120x150&quality=95&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fpeople%2Fportrait%2F201801%2F20180108113919887.jpg", "2018-12-23")

        fun getDummy_2(_id : String)= Alarm("보이그룹", "http://blogfiles.naver.net/20150115_278/jimin1226_1421248926310a8IkG_JPEG/vsbspvs.jpg", "2018-12-17")

        fun getDummy_3(_id : String)= Alarm("발라드", "http://imgnews.naver.net/image/213/2016/07/20/20160720_1468977905_09715900_1_99_20160720103907.jpg", "2018-12-29")

    }
}
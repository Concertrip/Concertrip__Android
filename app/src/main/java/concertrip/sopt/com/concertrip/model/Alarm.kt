package concertrip.sopt.com.concertrip.model


import concertrip.sopt.com.concertrip.interfaces.ListData
import concertrip.sopt.com.concertrip.utillity.Constants

class Alarm  : ListData{

    var title : String=""
    var date : String=""
    var imgUrl : String=""
    override fun getType(): Int = Constants.TYPE_ALARM

    override fun getId(): String =""

    override fun getMainTitle(): String =title

    override fun getSubTitle(): String  =date

    override fun getImageUrl(): String =imgUrl


    companion object {
        fun getDummyArray() : ArrayList<Alarm>{
            val list = ArrayList<Alarm>()
            for(i in 0..5) {
                val a = Alarm()
                a.title="힙합 페스티발"
                a.imgUrl="https://search.pstatic.net/common?type=a&size=120x150&quality=95&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fpeople%2Fportrait%2F201801%2F20180108113919887.jpg"
                a.date="2018-12-23"
                list.add(a)
            }
            return list

        }

    }
}
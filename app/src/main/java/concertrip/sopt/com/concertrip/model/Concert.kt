package concertrip.sopt.com.concertrip.model

import concertrip.sopt.com.concertrip.interfaces.ListData
import concertrip.sopt.com.concertrip.utillity.Constants

class Concert : ListData {

    var idx: Int=0
    var title : String = ""
    var profileImg : String =""
    var backImg : String =""
    var location : String =""
    var station : String =""
    var genre : String =""
    var cast : String =""
    var date : String =""
    var price : String =""
    var youtubeUrl : String =""

    fun getTag() : String ="#$genre #$genre"



    override fun getType(): Int = Constants.TYPE_CONCERT

    override fun getIndex(): Int = idx

    override fun getMainTitle(): String =title

    override fun getSubTitle(): String =getTag()

    override fun getImageUrl(): String =profileImg


    companion object {
        fun getDummyArray() : ArrayList<Concert>{
            val list = ArrayList<Concert>()
            for(i in 0..5) {
                val c = Concert()
                c.title="힙합 페스티발"
                c.idx=i
                c.genre="#힙합"
                c.youtubeUrl=""
                c.backImg=""
                c.profileImg="https://search.pstatic.net/common?type=a&size=120x150&quality=95&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fpeople%2Fportrait%2F201801%2F20180108113919887.jpg"
                c.cast="지코"
                c.date="2018-12-23"
                list.add(c)
            }
            return list

        }
    }
}
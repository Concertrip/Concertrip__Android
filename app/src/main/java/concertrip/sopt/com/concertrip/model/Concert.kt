package concertrip.sopt.com.concertrip.model

import concertrip.sopt.com.concertrip.interfaces.ListData
import concertrip.sopt.com.concertrip.utillity.Constants

data class Concert(

    var _id : String,
    var title : String,
    var profileImg : String,
    var backImg : String?,
    var location : String,
    var station: String?,
    var genre: String?,
    var cast : String?, // dont need it
    var date : List<String>?,

    var seatList : List<Seat>?,

    var precaution : List<Caution>?,
    var eventInfoImg : String?,
    var youtubeUrl : String?,
    var subscribeNum : Int?,
    var artistList : List<Artist>?,
    var subscribe: Boolean?

) : ListData {

    var tag  : String? = null


    constructor(_id : String,name : String, profileImg: String, date : List<String>,subscribe: Boolean?, tag : String) : this( _id, name, genre = "", youtubeUrl = "",backImg = "", eventInfoImg = "",
        profileImg = profileImg,cast = "", date = date,artistList = ArrayList<Artist>(),location = "",station = "",
        seatList = ArrayList<Seat>(), precaution=ArrayList<Caution>(), subscribeNum = 0, subscribe = subscribe) {
        this.tag=tag
    }

    constructor(_id : String) : this(_id = _id, title = "", genre = "", youtubeUrl = "",backImg = "", eventInfoImg = "",
        profileImg = "",cast = "", date = ArrayList<String>(),artistList = ArrayList<Artist>(),location = "",station = "",
        seatList = ArrayList<Seat>(), precaution=ArrayList<Caution>(), subscribeNum = 0, subscribe = false)



    private fun makeTag() : String{
      var result ="#$genre"
        date?.forEach {
            result = result.plus(" #$it")
        }
        return result

    }
    override fun getType(): Int = Constants.TYPE_CONCERT

    override fun getId(): String = _id

    override fun getMainTitle(): String =title

    override fun getSubTitle(): String{
        tag?.let {
            return it
        }
        return  makeTag()

    }

    override fun getImageUrl(): String =profileImg
    override fun isSubscribe(): Boolean? =subscribe


}
package concertrip.sopt.com.concertrip.model

import concertrip.sopt.com.concertrip.interfaces.ListData
import concertrip.sopt.com.concertrip.utillity.Constants

open class Artist(
    var _id: String,
    var profileImg: String,
    var backImg: String?,
    var name: String,
    var genre: String?,
    var youtubeUrl: String?,
    var subscribeNum: Int,
    var memberList : List<Artist>?,
    var concertList: List<Concert>?,
    var subscribe: Boolean
) : ListData {

    var tag: String? = null


    constructor(
        _id: String,
        profileImg: String,
        backImg: String?,
        name: String,
        genre: String,
        youtubeUrl: String,
        subscribeNum: Int,
        concertList: List<Concert>
    )
            : this(_id, profileImg, null, name, genre, youtubeUrl, subscribeNum, listOf(), listOf(),false)

    constructor(_id: String, name: String, profileImg: String) : this(
        _id,
        profileImg,
        null,
        name,
        null,
        null,
        0,
        listOf(),
        listOf(),
        false
    )

    constructor(_id: String) : this(_id, "", "", "", "", "", 0, listOf(), listOf(),false)


    private fun makeTag(): String = "#$genre #$genre"
    override fun getType(): Int = Constants.TYPE_ARTIST
    override fun getId(): String = _id
    override fun getMainTitle(): String = name
    override fun getSubTitle(): String {
        tag?.let {
            return it
        }
        return makeTag()
    }

    override fun getImageUrl(): String = profileImg
    override fun isSubscribe(): Boolean? = subscribe



}
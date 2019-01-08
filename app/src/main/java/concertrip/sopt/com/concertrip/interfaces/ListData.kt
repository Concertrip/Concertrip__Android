package concertrip.sopt.com.concertrip.interfaces


interface ListData {
    fun getType() : Int
    fun getId() : String
    fun getMainTitle(): String
    fun getSubTitle(): String
    fun getImageUrl(): String

    fun isSubscribe(): Boolean?
}
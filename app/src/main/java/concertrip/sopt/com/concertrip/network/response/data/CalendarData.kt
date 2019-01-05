package concertrip.sopt.com.concertrip.network.response.data

data class CalendarData(
    var _id : String?,
    var name : String?,
    var profileImg : String?,
    var date : List<String>?,
    var tag : String?,
    var subscribe : Boolean?
){

    override fun toString(): String {
        var result =
        "ArtistData{\n" +
                "_id : $_id\n" +
                "name : $name\n" +
                "profileImg : $profileImg\n"+
                "date : ["
        date?.forEach {
            result=result.plus("$it,")
        }

        result = result +  "] " +
                "tag : $tag\n" +
                "subscribe : $subscribe"+
                "}"

        return result
    }
}

package concertrip.sopt.com.concertrip.network.response.data

data class SearchData(
    var concerts : List<SimpleConcertData>?,
    var artists : List<SimpleArtistData>?,
    var genres : List<SimpleGenreData>?
){
    override fun toString(): String {
        var result : String =""
        result = result.plus("\nconcerts:[")
        concerts?.forEach {
            result = result.plus("\n\t{ $it }")
        }
        result = result.plus("]\nartists:[")
        artists?.forEach {
            result = result.plus("\n\t{ $it }")
        }
        result = result.plus("]\ngenres:[")
        genres?.forEach {
            result = result.plus("\n\t{ $it }")
        }
        result = result.plus("]\n")

        return result
    }
}
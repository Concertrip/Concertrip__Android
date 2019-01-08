package concertrip.sopt.com.concertrip.network.response

import concertrip.sopt.com.concertrip.model.Genre
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel


data class GetGenreSearchResponse(
    var data : List<GenreData>
):BaseModel(){
    fun toGenreList() : ArrayList<Genre>{
        val list = ArrayList<Genre>()
        data?.forEach {
            list.add(it.toGenre())
        }
        return list
    }
}

data class GenreData(
    var _id : String,
    var name : String,
    var profileImg : String,
    var subscribe : Boolean
){
    fun toGenre() : Genre{
        val g = Genre(_id)

        g.name = this.name
        g.profileImg = this.profileImg
        g.subscribe = this.subscribe

        return g
    }
}
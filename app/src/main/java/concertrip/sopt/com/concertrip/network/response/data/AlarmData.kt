package concertrip.sopt.com.concertrip.network.response.data

import concertrip.sopt.com.concertrip.model.Alarm

data class AlarmData(
    var id : Int?,
    var userIdx : Int?,
    var title : String?,
    var body : String?,
    var createdAt : String?,
    var noticeImg : String?
){
//    constructor(id: Int): this(id,"","","", "")

    fun toAlarm() : Alarm = Alarm(title?:"",body?:"",noticeImg?:"")

    override fun toString(): String ="AlarmListData{\n" +
            "id : ${id.toString()}\n" +
            "userIdx : ${userIdx.toString()}\n" +
            "title : ${title.toString()}\n" +
            "body : ${body.toString()}\n" +
            "createdAt : ${createdAt.toString()}\n" +
            "}"
}

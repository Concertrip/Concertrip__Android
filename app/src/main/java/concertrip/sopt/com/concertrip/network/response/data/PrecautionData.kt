package concertrip.sopt.com.concertrip.network.response.data

import concertrip.sopt.com.concertrip.model.Caution

data class PrecautionData(
    var code : Int,
    var name : String,
    var img : String
){
    fun toCaution() : Caution = Caution(img = img, content = name, code = code)

    companion object {
        fun getDummyList() : List<PrecautionData>{
            val list = listOf(PrecautionData(1,"만 7세 이상","https://img.huffingtonpost.com/asset/5ba482b82400003100546bc3.jpeg"),
                PrecautionData(2,"음식물 반입 금지","https://img.huffingtonpost.com/asset/5ba482b82400003100546bc3.jpeg"),
                PrecautionData(3,"재입장 불가","https://img.huffingtonpost.com/asset/5ba482b82400003100546bc3.jpeg"))
            return list
        }
    }
}
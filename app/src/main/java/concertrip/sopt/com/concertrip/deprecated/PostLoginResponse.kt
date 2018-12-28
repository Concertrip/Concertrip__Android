package concertrip.sopt.com.concertrip.deprecated

data class PostLoginResponse(
    val message: String,
    val name: String,
    val u_idx : Int,
    val phone : String,
    val bio : String,
    val id : String,
    val photo : String,
    val token : String
)
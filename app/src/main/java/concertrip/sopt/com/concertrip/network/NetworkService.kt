package concertrip.sopt.com.concertrip.network

import com.google.gson.JsonObject
import concertrip.sopt.com.concertrip.network.response.MessageResponse
import concertrip.sopt.com.concertrip.network.response.PostIdCheckResponse
import concertrip.sopt.com.concertrip.network.response.PostLoginResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface NetworkService {


    //POST 타입 JSONObject로 받을때 테스트 //테스트
    @Headers("Content-Type:application/json")
    @POST("/auth/register/check")
    fun postIdCheck(
        @Body() body: JsonObject
    ): Call<PostIdCheckResponse>

    //POST 타입 JSONObject로 받을때 테스트 //테스트
    @Headers("Content-Type:application/json")
    @POST("/auth/login")
    fun postLogin(
        @Body() body: JsonObject
    ): Call<PostLoginResponse>


    //POST 타입 (JsonObject로 받을때)
    @POST("")
    fun ex_posttype(
        @Header("") content_type: String,
        @Body() body: JsonObject
    ) //:Call<>


    //POST 타입 <파일로 받을때)
    @Multipart
    @POST("")
    fun ex_posttype2(
        @Header("") token: String,
        @Part("") title: RequestBody,
        @Part("") contents: RequestBody,
        @Part photo: MultipartBody.Part?
    ) //:Call<>


    //GET 타입
    @GET("/auth/login")
    @Headers("Content-Type:application/json")
    fun ex_gettype(
//        @Header("") content_type: String,
        @Query("") offset: Int,
        @Query("") limit: Int
    ) //: Call<>


    //----------------------------------------
    //*하트(구독)*
    //아티스트 구독하기/취소
    @POST("/artists/{artistIdx}/heart")
    fun PostHeartArtirst(
        @Header("Content-Type") content_type: String,
        @Body() body : JsonObject
    ):Call<MessageResponse>

    //장르 구독하기/취소
    @POST("/genre/{genreIdx}/heart")
    fun PostHeartGenre(
        @Header("Content-Type") content_type: String,
        @Body() body : JsonObject
    ):Call<MessageResponse>

    //*종(알림받기)*
    //콘서트 일정 알림받기/취소
    @POST("/concert/{concertIdx}/bell")
    fun Postbell(
        @Header("Content-Type") content_type: String,
        @Body() body : JsonObject
    )
}
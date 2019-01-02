package concertrip.sopt.com.concertrip.network

import com.google.gson.JsonObject
import concertrip.sopt.com.concertrip.network.response.GetArtistResponse
import concertrip.sopt.com.concertrip.network.response.*
import concertrip.sopt.com.concertrip.deprecated.PostIdCheckResponse
import concertrip.sopt.com.concertrip.deprecated.PostLoginResponse
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
    @POST("/api/subscribe/artist")
    @Headers("Content-Type:application/json")
    fun postSubScribeArtist(
//        @Header("token") token: String,
        @Body() body : JsonObject
    ):Call<MessageResponse>

    //장르 구독하기/취소
    @POST("/api/subscribe/genre")
    @Headers("Content-Type:application/json")
    fun postSubscribeGenre(
//        @Header("token") token: String,
        @Body() body : JsonObject
    ):Call<MessageResponse>
    //---------------------------------------
    //*종(알림받기)*
    //콘서트 일정 알림받기/취소
    @POST("/api/subscribe/concert")
    @Headers("Content-Type:application/json")
    fun postSubscribeConcert(
//        @Header("token") token: String,
        @Body() body : JsonObject
    ):Call<MessageResponse>

    //-----------------------------------------
    //*이벤트
    //이벤트 조회
    @GET("/api/event/detail")
    @Headers("Content-Type:application/json")
    fun getEvent(
        @Header("Authorization") token : Int,
        @Query("id") id : String
    ) : Call<GetConcertResponse>
    //-----------------------------------------
    //*아티스트
    //아티스트 상세 페이지
    @GET("/api/artist/detail")
    @Headers("Content-Type:application/json")
    fun getArtist(
        @Header("Authorization") token : String,
        @Query("id") id : String
    ):Call<GetArtistResponse>
    //-----------------------------------------
    //*장르
    // 장르 상세 페이지
    @GET("/api/genre/detail")
    @Headers("Content-Type:application/json")
    fun getGenre(
        @Header("Authorization") token : Int,
        @Query("id") id : String
    ):Call<GetGenreResponse>
    //-----------------------------------------
    //*검색
    //콘서트/아티스트 검색
    @GET("/api/search")
    @Headers("Content-Type:application/json")
    fun getSearch(
        @Header("Authorization") token : Int,
        @Query("tag") tag: String
    ):Call<GetSearchResponse>

    //------------------------------------------
    //*내 티켓 리스트
    @GET("/api/ticket")
    @Headers("Content-Type:application/json")
    fun getTicketList(
        @Header("token") token : Int
    ):Call<GetTicketListResponse>
}
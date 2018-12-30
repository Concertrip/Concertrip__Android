package concertrip.sopt.com.concertrip.network

import com.google.gson.JsonObject
import concertrip.sopt.com.concertrip.network.response.GetArtistResponse
import concertrip.sopt.com.concertrip.network.response.GetConcertReponse
import concertrip.sopt.com.concertrip.network.response.*
import concertrip.sopt.com.concertrip.deprecated.PostIdCheckResponse
import concertrip.sopt.com.concertrip.deprecated.PostLoginResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface NetworkService {


    // ArtistActivity, ExplorerFragment
    @Headers("Content-Type:application/json")
    @GET("/api/artist/detail")
    fun getArtistData(
        //@Header("token") token : String, // 위에 Headers랑 겹치지 않나?
        //@Path("artistId") artistId : String
        @Query("artistId") artistId : String
    ) : Call<GetArtistResponse>

    // ConcertActivity, ExplorerFragment
    @Headers("Content-Type:application/json")
    @GET("/api/event/detail")
    fun getEventData(
        //@Path("eventsId") eventsId : String
        @Query("eventsId") eventsId : String
    ) : Call<GetConcertReponse>

    // SearchFragment
    @Headers("Content-Type:application/json")
    @GET("/search/{tag}")
    fun getSearchData(
        @Path("tag") tag : Int
    ) : Call<GetSearchResponse>

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
    @GET("/artists/{artistIdx}/heart")
    fun GetHeartArtirst(
        @Header("Content-Type") content_type: String,
        @Path("artistIdx") artistIdx : Int
    ):Call<MessageResponse>

    //장르 구독하기/취소
    @GET("genre/{genreIdx}/heart")
    fun GetHeartGenre(
        @Header("Content-Type") content_type: String,
        @Path("genreIdx") genreIdx : Int
    ):Call<MessageResponse>
    //---------------------------------------
    //*종(알림받기)*
    //콘서트 일정 알림받기/취소
    @GET("/concert/{concertIdx}/bell")
    fun Getbell(
        @Header("Content-Type") content_type: String,
        @Path("concertIdx") concertIdx : Int
    ):Call<MessageResponse>

    //-----------------------------------------
    //*이벤트
    //이벤트 조회
    @GET("/events/{eventsId}")
    fun GetEvent(
        @Header("Content-Type") content_type: String,
        @Path("eventsId") eventId : Int
    )
    //-----------------------------------------
    //*아티스트
    //아티스트 조회
    @GET("/artists/{artistId}")
    fun GetArtist(
        @Header("Content-Type") content_type: String,
        @Path("artistId") artistId : Int
    ):Call<GetArtistResponse>
    //-----------------------------------------
    //*검색
    //콘서트/아티스트 검색
    @GET("/search")
    fun GetSearch(
        @Header("Content-Type") content_type: String,
        @Query("tag") tag: Int
    ):Call<GetSearchTagResponse>
}
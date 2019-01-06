package concertrip.sopt.com.concertrip.network

import com.google.gson.JsonObject
import concertrip.sopt.com.concertrip.network.response.GetArtistResponse
import concertrip.sopt.com.concertrip.network.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface NetworkService {
    
    //----------------------------------------
    //*하트(구독)*
    //아티스트 구독하기/취소
    @POST("/api/subscribe/artist")
    @Headers("Content-Type:application/json")
    fun postSubScribeArtist(
        @Header("Authorization") token: Int,
        @Body() body : JsonObject
    ):Call<MessageResponse>

    //장르 구독하기/취소
    @POST("/api/subscribe/genre")
    @Headers("Content-Type:application/json")
    fun postSubscribeGenre(
        @Header("Authorization") token: Int,
        @Body() body : JsonObject
    ):Call<MessageResponse>
    //---------------------------------------
    //*종(알림받기)*
    //콘서트 일정 알림받기/취소
    @POST("/api/subscribe/event")
    @Headers("Content-Type:application/json")
    fun postSubscribeConcert(
        @Header("Authorization") token: Int,
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
        @Header("Authorization") token : Int,
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
        @Header("Authorization") token : Int
    ):Call<GetTicketListResponse>

    //------------------------------------------
    //*구독 아티스트 리스트
    @GET("/api/subscribe/artist")
    @Headers("Content-Type:application/json")
    fun getSubscribedArtist(
        @Header("Authorization") token : Int
    ):Call<GetSubscribedResponse>

    //*구독 이벤트 리스트
    @GET("/api/subscribe/event")
    @Headers("Content-Type:application/json")
    fun getSubscribedEvent(
        @Header("Authorization") token : Int
    ):Call<GetSubscribedResponse>

    //*구독 장르 리스트
    @GET("/api/subscribe/genre")
    @Headers("Content-Type:application/json")
    fun getSubscribedGenre(
        @Header("Authorization") token : Int
    ):Call<GetSubscribedResponse>
  
    //*티켓 상세정보
    @GET("/api/ticket/detail")
    @Headers("Content-Type:application/json")
    fun getTicketDetail(
        @Header("Authorization") token: Int,
        @Query("id") id: Int
    ):Call<GetTicketDetailResponse>



    //*캘린더 탭 리스트
    @GET("/api/calendar/tab")
    @Headers("Content-Type:application/json")
    fun getCalendarTabList(
        @Header("Authorization") token: Int
    ):Call<GetCalendarTabResponse>

    //*캘린더 리스트
    @GET("/api/calendar/type")
    @Headers("Content-Type:application/json")
    fun getCalendarList(
        @Header("Authorization") token: Int,
        @Query("type") type: String,
        @Query("id") id: String?,
        @Query("year") year: String,
        @Query("month") month: String
    ):Call<GetCalendarResponse>

    //*캘린더 데이 리스트
    @GET("/api/calendar/day")
    @Headers("Content-Type:application/json")
    fun getCalendarDayList(
        @Header("Authorization") token: Int,
        @Query("type") type: String,
        @Query("id") id: String?,
        @Query("year") year: String,
        @Query("month") month: String,
        @Query("day") day: String
    ):Call<GetCalendarResponse>
}
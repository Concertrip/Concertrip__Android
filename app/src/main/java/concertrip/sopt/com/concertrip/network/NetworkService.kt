package concertrip.sopt.com.concertrip.network

import com.google.gson.JsonObject
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
    ) :Call<PostIdCheckResponse>

    //POST 타입 JSONObject로 받을때 테스트 //테스트
    @Headers("Content-Type:application/json")
    @POST("/auth/login")
    fun postLogin(
        @Body() body: JsonObject
    ) :Call<PostLoginResponse>

    //POST 타입 (JsonObject로 받을때)
    @POST("")
    fun ex_posttype(
        @Header("") content_type : String,
        @Body() body:JsonObject
    ) //:Call<>

    //POST 타입 <파일로 받을때)
    @Multipart
    @POST("")
    fun ex_posttype2(
        @Header("") token : String,
        @Part("") title : RequestBody,
        @Part("") contents : RequestBody,
        @Part photo: MultipartBody.Part?
    ) //:Call<>


    //GET 타입
    @GET
    fun ex_gettype(
        @Header("") content_type: String,
        @Query("") offset : Int,
        @Query("") limit : Int
    ) //: Call<>

}
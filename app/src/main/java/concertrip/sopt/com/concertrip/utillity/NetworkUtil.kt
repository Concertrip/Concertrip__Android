package concertrip.sopt.com.concertrip.utillity

import android.util.Log
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import concertrip.sopt.com.concertrip.interfaces.OnResponse
import concertrip.sopt.com.concertrip.network.NetworkService
import concertrip.sopt.com.concertrip.network.USGS_REQUEST_URL
import concertrip.sopt.com.concertrip.network.response.*
import concertrip.sopt.com.concertrip.network.response.data.ConcertData
import concertrip.sopt.com.concertrip.network.response.GetSearchResponse
import concertrip.sopt.com.concertrip.network.response.GetTicketListResponse
import concertrip.sopt.com.concertrip.network.response.MessageResponse
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.TYPE_ARTIST
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.TYPE_CONCERT
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.TYPE_DAY
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.TYPE_MONTH
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.TYPE_THEME
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkUtil {

    companion object {


        private const val LOG_SUBSCRIBE_ARTIST = "/api/subscribe/artist"
        fun subscribeArtist(networkService: NetworkService, listener: OnResponse?, _id: String) =
            subscribeArtist(networkService, listener, _id, null)

        fun subscribeArtist(networkService: NetworkService, listener: OnResponse?, _id: String, position: Int?) {
            val jsonObject = JSONObject()
            jsonObject.put(USGS_REQUEST_URL.JSON_ARTIST_ID, _id)
            val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

            Log.d(Constants.LOG_NETWORK, "$LOG_SUBSCRIBE_ARTIST, POST :$gsonObject")
            val subscribeArtist: Call<MessageResponse> =
                networkService.postSubScribeArtist(1, gsonObject)
            subscribeArtist.enqueue(object : Callback<MessageResponse> {
                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    Log.e(Constants.LOG_NETWORK, t.toString())
                    listener?.onFail(Secret.NETWORK_UNKNOWN)
                }

                //통신 성공 시 수행되는 메소드
                override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                    Log.d(Constants.LOG_NETWORK, response.errorBody()?.string() ?: response.message())
                    if (response.isSuccessful && response.body()?.status== Secret.NETWORK_SUCCESS) {
                        Log.d(Constants.LOG_NETWORK, "$LOG_SUBSCRIBE_ARTIST :${response.body()}")
                        listener?.onSuccess(response.body() as BaseModel, position)
                    } else {
                        Log.d(Constants.LOG_NETWORK, "$LOG_SUBSCRIBE_ARTIST : fail ${response.body()?.message}")
                        listener?.onFail(response.body()?.status?: Secret.NETWORK_UNKNOWN)
                    }
                }
            })
        }


        private const val LOG_SUBSCRIBE_GENRE = "/api/subscribe/genre"
        fun subscribeGenre(networkService: NetworkService, listener: OnResponse?, _id: String) =
            subscribeGenre(networkService, listener, _id, null)

        fun subscribeGenre(networkService: NetworkService, listener: OnResponse?, _id: String, position: Int?) {
            val jsonObject = JSONObject()
            jsonObject.put(USGS_REQUEST_URL.JSON_GENRE_ID, _id)
            val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

            Log.d(Constants.LOG_NETWORK, "$LOG_SUBSCRIBE_GENRE, POST :$gsonObject")

            val subscribeGenre: Call<MessageResponse> =
                networkService.postSubscribeGenre(1, gsonObject)
            subscribeGenre.enqueue(object : Callback<MessageResponse> {

                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    Log.e(Constants.LOG_NETWORK, t.toString())
                    listener?.onFail(Secret.NETWORK_UNKNOWN)
                }

                //통신 성공 시 수행되는 메소드
                override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                    Log.d(Constants.LOG_NETWORK, response.errorBody()?.string() ?: response.message())

                    if (response.isSuccessful && response.body()?.status==Secret.NETWORK_SUCCESS) {
                        Log.d(Constants.LOG_NETWORK, "$LOG_SUBSCRIBE_GENRE :${response.body()}")
                        listener?.onSuccess(response.body() as BaseModel, position)
                    } else {
                        Log.d(Constants.LOG_NETWORK, "$LOG_SUBSCRIBE_GENRE : fail  ${response.body()?.message}")
                        listener?.onFail(response.body()?.status?:Secret.NETWORK_UNKNOWN)
                    }
                }
            })
        }


        private const val LOG_SUBSCRIBE_CONCERT = "/api/subscribe/concert"
        fun subscribeConcert(networkService: NetworkService, listener: OnResponse?, _id: String) =
            subscribeConcert(networkService, listener, _id, null)

        fun subscribeConcert(networkService: NetworkService, listener: OnResponse?, _id: String, position: Int?) {
            val jsonObject = JSONObject()
            jsonObject.put(USGS_REQUEST_URL.JSON_CONCERT_ID, _id)
            val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

            Log.d(Constants.LOG_NETWORK, "$LOG_SUBSCRIBE_CONCERT, POST :$gsonObject")

            val subscribeConcert: Call<MessageResponse> =
                networkService.postSubscribeConcert(1, gsonObject)
            subscribeConcert.enqueue(object : Callback<MessageResponse> {

                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    Log.e(Constants.LOG_NETWORK, t.toString())
                    listener?.onFail(Secret.NETWORK_UNKNOWN)
                }

                //통신 성공 시 수행되는 메소드
                override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                    Log.d(Constants.LOG_NETWORK, response.errorBody()?.string() ?: response.message())

                    if (response.isSuccessful && response.body()?.status==Secret.NETWORK_SUCCESS) {
                        Log.d(Constants.LOG_NETWORK, "$LOG_SUBSCRIBE_CONCERT :${response.body()}")
                        listener?.onSuccess(response.body() as BaseModel, position)
                    } else {
                        Log.d(Constants.LOG_NETWORK, "$LOG_SUBSCRIBE_CONCERT: fail  ${response.body()?.message}")
                        listener?.onFail(response.body()?.status?:Secret.NETWORK_UNKNOWN)
                    }
                }
            })
        }


        private const val LOG_SEARCH = "/api/search"
        fun search(networkService: NetworkService, listener: OnResponse?, tag: String) =
            search(networkService, listener, tag, null)

        fun search(networkService: NetworkService, listener: OnResponse?, tag: String, position: Int?) {

            Log.d(Constants.LOG_NETWORK, "$LOG_SEARCH, GET ? tag=$tag")

            val search: Call<GetSearchResponse> =
                networkService.getSearch(1, tag)
            search.enqueue(object : Callback<GetSearchResponse> {

                override fun onFailure(call: Call<GetSearchResponse>, t: Throwable) {
                    Log.e(Constants.LOG_NETWORK, t.toString())
                    listener?.onFail(Secret.NETWORK_UNKNOWN)
                }

                //통신 성공 시 수행되는 메소드
                override fun onResponse(call: Call<GetSearchResponse>, response: Response<GetSearchResponse>) {
                    Log.d(Constants.LOG_NETWORK, response.errorBody()?.string() ?: response.message())

                    if (response.isSuccessful) {
                        Log.d(Constants.LOG_NETWORK, "$LOG_SEARCH :${response.body()?.status}")
                        response.body()?.let {
                            if (it.status == Secret.NETWORK_SUCCESS) {
                                Log.d(Constants.LOG_NETWORK, "$LOG_SEARCH :${response.body().toString()}")
                                listener?.onSuccess(response.body() as BaseModel, position)
                            } else{
                                Log.d(Constants.LOG_NETWORK, "$LOG_SEARCH: fail  ${response.body()?.message}")
                                listener?.onFail(response.body()?.status ?: Secret.NETWORK_UNKNOWN)
                            }
                        }

                    } else {
                        listener?.onFail(Secret.NETWORK_UNKNOWN)

                    }
                }
            })
        }

        fun getTicketList(networkService: NetworkService, listener: OnResponse?, _id: String) {
            val getTicketListResponse: Call<GetTicketListResponse> = networkService.getTicketList(1) // _id

            getTicketListResponse.enqueue(object : Callback<GetTicketListResponse> {

                override fun onFailure(call: Call<GetTicketListResponse>, t: Throwable) {
                    Log.e(Constants.LOG_NETWORK, t.toString())
                    listener?.onFail(Secret.NETWORK_UNKNOWN)
                }

                override fun onResponse(call: Call<GetTicketListResponse>, response: Response<GetTicketListResponse>) {
                    response.body()?.let {
                        if (it.status == Secret.NETWORK_SUCCESS) {
                            Log.d(Constants.LOG_NETWORK, "$LOG_SEARCH :${response.body().toString()}")
                            listener?.onSuccess(response.body() as BaseModel, 0)
                        } else {
                            Log.d(Constants.LOG_NETWORK, "$LOG_SEARCH: fail")
                            listener?.onFail(response.body()?.status?: Secret.NETWORK_UNKNOWN)
                        }
                    }
                }

            })
        }

        fun getSubscribedList(networkService: NetworkService, listener: OnResponse?, _id: String, type : Int) {
            lateinit var getSubscribedResonse : Call<GetSubscribedResponse>

            when(type){
                TYPE_ARTIST->getSubscribedResonse = networkService.getSubscribedArtist(1)
                TYPE_CONCERT->getSubscribedResonse = networkService.getSubscribedEvent(1)
                TYPE_THEME->getSubscribedResonse = networkService.getSubscribedGenre(1)
            }

            getSubscribedResonse.enqueue(object : Callback<GetSubscribedResponse> {

                override fun onFailure(call: Call<GetSubscribedResponse>, t: Throwable) {
                    Log.e(Constants.LOG_NETWORK, t.toString())
                    listener?.onFail()
                }

                override fun onResponse(call: Call<GetSubscribedResponse>, response: Response<GetSubscribedResponse>) {
                    response.body()?.let {
                        if (it.status == 200) {
                            Log.d(Constants.LOG_NETWORK, "$LOG_SEARCH :${response.body().toString()}")
                            listener?.onSuccess(response.body() as BaseModel, 0)
                        } else {
                            Log.d(Constants.LOG_NETWORK, "$LOG_SEARCH: fail")
                            listener?.onFail()
                        }
                    }
                }

            })
        }

        fun getCalendarList(networkService: NetworkService, listener: OnResponse?,
                            type: String, id:String, year: String, month: String, day: String?=null) {
            lateinit var getCalendarResponse: Call<GetCalendarResponse>
            var networkServiceType = TYPE_MONTH

            if(day == null){
                networkService.getCalendarList(1, "all", null, "2019", "1")
            }
            else{
                networkService.getCalendarDayList(1, "all", null, "2019", "1", "1")
                networkServiceType = TYPE_DAY
            }

            getCalendarResponse?.enqueue(object : Callback<GetCalendarResponse> {

                override fun onFailure(call: Call<GetCalendarResponse>, t: Throwable) {
                    Log.e(Constants.LOG_NETWORK, t.toString())
                    listener?.onFail(Secret.NETWORK_UNKNOWN)
                }

                override fun onResponse(call: Call<GetCalendarResponse>, response: Response<GetCalendarResponse>) {
                    response.body()?.let {
                        if (it.status == Secret.NETWORK_SUCCESS) {
                            Log.d(Constants.LOG_NETWORK, "$LOG_SEARCH :${response.body().toString()}")
                            listener?.onSuccess(response.body() as BaseModel, networkServiceType)
                        } else {
                            Log.d(Constants.LOG_NETWORK, "$LOG_SEARCH: fail")
                            listener?.onFail(response.body()?.status?: Secret.NETWORK_UNKNOWN)
                        }
                    }
                }

            })
        }

        fun testRetrofit1(networkService: NetworkService, listener: OnResponse?) {
            val jsonObject = JSONObject()
            jsonObject.put("id", "heesung6701@naver.com")
            val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

            Log.d(Constants.LOG_NETWORK, "postIdCheck :$gsonObject")
            val postSignUpResponse: Call<PostIdCheckResponse> =
                networkService.postIdCheck(gsonObject)
            postSignUpResponse.enqueue(object : Callback<PostIdCheckResponse> {

                override fun onFailure(call: Call<PostIdCheckResponse>, t: Throwable) {
                    Log.e(Constants.LOG_NETWORK, t.toString())
                    listener?.onFail()
                }

                //통신 성공 시 수행되는 메소드
                override fun onResponse(call: Call<PostIdCheckResponse>, response: Response<PostIdCheckResponse>) {
                    Log.d(Constants.LOG_NETWORK, response.errorBody()?.string() ?: response.message())

                    if (response.isSuccessful) {
                        Log.d(Constants.LOG_NETWORK, "postLogin :${response.body()}")
                        listener?.onSuccess(response.body() as BaseModel, 0)
                    } else {
                        Log.d(Constants.LOG_NETWORK, "postLogin : fail")
                    }
                }
            })
        }

        fun testREtrofit2(networkService: NetworkService, listener: OnResponse?) {
            val jsonObject = JSONObject()
            jsonObject.put("id", "teamkerbell@teamkerbell.tk")
            jsonObject.put("pwd", "12341234")
            jsonObject.put("client_token", "")
            val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

            Log.d(Constants.LOG_NETWORK, "postLogin :$gsonObject")
            val postLoginResponse: Call<PostLoginResponse> =
                networkService.postLogin(gsonObject)
            postLoginResponse.enqueue(object : Callback<PostLoginResponse> {

                override fun onFailure(call: Call<PostLoginResponse>, t: Throwable) {
                    Log.e("sign up fail", t.toString())
                }

                //통신 성공 시 수행되는 메소드
                override fun onResponse(call: Call<PostLoginResponse>, response: Response<PostLoginResponse>) {
                    Log.d(Constants.LOG_NETWORK, response.toString())
                    if (response.isSuccessful) {
                        Log.d(Constants.LOG_NETWORK, "postLogin :${response.body()}")
//                        listener?.onSuccess()
                    } else {
                        Log.d(Constants.LOG_NETWORK, "postLogin : fail")
                    }
                }
            })
        }
    }
}
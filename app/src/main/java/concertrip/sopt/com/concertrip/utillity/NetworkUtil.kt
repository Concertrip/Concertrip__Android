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
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.TYPE_GENRE
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.USER_TOKEN
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkUtil {

    companion object {


        private const val LOG_SUBSCRIBE_ARTIST = "/api/subscribe/artist"
        fun subscribeArtist(networkService: NetworkService, listener: OnResponse?, _id: String, position: Int? = null) {
            val jsonObject = JSONObject()
            jsonObject.put(USGS_REQUEST_URL.JSON_ARTIST_ID, _id)
            val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

            Log.d(Constants.LOG_NETWORK, "$LOG_SUBSCRIBE_ARTIST, POST :$gsonObject")
            val subscribeArtist: Call<MessageResponse> =
                networkService.postSubScribeArtist(USER_TOKEN, gsonObject)
            subscribeArtist.enqueue(object : Callback<MessageResponse> {
                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    Log.e(Constants.LOG_NETWORK, t.toString())
                    listener?.onFail(Secret.NETWORK_UNKNOWN)
                }

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
        fun subscribeGenre(networkService: NetworkService, listener: OnResponse?, _id: String, position: Int?=null) {
            val jsonObject = JSONObject()
            jsonObject.put(USGS_REQUEST_URL.JSON_GENRE_ID, _id)
            val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

            Log.d(Constants.LOG_NETWORK, "$LOG_SUBSCRIBE_GENRE, POST :$gsonObject")

            val subscribeGenre: Call<MessageResponse> =
                networkService.postSubscribeGenre(USER_TOKEN, gsonObject)
            subscribeGenre.enqueue(object : Callback<MessageResponse> {

                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    Log.e(Constants.LOG_NETWORK, t.toString())
                    listener?.onFail(Secret.NETWORK_UNKNOWN)
                }

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


        private const val LOG_SUBSCRIBE_CONCERT = "/api/subscribe/event"
        fun subscribeConcert(networkService: NetworkService, listener: OnResponse?, _id: String, position: Int?=null) {
            val jsonObject = JSONObject()
            jsonObject.put(USGS_REQUEST_URL.JSON_CONCERT_ID, _id)
            val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

            Log.d(Constants.LOG_NETWORK, "$LOG_SUBSCRIBE_CONCERT, POST :$gsonObject")

            val subscribeConcert: Call<MessageResponse> =
                networkService.postSubscribeConcert(USER_TOKEN, gsonObject)
            subscribeConcert.enqueue(object : Callback<MessageResponse> {

                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    Log.e(Constants.LOG_NETWORK, t.toString())
                    listener?.onFail(Secret.NETWORK_UNKNOWN)
                }

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

        fun search(networkService: NetworkService, listener: OnResponse?, tag: String, position: Int?=null) {

            Log.d(Constants.LOG_NETWORK, "$LOG_SEARCH, GET ? tag=$tag")

            val search: Call<GetSearchResponse> =
                networkService.getSearch(USER_TOKEN, tag)
            search.enqueue(object : Callback<GetSearchResponse> {

                override fun onFailure(call: Call<GetSearchResponse>, t: Throwable) {
                    Log.e(Constants.LOG_NETWORK, t.toString())
                    listener?.onFail(Secret.NETWORK_UNKNOWN)
                }

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
            val getTicketListResponse: Call<GetTicketListResponse> = networkService.getTicketList(USER_TOKEN) // _id

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
                            Log.d(Constants.LOG_NETWORK, "$LOG_SEARCH: fail ${response.body()?.message}")
                            listener?.onFail(response.body()?.status?: Secret.NETWORK_UNKNOWN)
                        }
                    }
                }

            })
        }

        fun getSubscribedList(networkService: NetworkService, listener: OnResponse?, _id: String, type : Int) {
            lateinit var getSubscribedResonse : Call<GetSubscribedResponse>

            when(type){
                TYPE_ARTIST->getSubscribedResonse = networkService.getSubscribedArtist(USER_TOKEN)
                TYPE_CONCERT->getSubscribedResonse = networkService.getSubscribedEvent(USER_TOKEN)
                TYPE_GENRE->getSubscribedResonse = networkService.getSubscribedGenre(USER_TOKEN)
            }

            getSubscribedResonse.enqueue(object : Callback<GetSubscribedResponse> {

                override fun onFailure(call: Call<GetSubscribedResponse>, t: Throwable) {
                    Log.e(Constants.LOG_NETWORK, t.toString())
                    listener?.onFail(Secret.NETWORK_UNKNOWN)
                }

                override fun onResponse(call: Call<GetSubscribedResponse>, response: Response<GetSubscribedResponse>) {
                    response.body()?.let {
                        if (it.status == Secret.NETWORK_SUCCESS) {
                            Log.d(Constants.LOG_NETWORK, "$LOG_SEARCH :${response.body().toString()}")
                            listener?.onSuccess(response.body() as BaseModel, 0)
                        } else {
                            Log.d(Constants.LOG_NETWORK, "$LOG_SEARCH: fail ${response.body()?.message}")
                            listener?.onFail(Secret.NETWORK_UNKNOWN)
                        }
                    }
                }

            })
        }


        private const val LOG_CALENDAR_DAY = "/api/calendar/day"
        private const val LOG_CALENDAR_TYPE = "/api/calendar/type"
        fun getCalendarList(networkService: NetworkService, listener: OnResponse?,
                            type: String, id:String, year: String, month: String, day: String?=null) {
            val getCalendarResponse: Call<GetCalendarResponse>
            var networkServiceType = TYPE_MONTH

            var LOG_TAG : String =""
            getCalendarResponse = if(day == null){

                LOG_TAG = LOG_CALENDAR_TYPE
                Log.d(Constants.LOG_NETWORK, "$LOG_TAG, GET ? type = $type , id = $id , year = $year , month = $month")
                networkService.getCalendarList(USER_TOKEN, type, id, year, month)
            }
            else{
                LOG_TAG= LOG_CALENDAR_DAY
                networkServiceType = TYPE_DAY
                Log.d(Constants.LOG_NETWORK, "$LOG_TAG, GET ? type = $type , id = $id , year = $year , month = $month, day = $day")
                networkService.getCalendarDayList(USER_TOKEN, type, id, year, month,day)
            }

            getCalendarResponse.enqueue(object : Callback<GetCalendarResponse> {

                override fun onFailure(call: Call<GetCalendarResponse>, t: Throwable) {
                    Log.e(Constants.LOG_NETWORK, "$LOG_TAG $t")
                    listener?.onFail(Secret.NETWORK_UNKNOWN)
                }

                override fun onResponse(call: Call<GetCalendarResponse>, response: Response<GetCalendarResponse>) {
                    response.body()?.let {
                        if (it.status == Secret.NETWORK_SUCCESS) {
                            Log.d(Constants.LOG_NETWORK, "$LOG_TAG :${response.body().toString()}")
                            listener?.onSuccess(response.body() as BaseModel, networkServiceType)
                        } else {
                            Log.d(Constants.LOG_NETWORK, "$LOG_TAG: fail ${response.body()?.message}")
                            listener?.onFail(response.body()?.status?: Secret.NETWORK_UNKNOWN)
                        }
                    }
                }

            })
        }

    }
}
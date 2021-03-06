package concertrip.sopt.com.concertrip.network

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApplicationController : Application(){

    private val baseURL = USGS_REQUEST_URL.URL_ROOT

    lateinit var networkService: NetworkService

    companion object {
        lateinit var instance: ApplicationController
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
        buildNetWork()
    }


    private fun buildNetWork(){
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create()).build()

        networkService = retrofit.create(NetworkService::class.java)
    }
}
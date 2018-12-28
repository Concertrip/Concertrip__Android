package concertrip.sopt.com.concertrip.activities.main.fragment.liked

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.gson.JsonObject
import com.google.gson.JsonParser

import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.activities.info.ArtistActivity
import concertrip.sopt.com.concertrip.activities.info.ConcertActivity
import concertrip.sopt.com.concertrip.interfaces.ListData
import concertrip.sopt.com.concertrip.interfaces.OnFragmentInteractionListener
import concertrip.sopt.com.concertrip.list.adapter.BasicListAdapter
import concertrip.sopt.com.concertrip.model.Artist
import concertrip.sopt.com.concertrip.model.Concert
import concertrip.sopt.com.concertrip.network.ApplicationController
import concertrip.sopt.com.concertrip.network.NetworkService
import concertrip.sopt.com.concertrip.network.response.PostIdCheckResponse
import concertrip.sopt.com.concertrip.network.response.PostLoginResponse
import concertrip.sopt.com.concertrip.utillity.Constants
import kotlinx.android.synthetic.main.fragment_liked.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.ref.WeakReference

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [LikedFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [LikedFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class LikedFragment : Fragment() ,View.OnClickListener{

    var LOG_TAG = this::class.java.simpleName

    var dataList = ArrayList<ListData>()
    var dataListArtist = arrayListOf<Artist>()
    var dataListConcert = arrayListOf<Concert>()

    lateinit  var adapter :BasicListAdapter

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }


    val STATE_ARTIST = 0
    val STATE_THEME = 1
    val STATE_CONCERT = 2

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_liked, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialUI()
        connectRequestData(STATE_ARTIST)
    }

    // TODO: Rename method, update argument and hook method into UI event


    private fun changeFragment(to : Int, idx : Int){
        val bundle = Bundle()
        bundle.putInt(Constants.BUNDLE_KEY_INDEX,idx)

        //TODO
        listener?.changeFragment(to,bundle)
    }


    private fun initialUI(){
        btn_liked_artist.setOnClickListener(this)
        btn_liked_concert.setOnClickListener(this)
        btn_liked_theme.setOnClickListener(this)

        activity?.let {
            adapter= BasicListAdapter(it.applicationContext, dataList)

            recycler_view.adapter=adapter
        }


        updateTextColor(btn_liked_artist)
    }

    private fun connectRequestData(state : Int){

        when(state){
            STATE_ARTIST->{
                updateDataList(Artist.getDummyArray())
            }
            STATE_CONCERT->{
                updateDataList(Concert.getDummyArray())
            }
            STATE_THEME->{
                val list = ArrayList<ListData>()
                list.addAll(Concert.getDummyArray())
                list.addAll(Artist.getDummyArray())
                updateDataList(list)
            }
            else->{
            }
        }
        //TODO onFail -> Toast, OnSuccess->udateDataList()
    }


    private fun updateDataList(list : ArrayList<out ListData>){



        this.dataList.clear()
        this.dataList.addAll(list)
        adapter.notifyDataSetChanged()

    }


    fun buttonClick(obj : ListData){
        //TODO
        when(obj){
            is Artist->{
                startActivity<ArtistActivity>()
            }
            is Concert->{
                startActivity<ConcertActivity>()

            }
        }


    }



    private var curTextView : TextView?=null
    fun updateTextColor(view : TextView){
        curTextView?.setTextColor(Color.BLACK)
        curTextView=view
        view.setTextColor(Color.BLUE)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.btn_liked_concert->{
                updateTextColor(view as TextView)
                connectRequestData(STATE_CONCERT)
            }
            R.id.btn_liked_artist->{
                updateTextColor(view as TextView)
                Log.d(LOG_TAG,"artist click")
                connectRequestData(STATE_ARTIST)
            }
            R.id.btn_liked_theme->{
                updateTextColor(view as TextView)
                Log.d(LOG_TAG,"theme click")
                connectRequestData(STATE_THEME)
            }
        }

    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LikedFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LikedFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun testRetrofit1(){
        val jsonObject = JSONObject()
        jsonObject.put("id","heesung6701@naver.com")
        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

        Log.d(Constants.LOG_NETWORK, "postIdCheck :$gsonObject")
        val postSignUpResponse: Call<PostIdCheckResponse> =
            networkService.postIdCheck(gsonObject)
        postSignUpResponse.enqueue(object : Callback<PostIdCheckResponse> {

            override fun onFailure(call: Call<PostIdCheckResponse>, t: Throwable) {
                Log.e("sign up fail", t.toString())
            }
            //통신 성공 시 수행되는 메소드
            override fun onResponse(call: Call<PostIdCheckResponse>, response: Response<PostIdCheckResponse>) {
                Log.d(Constants.LOG_NETWORK, response.errorBody()?.string()?:response.message())
                if (response.isSuccessful) {
                    Log.d(Constants.LOG_NETWORK, "postLogin :${response.body()}")
                    toast(response.body()!!.message)
                }else{
                    Log.d(Constants.LOG_NETWORK, "postLogin : fail")
                }
            }
        })
    }
    fun testREtrofit2(){
        val jsonObject = JSONObject()
        jsonObject.put("id","teamkerbell@teamkerbell.tk")
        jsonObject.put("pwd","12341234")
        jsonObject.put("client_token","")
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
                    toast(response.body()!!.toString())
                }
                else{
                    Log.d(Constants.LOG_NETWORK, "postLogin : fail")
                }
            }
        })
    }
}

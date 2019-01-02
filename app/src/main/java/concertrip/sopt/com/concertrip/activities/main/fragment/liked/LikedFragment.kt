package concertrip.sopt.com.concertrip.activities.main.fragment.liked

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.R.id.*
import concertrip.sopt.com.concertrip.interfaces.ListData
import concertrip.sopt.com.concertrip.interfaces.OnFragmentInteractionListener
import concertrip.sopt.com.concertrip.list.adapter.BasicListAdapter
import concertrip.sopt.com.concertrip.model.Artist
import concertrip.sopt.com.concertrip.model.Concert
import concertrip.sopt.com.concertrip.network.ApplicationController
import concertrip.sopt.com.concertrip.network.NetworkService
import concertrip.sopt.com.concertrip.interfaces.OnItemClick
import concertrip.sopt.com.concertrip.interfaces.OnResponse
import concertrip.sopt.com.concertrip.network.response.GetArtistSubscribeResponse
import concertrip.sopt.com.concertrip.network.response.GetConcertSubscribeResponse
import concertrip.sopt.com.concertrip.network.response.GetGenreSubscribeResponse
import concertrip.sopt.com.concertrip.network.response.GetSubscribedResponse
import concertrip.sopt.com.concertrip.network.response.data.ArtistData
import concertrip.sopt.com.concertrip.network.response.data.ConcertData
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel
import concertrip.sopt.com.concertrip.utillity.Constants
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.TYPE_ARTIST
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.TYPE_CONCERT
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.TYPE_THEME
import concertrip.sopt.com.concertrip.utillity.NetworkUtil
import kotlinx.android.synthetic.main.fragment_liked.*

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
class LikedFragment : Fragment() ,View.OnClickListener, OnItemClick, OnResponse{

    var LOG_TAG = this::class.java.simpleName

    var dataList = ArrayList<ListData>()

    lateinit  var adapter :BasicListAdapter

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var listener: OnFragmentInteractionListener? = null


    override fun onSuccess(obj: BaseModel,position: Int?) {

        when(obj){
            is GetArtistSubscribeResponse->{
                activity?.let {
                    Toast.makeText(it.applicationContext, "내 아티스트에 추가되었습니다!", Toast.LENGTH_LONG).show()
                }
            }
            is GetConcertSubscribeResponse->{

                activity?.let {
                    Toast.makeText(it.applicationContext, "내 공연에 추가되었습니다!", Toast.LENGTH_LONG).show()
                }
            }
            is GetGenreSubscribeResponse->{

                activity?.let {
                    Toast.makeText(it.applicationContext, "내 장르에 추가되었습니다!", Toast.LENGTH_LONG).show()
                }

            }
            is GetSubscribedResponse->{
                // 아티스트, 이벤트, 장르를 리사이클러뷰에 출력
            }

        }
    }

    override fun onFail() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun changeFragment(to : Int, idx : Int){
        val bundle = Bundle()
        bundle.putInt(Constants.BUNDLE_KEY_INDEX,idx)

        //TODO
        listener?.changeFragment(to,bundle)
    }


    override fun onItemClick(root: RecyclerView.Adapter<out RecyclerView.ViewHolder>, position: Int) {

//        when(curTextView?.id ?: R.id.btn_liked_artist){
//            R.id.btn_liked_artist->{
//                NetworkUtil.subscribeArtist(networkService,this,dataList[position].getId())
//            }
//            R.id.btn_liked_concert->{
//                NetworkUtil.subscribeConcert(networkService,this,dataList[position].getId())
//            }
//            R.id.btn_liked_theme->{
//                NetworkUtil.subscribeGenre(networkService,this,dataList[position].getId())
//            }
//        }

    }



    override fun onClick(view: View) {
        when(view.id){
            R.id.tv_liked_concert->{
                connectRequestData(TYPE_CONCERT)
            }
            R.id.tv_liked_artist->{
                connectRequestData(TYPE_ARTIST)
            }
            R.id.tv_liked_theme->{
                connectRequestData(TYPE_THEME)
            }
        }

    }





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
        connectRequestData(TYPE_ARTIST)
    }




    private fun initialUI(){
        tv_liked_artist.setOnClickListener(this)
        tv_liked_concert.setOnClickListener(this)
        tv_liked_theme.setOnClickListener(this)

        activity?.let {
            adapter= BasicListAdapter(it.applicationContext, dataList,this)

            recycler_view.adapter=adapter
        }

    }


    private var curTextView : TextView?=null
    private fun updateTextColor(view : TextView){
        activity?.let {
            curTextView?.setTextColor(ContextCompat.getColor(it.applicationContext, R.color.textTagDefault))
            curTextView=view
            view.setTextColor(ContextCompat.getColor(it.applicationContext, R.color.textSelected))
        }
    }



    private fun updateDataList(list : ArrayList<out ListData>){
        this.dataList.clear()
        this.dataList.addAll(list)
        adapter.notifyDataSetChanged()
    }





    private fun connectRequestData(state : Int){
        when(state){
            TYPE_ARTIST->{
                connectArtistSubscribe()
                updateTextColor(tv_liked_artist)
            }
            TYPE_CONCERT->{
                connectConcertSubscribe()
                updateTextColor(tv_liked_concert)
            }
            TYPE_THEME->{
                connectGenreSubscribe()
                updateTextColor(tv_liked_theme)
            }
            else->{
            }
        }
    }
    private fun connectArtistSubscribe(){
//        val artistSubscribeResponse = GetArtistSubscribeResponse(ArtistData.getDummyArray())
//        updateDataList(artistSubscribeResponse.getArtistList())
    }

    private fun connectConcertSubscribe(){
        val concertSubscribeResponse = GetConcertSubscribeResponse(ConcertData.getDummyArray())
        updateDataList(concertSubscribeResponse.getConcertList())
    }

    private fun connectGenreSubscribe(){


//        val genreSubscribeResponse = GetGenreSubscribeResponse(ArtistData.getDummyArray())
//
//        val list = ArrayList<ListData>()
//        list.addAll(genreSubscribeResponse.getArtistList())
//        updateDataList(list)

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


}

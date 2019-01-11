package concertrip.sopt.com.concertrip.activities.main.fragment.liked

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.interfaces.ListData
import concertrip.sopt.com.concertrip.interfaces.OnFragmentInteractionListener
import concertrip.sopt.com.concertrip.list.adapter.BasicListAdapter
import concertrip.sopt.com.concertrip.network.ApplicationController
import concertrip.sopt.com.concertrip.network.NetworkService
import concertrip.sopt.com.concertrip.interfaces.OnResponse
import concertrip.sopt.com.concertrip.network.response.GetSubscribedResponse
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.TAB_ARTIST
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.TAB_CONCERT
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.TAB_GENRE
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.TYPE_ARTIST
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.TYPE_CONCERT
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.TYPE_GENRE
import concertrip.sopt.com.concertrip.utillity.NetworkUtil.Companion.getSubscribedList
import concertrip.sopt.com.concertrip.utillity.Secret.Companion.NETWORK_NO_DATA
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_liked.*

class LikedFragment : Fragment(), View.OnClickListener, OnResponse {

    var LOG_TAG = this::class.java.simpleName

    var dataList = ArrayList<ListData>()
    lateinit var adapter: BasicListAdapter

    var curState : Int = TYPE_ARTIST

    private val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    private var listener: OnFragmentInteractionListener? = null


    override fun onSuccess(obj: BaseModel, position: Int?) {


        activity?.progress_bar?.visibility = View.GONE

        activity?.let {
            if (obj is GetSubscribedResponse) {
                // 아티스트, 이벤트, 장르를 리사이클러뷰에 출력
                val responseBody = obj as GetSubscribedResponse

                dataList.clear()
                when (position) {
                    TYPE_ARTIST ->
                        dataList.addAll(responseBody.toArtistList())
                    TYPE_GENRE ->
                        dataList.addAll(responseBody.toGenreList())
                    TYPE_CONCERT ->
                        dataList.addAll(responseBody.toConcertList())
                }

                adapter.notifyDataSetChanged()

            }
        }
    }

    override fun onFail(status: Int) {
        activity?.progress_bar?.visibility = View.GONE

        when (status) {
            NETWORK_NO_DATA -> {
                clearDataList()
            }
            else -> {
            }

        }

    }


    override fun onClick(view: View) {
        if (activity?.progress_bar?.visibility == View.VISIBLE) return
        when (view.id) {
            R.id.tv_liked_concert -> {
                curState= TYPE_CONCERT
                connectRequestData(TYPE_CONCERT)
            }
            R.id.tv_liked_artist -> {
                curState= TYPE_ARTIST
                connectRequestData(TYPE_ARTIST)
            }
            R.id.tv_liked_genre -> {
                curState= TYPE_GENRE
                connectRequestData(TYPE_GENRE)
            }
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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


    private fun initialUI() {


        tv_liked_artist.setOnClickListener(this)
        tv_liked_concert.setOnClickListener(this)
        tv_liked_genre.setOnClickListener(this)

        activity?.let {
            updateTextColor(tv_liked_artist, v_artist_underlind)

            adapter = BasicListAdapter(it.applicationContext, dataList)
            recycler_view.adapter = adapter
        }

    }


    private var curTextView: TextView? = null
    private var curView: View? = null
    private fun updateTextColor(textView: TextView, view: View) {
        activity?.let {
            curTextView?.setTextColor(ContextCompat.getColor(it.applicationContext, R.color.textTagDefault))
            curView?.visibility = View.INVISIBLE
            curTextView = textView
            curView = view

            view.visibility = View.VISIBLE
            textView.setTextColor(ContextCompat.getColor(it.applicationContext, R.color.textSelected))
        }
    }


    private fun clearDataList() {
        dataList.clear()
        adapter.notifyDataSetChanged()
    }

    private fun connectRequestData(state: Int) {
        clearDataList()



        activity?.progress_bar?.visibility = View.VISIBLE

        when (state) {
            TYPE_ARTIST -> {
                getSubscribedList(networkService, this, "", TYPE_ARTIST)
                updateTextColor(tv_liked_artist, v_artist_underlind)
            }
            TYPE_CONCERT -> {
                getSubscribedList(networkService, this, "", TYPE_CONCERT)
                updateTextColor(tv_liked_concert, v_concert_underlind)
            }
            TYPE_GENRE -> {
                getSubscribedList(networkService, this, "", TYPE_GENRE)
                updateTextColor(tv_liked_genre, v_theme_underlind)
            }
            else -> {
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

    override fun onResume() {
        super.onResume()
        connectRequestData(curState)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

}

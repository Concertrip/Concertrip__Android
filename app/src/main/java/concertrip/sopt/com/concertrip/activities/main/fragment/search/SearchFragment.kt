package concertrip.sopt.com.concertrip.activities.main.fragment.search

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.interfaces.OnFragmentInteractionListener
import concertrip.sopt.com.concertrip.interfaces.OnResponse
import concertrip.sopt.com.concertrip.list.adapter.BasicListAdapter
import concertrip.sopt.com.concertrip.model.Artist
import concertrip.sopt.com.concertrip.model.Concert
import concertrip.sopt.com.concertrip.model.Genre
import concertrip.sopt.com.concertrip.network.ApplicationController
import concertrip.sopt.com.concertrip.network.NetworkService
import concertrip.sopt.com.concertrip.network.response.GetSearchResponse
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel
import concertrip.sopt.com.concertrip.utillity.NetworkUtil
import concertrip.sopt.com.concertrip.utillity.Secret
import kotlinx.android.synthetic.main.fragment_search.*
import org.jetbrains.anko.toast

class SearchFragment : Fragment() ,OnResponse{
    var dataListArtist = arrayListOf<Artist>()
    var dataListGenre = arrayListOf<Artist>()
    var dataListConcert = arrayListOf<Concert>()

    var adapter : BasicListAdapter?= null


    private val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    private var listener: OnFragmentInteractionListener? = null

    private var searchTxt : String=""

    lateinit var concertListAdapter: BasicListAdapter
    lateinit var artistListAdapter: BasicListAdapter
    lateinit var genreListAdapter: BasicListAdapter

    override fun onSuccess(obj: BaseModel, position: Int?) {

        if(obj is GetSearchResponse){
            val searchResponseData = obj as GetSearchResponse

            val concertList = searchResponseData.toConcertList()
            val artistList = searchResponseData.toArtistList()
            val genreList = searchResponseData.toGenreList()

            showListView((concertList.size+ artistList.size + genreList.size )>0)


            dataListConcert.clear()
            dataListConcert.addAll(concertList)

            dataListArtist.clear()
            dataListArtist.addAll(artistList)


            dataListGenre.clear()
            dataListGenre.addAll(artistList)

            updateListConcert(ArrayList(concertList))
            updateListArtist(ArrayList(artistList))
            updateListGenre(ArrayList(genreList))


        }
    }

    override fun onFail(status : Int) {
        when(status){
            Secret.NETWORK_NO_DATA->{
                showListView(false)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialUI()
    }


    private fun initialUI(){
        activity?.let {
            concertListAdapter = BasicListAdapter(it.applicationContext,dataListConcert)
            recycler_view_concert.adapter=concertListAdapter

            artistListAdapter = BasicListAdapter(it.applicationContext,dataListArtist)
            recycler_view_artist.adapter=artistListAdapter


            genreListAdapter = BasicListAdapter(it.applicationContext,dataListGenre)
            recycler_view_genre.adapter=genreListAdapter
        }


        btn_search.setOnClickListener {
            connectRequestData()
        }

        btn_back.setOnClickListener {
            activity?.onBackPressed()
        }
        btn_result_add.setOnClickListener {
            activity?.toast("$searchTxt 정보 등록을 요청했습니다.")
        }

    }

    private fun showListView(b : Boolean){
            search_result.visibility=if (b) View.VISIBLE else View.GONE
    }


    private fun updateListArtist(list : ArrayList<Artist>){
        dataListArtist.clear()
        dataListArtist.addAll(list)
        artistListAdapter.notifyDataSetChanged()
    }
    private fun updateListGenre(list : ArrayList<Genre>){
        dataListGenre.clear()
        dataListGenre.addAll(list)
        genreListAdapter.notifyDataSetChanged()
    }


    private fun updateListConcert(list : ArrayList<Concert>){
        dataListConcert.clear()
        dataListConcert.addAll(list)
        concertListAdapter.notifyDataSetChanged()
    }


    private fun connectRequestData() {

        searchTxt = edt_search.text.toString()
        tv_result_no.text = ("'$searchTxt' ${getString(R.string.txt_result_no)}")
        btn_result_add.text = ("'$searchTxt' ${getString(R.string.txt_result_add)}")

        NetworkUtil.search(networkService, this, searchTxt)

    }
}

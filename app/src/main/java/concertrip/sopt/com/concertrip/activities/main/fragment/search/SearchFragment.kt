package concertrip.sopt.com.concertrip.activities.main.fragment.search

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.interfaces.OnFragmentInteractionListener
import concertrip.sopt.com.concertrip.interfaces.OnResponse
import concertrip.sopt.com.concertrip.list.adapter.BasicListAdapter
import concertrip.sopt.com.concertrip.model.Artist
import concertrip.sopt.com.concertrip.model.Concert
import concertrip.sopt.com.concertrip.network.ApplicationController
import concertrip.sopt.com.concertrip.network.NetworkService
import concertrip.sopt.com.concertrip.network.response.GetSearchResponse
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel
import concertrip.sopt.com.concertrip.utillity.NetworkUtil
import concertrip.sopt.com.concertrip.utillity.Secret
import kotlinx.android.synthetic.main.fragment_search.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SearchFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class SearchFragment : Fragment() ,OnResponse{
    override fun onSuccess(obj: BaseModel, position: Int?) {

        if(obj is GetSearchResponse){
            val searchResponseData = obj as GetSearchResponse
            /*TODO API 들어오면 이거 다시 정확하게 하고 주석 풀기*/
//            val searchResponseData : GetSearchResponse
//                    = GetSearchResponse(SimpleConcertData.getDummyList(), SimpleArtistData.getDummyList())
            val concertList = searchResponseData.toConcertList()
            val artistList = searchResponseData.toArtistList()
            val genreList = searchResponseData.toGenreList()

            showListView((concertList.size+ artistList.size + genreList.size )>0)



            //임시 처리
            dataListConcert.clear()
            dataListConcert.addAll(concertList)

            dataListArtist.clear()
            dataListArtist.addAll(artistList)


            dataListGenre.clear()
            dataListGenre.addAll(artistList)

//        if(searchTxt.length>10) {
//            dataListArtist.addAll(Artist.getDummyArray())
//            dataListConcert.addAll(Concert.getDummyArray())
//        }
//        else if(searchTxt.length>5) {
//            dataListConcert.addAll(Concert.getDummyArray())
//        }

            updateListConcert(ArrayList(concertList))
            updateListArtist(ArrayList(artistList))
            updateListGenre(ArrayList(genreList))
            //updateListTheme()


        }
    }

    override fun onFail(status : Int) {
        when(status){
            Secret.NETWORK_NO_DATA->{
                showListView(false)
            }
        }

    }

    var dataListArtist = arrayListOf<Artist>()
    var dataListGenre = arrayListOf<Artist>()
    var dataListConcert = arrayListOf<Concert>()

    var adapter : BasicListAdapter?= null


    private val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private var searchTxt : String=""

    lateinit var concertListAdapter: BasicListAdapter
    lateinit var artistListAdapter: BasicListAdapter
    lateinit var genreListAdapter: BasicListAdapter

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
         * @return A new instance of fragment SearchResultFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
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
        return inflater.inflate(R.layout.fragment_search, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialUI()
//        connectRequestData()
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

    }

    private fun showListView(b : Boolean){
            search_result.visibility=if (b) View.VISIBLE else View.GONE
    }


    private fun updateListArtist(list : ArrayList<Artist>){
        dataListArtist.clear()
        dataListArtist.addAll(list)
        artistListAdapter.notifyDataSetChanged()
    }
    private fun updateListGenre(list : ArrayList<Artist>){
        dataListGenre.clear()
        dataListGenre.addAll(list)
        genreListAdapter.notifyDataSetChanged()
    }


    private fun updateListConcert(list : ArrayList<Concert>){
        dataListConcert.clear()
        dataListConcert.addAll(list)
        concertListAdapter.notifyDataSetChanged()
    }


    private fun connectRequestData(){

        searchTxt = edt_search.text.toString()
        tv_result_no.text=("'$searchTxt' ${getString(R.string.txt_result_no)}")
        btn_result_add.text=("'$searchTxt' ${getString(R.string.txt_result_add)}")




        NetworkUtil.search(networkService,this,searchTxt)



    }

}

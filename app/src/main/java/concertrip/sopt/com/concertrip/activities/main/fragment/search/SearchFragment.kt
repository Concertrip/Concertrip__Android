package concertrip.sopt.com.concertrip.activities.main.fragment.search

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager

import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.interfaces.ListData
import concertrip.sopt.com.concertrip.interfaces.OnFragmentInteractionListener
import concertrip.sopt.com.concertrip.list.adapter.BasicListAdapter
import concertrip.sopt.com.concertrip.model.Artist
import concertrip.sopt.com.concertrip.model.Concert
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
class SearchFragment : Fragment() {

    //var dataList = arrayListOf<ListData>()
    var dataListArtist = arrayListOf<Artist>()
    var dataListConcert = arrayListOf<Concert>()

    var adapter : BasicListAdapter?= null

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null



    private var searchTxt : String=""


    lateinit var concertListAdapter: BasicListAdapter
    lateinit var artistListAdapter: BasicListAdapter

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
        connectRequestData()



    }


    private fun initialUI(){


        activity?.let {

            concertListAdapter = BasicListAdapter(it.applicationContext,dataListConcert)
            recycler_view_concert.adapter=concertListAdapter

            artistListAdapter = BasicListAdapter(it.applicationContext,dataListArtist)
            recycler_view_artist.adapter=artistListAdapter
        }


        btn_search.setOnClickListener {

            connectRequestData()
        }

    }
    private fun connectRequestData(){
        searchTxt = edt_search.text.toString()
        tv_result_no.text=("'$searchTxt' ${getString(R.string.txt_result_no)}")
        tv_result_add.text=("'$searchTxt' ${getString(R.string.txt_result_add)}")


        //임시 처리
        dataListArtist.clear()
        dataListConcert.clear()
        if(searchTxt.length>10) {
            dataListArtist.addAll(Artist.getDummyArray())
            dataListConcert.addAll(Concert.getDummyArray())
        }
        else if(searchTxt.length>5) {
            dataListConcert.addAll(Concert.getDummyArray())
        }

        updateUI()

    }
    private fun updateUI(){
        //TODO 1. obj의 결과가 아무것도 없는지 확인
        //없으면 visibility를 GONE, ~~~대한 결과가 없습니다. update해줘야함.

        //아니면 ↓
        if(dataListArtist.size+dataListConcert.size==0)
            search_result.visibility=View.GONE
        else
            search_result.visibility=View.VISIBLE

        updateListArtist()
        updateListConcert()
//      updateListTheme()
    }


    private fun updateListArtist(){

        artistListAdapter.notifyDataSetChanged()

    }


    private fun updateListConcert(){

        concertListAdapter.notifyDataSetChanged()

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
}

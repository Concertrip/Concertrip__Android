package concertrip.sopt.com.concertrip.activities.main.fragment.search

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.dialog.ColorToast
import concertrip.sopt.com.concertrip.interfaces.ListData
import concertrip.sopt.com.concertrip.interfaces.OnFragmentInteractionListener
import concertrip.sopt.com.concertrip.interfaces.OnItemClick
import concertrip.sopt.com.concertrip.interfaces.OnResponse
import concertrip.sopt.com.concertrip.list.adapter.BasicListAdapter
import concertrip.sopt.com.concertrip.list.adapter.HorizontalListAdapter
import concertrip.sopt.com.concertrip.model.Artist
import concertrip.sopt.com.concertrip.model.Concert
import concertrip.sopt.com.concertrip.network.ApplicationController
import concertrip.sopt.com.concertrip.network.NetworkService
import concertrip.sopt.com.concertrip.network.response.GetGenreSearchResponse
import concertrip.sopt.com.concertrip.network.response.GetSearchResponse
import concertrip.sopt.com.concertrip.network.response.data.*
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel

import concertrip.sopt.com.concertrip.utillity.Constants
import concertrip.sopt.com.concertrip.utillity.NetworkUtil
import concertrip.sopt.com.concertrip.utillity.Secret
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_explorer.*

class ExplorerFragment : Fragment(), OnItemClick ,OnResponse{

    override fun onSuccess(obj: BaseModel, position: Int?) {

        activity?.progress_bar?.visibility=View.GONE
        if(obj is GetSearchResponse) {
            val explorerRequestData: GetSearchResponse = obj as GetSearchResponse

            val list = arrayListOf<ListData>()
            list.addAll(explorerRequestData.toArtistList())
            list.addAll(explorerRequestData.toConcertList())
            list.addAll(explorerRequestData.toGenreList())
            updateDataList(list)
        }
        else if(obj is GetGenreSearchResponse) {
            val explorerRequestData: GetGenreSearchResponse = obj as GetGenreSearchResponse

            val list = arrayListOf<ListData>()
            list.addAll(explorerRequestData.toGenreList())
            updateDataList(list)
        }
    }

    private var listener: OnFragmentInteractionListener? = null


    override fun onFail(status : Int) {
        activity?.progress_bar?.visibility=View.GONE
        if(status== Secret.NETWORK_NO_DATA)
            updateDataList(ArrayList<ListData>())
        else{
            activity?.let {
                ColorToast(it.applicationContext,"실패")
            }
        }

    }

    var dataList = arrayListOf<ListData>()
    var dataListTag = arrayListOf<String>("테마", "보이그룹", "걸그룹","힙합","발라드","R&B","댄스","POP","EDM","인디","재즈","록","댄스");


    var selectedTab : Int = 0
    var curSearch = ""

    lateinit var tagAdapter: HorizontalListAdapter
    lateinit var dataAdapter: BasicListAdapter


    private val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
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

    override fun onItemClick(root: RecyclerView.Adapter<out RecyclerView.ViewHolder>, position: Int) {
        if(activity?.progress_bar?.visibility==View.VISIBLE) return

        tagAdapter.setSelect(position)

        if(root is HorizontalListAdapter){
//            when (position) {
//                0 -> {  //TODO 테마 선택시 클릭시
//
//                }
//                else -> connectRequestData(dataListTag[position])
//            }
            selectedTab = position
            connectRequestData(dataListTag[selectedTab])
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
        return inflater.inflate(R.layout.fragment_explorer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialUI()
    }

    private fun initialUI() {
        search_bar.setOnClickListener {
            listener?.changeFragment(Constants.FRAGMENT_SEARCH)
        }

        activity?.let {

            tagAdapter = HorizontalListAdapter(it.applicationContext, dataListTag, this)
            recycler_view_horizontal.adapter = tagAdapter

            dataAdapter = BasicListAdapter(it.applicationContext, dataList, this)
            recycler_view.adapter = dataAdapter

            selectedTab = 0

            connectRequestData(dataListTag[selectedTab])
        }

    }

    private fun updateDataList(list: ArrayList<out ListData>) {
        dataList.clear()
        dataList.addAll(list)
        dataAdapter.notifyDataSetChanged()
    }


    private fun clearListData(){
        dataList.clear()
        dataAdapter.notifyDataSetChanged()
    }

    private fun connectRequestData(tag: String) {

        activity?.progress_bar?.visibility=View.VISIBLE

        clearListData()
        curSearch=tag
        NetworkUtil.search(networkService,this,tag)
    }

    override fun onResume() {
        super.onResume()
        //clearListData()
        connectRequestData(dataListTag[selectedTab])
    }

    override fun onAttachFragment(childFragment: Fragment?) {
        super.onAttachFragment(childFragment)
        //connectRequestData(curSearch) // 작동안됨
    }
}
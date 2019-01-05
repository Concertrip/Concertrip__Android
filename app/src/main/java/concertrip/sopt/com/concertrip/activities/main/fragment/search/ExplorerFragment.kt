package concertrip.sopt.com.concertrip.activities.main.fragment.search

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import concertrip.sopt.com.concertrip.R
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
import concertrip.sopt.com.concertrip.network.response.GetSearchResponse
import concertrip.sopt.com.concertrip.network.response.data.*
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel

import concertrip.sopt.com.concertrip.utillity.Constants
import concertrip.sopt.com.concertrip.utillity.NetworkUtil
import concertrip.sopt.com.concertrip.utillity.Secret
import kotlinx.android.synthetic.main.fragment_explorer.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [Explorer.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [Explorer.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ExplorerFragment : Fragment(), OnItemClick ,OnResponse{
    override fun onSuccess(obj: BaseModel, position: Int?) {

        if(obj is GetSearchResponse) {
            val explorerRequestData: GetSearchResponse = obj as GetSearchResponse

            val list = arrayListOf<ListData>()
            list.addAll(explorerRequestData.toArtistList())
            list.addAll(explorerRequestData.toConcertList())
            list.addAll(explorerRequestData.toGenreList())
            updateDataList(list)
        }
    }

    override fun onFail(status : Int) {
        if(status== Secret.NETWORK_NO_DATA)
            updateDataList(ArrayList<ListData>())
        else
            Toast.makeText(activity!!.applicationContext,"실패",Toast.LENGTH_SHORT).show()
    }

    var dataList = arrayListOf<ListData>()
    var dataListTag = arrayListOf<String>("모두" , "테마", "보이그룹", "걸그룹","힙합","발라드","R&B","댄스","POP","EDM","인디","재즈","록","댄스");


    lateinit var tagAdapter: HorizontalListAdapter
    lateinit var dataAdapter: BasicListAdapter


    private var listener: OnFragmentInteractionListener? = null


    private val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private fun changeFragment() {
        listener?.changeFragment(Constants.FRAGMENT_SEARCH)
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
        tagAdapter.setSelect(position)

        /*TODO 서버 API받고 똑바로 구성한 뒤, 주석풀기*/
        if(root is HorizontalListAdapter){ // 태그를 클릭했을 때
            when (position) {
                0 -> {
                    //아직 API가...ㅜ
                }
                1 -> {  // 테마를 선택하면 이 내부에 저장된 것들을 불러옴

                }
                else -> connectRequestData(dataListTag[position])
            }
        }
//        else{
//            // getBtn()
//            /*TODO 하트 or 종 convert + subscribe 전환*/
//            activity?.let {
//                Toast.makeText(it.applicationContext, "내 공연에 추가되었습니다!", Toast.LENGTH_LONG).show()
//            }
//        }

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
        return inflater.inflate(R.layout.fragment_explorer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialUI() // search_bar에 setOnClickListner 및 adapter 설정
    }

    private fun initialUI() {
        // convert to SearchFragment
        search_bar.setOnClickListener {
            changeFragment()
        }

        activity?.let {

            tagAdapter = HorizontalListAdapter(it.applicationContext, dataListTag, this)
            recycler_view_horizontal.adapter = tagAdapter

            /*TODO 이부분 이상함*/
//            dataListArtist=Artist.getDummyArray()
            //dataListDetail=Artist.getDummyArray()
            dataAdapter = BasicListAdapter(it.applicationContext, dataList, this)
            recycler_view.adapter = dataAdapter


        }

    }

    private fun updateDataList(list: ArrayList<out ListData>) {
        dataList.clear()
        dataList.addAll(list)
        dataAdapter.notifyDataSetChanged()
    }



    private fun connectRequestData(tag: String) {
        NetworkUtil.search(networkService,this,tag)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ExplorerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
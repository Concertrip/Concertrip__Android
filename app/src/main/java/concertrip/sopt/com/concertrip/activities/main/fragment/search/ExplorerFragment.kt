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
import concertrip.sopt.com.concertrip.list.adapter.BasicListAdapter
import concertrip.sopt.com.concertrip.list.adapter.HorizontalListAdapter
import concertrip.sopt.com.concertrip.model.Artist
import concertrip.sopt.com.concertrip.model.Concert
import concertrip.sopt.com.concertrip.network.response.GetSearchResponse
import concertrip.sopt.com.concertrip.network.response.data.ArtistData
import concertrip.sopt.com.concertrip.network.response.data.ConcertData
import concertrip.sopt.com.concertrip.network.response.data.SimpleArtistData
import concertrip.sopt.com.concertrip.network.response.data.SimpleConcertData

import concertrip.sopt.com.concertrip.utillity.Constants
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
class ExplorerFragment : Fragment(), OnItemClick {

    var dataList = arrayListOf<ListData>()
//    var dataListArtist = arrayListOf<Artist>()
//    var dataListConcert = arrayListOf<Concert>()
    var dataListTag = arrayListOf<String>("모두","테마","걸그룹","보이그룹","힙합","발라드")

    lateinit var tagAdapter : HorizontalListAdapter
    lateinit var dataAdapter : BasicListAdapter



    private var listener: OnFragmentInteractionListener? = null


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null



    private fun changeFragment(){
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
//        if(root is HorizontalListAdapter){ // 태그를 클릭했을 때
//            if(position == 1){  // 테마를 선택하면 이 내부에 저장된 것들을 불러옴
//
//            }
//            else{
//                connectRequestData(dataListTag[position])
//            }
//        }
//        else{
//            // getBtn()
//            /*TODO 하트 or 종 convert + isSubscribe 전환*/
//            activity?.let {
//                Toast.makeText(it.applicationContext, "내 공연에 추가되었습니다!", Toast.LENGTH_LONG).show()
//            }
//        }

      //-------------- 밑으로 테스트용------------------

      when (position) {
          1 -> // 테마를 선택한 경우 안드 내부에 저장되어있는 것을 출력
              // 해당 데이터가 저장된 어레이를 이용해 updateDataList 함수 호출
              updateDataList(Artist.getDummyArray2())
          0 -> updateDataList(Artist.getDummyArray())
          else -> connectRequestData(dataListTag[position])
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
        return inflater.inflate(R.layout.fragment_explorer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialUI() // search_bar에 setOnClickListner 및 adapter 설정
        connectRequestTag() // 태그를 받아옴 // 이 태그는 다른 태그를 선택한다고 바뀌는게 아니니까 처음에만 서버에서 받아옴
    }

    private fun initialUI(){
        // convert to SearchFragment
        search_bar.setOnClickListener{
            changeFragment()
        }

        activity?.let {

            tagAdapter = HorizontalListAdapter(it.applicationContext, dataListTag,this)
            recycler_view_horizontal.adapter=tagAdapter

            /*TODO 이부분 이상함*/
//            dataListArtist=Artist.getDummyArray()
            //dataList=Artist.getDummyArray()
            dataAdapter = BasicListAdapter(it.applicationContext, dataList,this)
            recycler_view.adapter = dataAdapter


        }

    }

    private fun updateDataList(list : ArrayList<out ListData>){
        dataList.clear()
        dataList.addAll(list)
        dataAdapter.notifyDataSetChanged()
    }

  

    private fun connectRequestTag(){
        /*TODO
        * dataListTag 초기화
        * 따라서, 나중에 클릭리스너로 리사이클러뷰의 포지션 값을 받으면 이 포지션값을 인덱스로해 connectRequestData 호출*/

        //TODO Retrofit2
        //OnFaill -> Toast ,  OnSuccess-> connectRequest(),updateTagList()
//        val loungePostingResponse: Call<LoungePostingResponse> = networkService!!.postLoungePosting(SharedPreferencesService.instance!!.getPrefStringData("token", "")!!, content, isPublic, body)
//        loungePostingResponse.enqueue(object : Callback<LoungePostingResponse> {
//            override fun onFailure(call: Call<LoungePostingResponse>?, t: Throwable?) {
//                Toast.makeText(this@ExplorerFragment, "connectRequestTag failed", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onResponse(call: Call<LoungePostingResponse>?, response: Response<LoungePostingResponse>?) {
//                if (response!!.body().status.equals(LoungeFragment.NETWORK_SUCCESS)) {
        // dataListTag 초기화
        //connectRequestData("모두") // connnectRequestTag 함수는 처음에만 호출되는거라 여기서는 고정적으로 "모두"에 해당하는 데이터를 받아오면 됨
        // parameter 설정해 받아옴
        // val mAdapter = HorizontalListAdapter(context!!, )
        //recycler_view_horizontal.adapter = mAdapter
        // 리사이클러뷰 리스너 > 클릭시 item position을 dataListTag의 인덱스로 사용해 connectRequestData호출
//                }
//            }
//
//        })

    }







    private fun connectRequestData(tag : String){
        //1. 이거에 해당하는 response class 더미데이터 생성
        //2. 더미데이터에서 필요한 정보 추출
        //

        // 처음 및 태그를 사용자가 클릭했을 때 호출되는 함수

        // 모두, 테마, 걸그룹, 보이그룹, 힙합, 발라드 등등,,,
        // 어느 데이터를 받아올지 param로 받아옴

        val explorerRequestData : GetSearchResponse = GetSearchResponse(SimpleConcertData.getDummyList(), SimpleArtistData.getDummyList())
        val list = arrayListOf<ListData>()
        list.addAll(explorerRequestData.toArtistList())
        list.addAll(explorerRequestData.toConcertList())
        updateDataList(list)

       // val explorerData : ListData = GetSearchResponse(ConcertData.toConcert(), AritistData.toArtist())
        //toConcert toArtist




        //TODO onFail -> Toast, OnSuccess->uodateDataList()
        //        val loungePostingResponse: Call<LoungePostingResponse> = networkService!!.postLoungePosting(SharedPreferencesService.instance!!.getPrefStringData("token", "")!!, content, isPublic, body)
//        loungePostingResponse.enqueue(object : Callback<LoungePostingResponse> {
//            override fun onFailure(call: Call<LoungePostingResponse>?, t: Throwable?) {
//                Toast.makeText(this@ExplorerFragment, "connectRequestTag failed", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onResponse(call: Call<LoungePostingResponse>?, response: Response<LoungePostingResponse>?) {
//                if (response!!.body().status.equals(LoungeFragment.NETWORK_SUCCESS)) {
        //connectRequestData()
        //updateDataList() // 처음엔 모두로 다 받아옴!
//                }
//            }
//
//        })
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
package concertrip.sopt.com.concertrip.activities.info

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.webkit.URLUtil
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.dialog.CustomDialog
import concertrip.sopt.com.concertrip.interfaces.OnItemClick
import concertrip.sopt.com.concertrip.list.adapter.BasicListAdapter
import concertrip.sopt.com.concertrip.model.Artist
import concertrip.sopt.com.concertrip.model.Concert
import concertrip.sopt.com.concertrip.network.ApplicationController
import concertrip.sopt.com.concertrip.network.NetworkService
import concertrip.sopt.com.concertrip.network.response.GetArtistResponse
import concertrip.sopt.com.concertrip.network.response.GetGenreResponse
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.INTENT_TAG_ID
import concertrip.sopt.com.concertrip.utillity.Secret
import kotlinx.android.synthetic.main.activity_artist.*

import kotlinx.android.synthetic.main.content_artist.*
import kotlinx.android.synthetic.main.content_header.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArtistActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener, OnItemClick {

    override fun onItemClick(root: RecyclerView.Adapter<out RecyclerView.ViewHolder>, position: Int) {
        Toast.makeText(this, "내 공연에 추가되었습니다!", Toast.LENGTH_LONG).show()
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        // 다가오는 공연을 담은 리사이클러뷰를 클릭했을때
        /*TODO 하트 or 종 convert + Toast 바꾸기*/
    }

    private val RECOVERY_DIALOG_REQUEST = 1

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, youTubePlayer: YouTubePlayer?, b: Boolean) {
        if (!b&& ::artist.isInitialized) {
            val youtubeUrlList = artist.youtubeUrl!!.split("?v=")
            youTubePlayer?.cueVideo(youtubeUrlList[youtubeUrlList.size-1])  //http://www.youtube.com/watch?v=IA1hox-v0jQ
//            youTubePlayer?.cueVideo(artist.youtubeUrl)  //http://www.youtube.com/watch?v=IA1hox-v0jQ
        }
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider,
        youTubeInitializationResult: YouTubeInitializationResult
    ) {
        if (youTubeInitializationResult.isUserRecoverableError) {
            youTubeInitializationResult.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show()
        } else {
            val errorMessage = String.format(
                "There was an error initializing the YouTubePlayer (%1\$s)", youTubeInitializationResult.toString()
            )
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    private fun getYouTubePlayerProvider(): YouTubePlayer.Provider {
        return findViewById<View>(R.id.youtude) as YouTubePlayerView
    }

    private var isGenre: Boolean = true
    private var artistId: String ="5c298b2a3eea39d2b00ca7d4"

//    private var isGenre: Boolean = false
//    private var artistId: String ="5c287b713eea39d2b0049f3f"

    lateinit  var artist: Artist
    var dataList = arrayListOf<Concert>()
    private lateinit var adapter : BasicListAdapter

    var dataListMember = arrayListOf<Artist>()
    lateinit var memberListAdapter : BasicListAdapter

    private val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    private fun showDialog() {
        val dialog = CustomDialog(this)
        dialog.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artist)

//        if(intent.hasExtra(INTENT_TAG_ID))
//            artistId = intent.getStringExtra(INTENT_TAG_ID)
// 장르인지 아티스트인지 intent에서 받아오기

        initialUI()
        connectRequestData(artistId)
    }

    override fun onResume() {
        super.onResume()

        connectRequestData(artistId)
    }

    fun initialUI(){
        adapter = BasicListAdapter(this, dataList)
        recycler_view.adapter = adapter

//        dataListMember = Artist.getDummyArray()
        memberListAdapter = BasicListAdapter(this, dataListMember, BasicListAdapter.MODE_THUMB)
        recycler_view_member.adapter = memberListAdapter

        scroll_view.smoothScrollTo(0, 0)

        btn_follow.setOnClickListener {
            showDialog()
        }
    }

    private fun updateUI(){
            if(dataListMember.size == 0)
                li_member.visibility = View.GONE
            else
                li_member.visibility = View.VISIBLE
    }


    private fun updateConcertList(list : ArrayList<Concert>){
        dataList.clear()
        dataList.addAll(list)
        adapter.notifyDataSetChanged()
    }

    private fun updateArtistData(){
        // TODO 좋아요 버튼 설정
        if(URLUtil.isValidUrl(artist.backImg))
            Glide.with(this).load(artist.backImg).into(iv_back)
//        else
//            // 기본이미지로 설정
        if(URLUtil.isValidUrl(artist.profileImg))
            Glide.with(this).load(artist.profileImg).apply(RequestOptions.circleCropTransform()).into(iv_profile)
//        else
//            // 기본이미지로 설정

        tv_title.text = artist.name
        tv_tag.text = artist.subscribeNum.toString()

        getYouTubePlayerProvider().initialize(Secret.YOUTUBE_API_KEY, this)
    }

    private fun updateMemberList(list : ArrayList<Artist>){
        dataListMember.clear()
        dataListMember.addAll(list)
        memberListAdapter.notifyDataSetChanged()
    }

    private fun connectRequestData(id : String){


        // 서버에서 넘어오는 데이터 구조가 달라서 따로 구현할 수 밖에 없음ㅠ
        if(isGenre){
            val getGenreResponse : Call<GetGenreResponse> = networkService.getGenre(1, artistId)
            getGenreResponse.enqueue(object : Callback<GetGenreResponse>
            {
                override fun onFailure(call: Call<GetGenreResponse>?, t: Throwable?) {
                    Log.v("test0101", "getArtistResponse in onFailure" + t.toString())
                }
                override fun onResponse(call: Call<GetGenreResponse>?, response: Response<GetGenreResponse>?) {
                    if (response!!.body()?.status == 200) {
                        artist = response!!.body()!!.data.toArtist()
                        updateConcertList(ArrayList(artist.concertList))
                        updateArtistData()
                        updateUI()
                    } else {
                        Log.v("test0102", "getGenreResponse in "+ response.body()?.status.toString())
                    }
                }
            })
        }
        else{
            val getArtistResponse : Call<GetArtistResponse> = networkService.getArtist(1, artistId)
            getArtistResponse.enqueue(object : Callback<GetArtistResponse>
            {
                override fun onFailure(call: Call<GetArtistResponse>?, t: Throwable?) {
                    Log.v("test0101", "getArtistResponse in onFailure" + t.toString())
                }
                override fun onResponse(call: Call<GetArtistResponse>?, response: Response<GetArtistResponse>?) {
                    if (response!!.body()?.status == 200) {
                        artist = response!!.body()!!.data.toArtist()
                        updateConcertList(ArrayList(artist.concertList)) // 굳이 param으로 안넘겨줘도됨!
                        updateMemberList(ArrayList(artist.memberList))
                        updateArtistData()
                        updateUI()
                    } else {
                        Log.v("test0101", "getArtistResponse in "+ response.body()?.status.toString())
                    }

                }
            })
        }
    }

    companion object {
        fun newInstance(): ArtistActivity = ArtistActivity()
    }
}

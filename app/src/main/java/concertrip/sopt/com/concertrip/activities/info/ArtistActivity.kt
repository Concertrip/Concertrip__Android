package concertrip.sopt.com.concertrip.activities.info

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
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
import concertrip.sopt.com.concertrip.network.response.GetArtistResponse
import concertrip.sopt.com.concertrip.network.response.data.ArtistData
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.INTENT_TAG_ID
import concertrip.sopt.com.concertrip.utillity.Secret
import kotlinx.android.synthetic.main.activity_artist.*

import kotlinx.android.synthetic.main.content_artist.*
import kotlinx.android.synthetic.main.content_header.*

class ArtistActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener, OnItemClick {

    override fun onItemClick(root: RecyclerView.Adapter<out RecyclerView.ViewHolder>, idx: Int) {
        Toast.makeText(this, "내 공연에 추가되었습니다!", Toast.LENGTH_LONG).show()
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        // 다가오는 공연을 담은 리사이클러뷰를 클릭했을때
        /*TODO 하트 or 종 convert + Toast 바꾸기*/
    }

    private val RECOVERY_DIALOG_REQUEST = 1

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, youTubePlayer: YouTubePlayer?, b: Boolean) {
        if (!b) {
            youTubePlayer?.cueVideo("ZHoLaLlL5lA")  //http://www.youtube.com/watch?v=IA1hox-v0jQ
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            getYouTubePlayerProvider().initialize(Secret.YOUTUBE_API_KEY, this)
        }
    }

    private var artistId: Int? = null

    lateinit  var artist: Artist
    var dataList = arrayListOf<Concert>()

    private lateinit var adapter : BasicListAdapter

    var dataListMember = arrayListOf<Artist>()
    lateinit var memberListAdapter : BasicListAdapter

    private fun showDialog() {
        val dialog = CustomDialog(this)
        dialog.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artist)
//        setSupportActionBar(toolbar)

        artistId = getIntent().getIntExtra(INTENT_TAG_ID, 0)

        initialUI()
        connectRequestData(artistId!!)
    }

    fun initialUI(){
        adapter = BasicListAdapter(this, Concert.getDummyArray())
        recycler_view.adapter = adapter

        dataListMember = Artist.getDummyArray()
        memberListAdapter = BasicListAdapter(this, dataListMember, BasicListAdapter.MODE_THUMB)
        recycler_view_member.adapter = memberListAdapter

        getYouTubePlayerProvider().initialize(Secret.YOUTUBE_API_KEY, this);
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

    private fun updateArtistData(artist : Artist){
        // TODO 좋아요 버튼 설정
        if(URLUtil.isValidUrl(artist.backImg))
            Glide.with(this).load(artist.backImg).into(iv_back)
        if(URLUtil.isValidUrl(artist.profileImg))
            Glide.with(this).load(artist.profileImg).apply(RequestOptions.circleCropTransform()).into(iv_profile)
        tv_title.text = artist.name
        tv_tag.text = artist.subscribeNum.toString()
    }

    private fun connectRequestData(id : Int){
        // 서버에 데이터 request보내고
        // response 데이터를 이용해
        // 전역변수로 선언되어있는 artist, dataList 업데이트

        // dataList 업데이트
        //this.dataList.clear()
        //this.dataList.addAll(list)

        val getArtistResponse : GetArtistResponse = GetArtistResponse(ArtistData.getDummy())
        val artist = getArtistResponse.data.toArtist()

        //updateConcertList(ArrayList(artist.concertList))
        updateArtistData(artist)
        updateUI()
    }

    override fun onResume() {
        super.onResume()

        connectRequestData(artistId!!)
    }

    companion object {
        fun newInstance(): ArtistActivity = ArtistActivity()
    }
}

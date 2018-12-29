package concertrip.sopt.com.concertrip.activities.info

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.webkit.URLUtil
import android.widget.ScrollView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.R.id.recycler_view
import concertrip.sopt.com.concertrip.dialog.CustomDialog
import concertrip.sopt.com.concertrip.interfaces.OnItemClick
import concertrip.sopt.com.concertrip.list.adapter.ArtistThumbListAdapter
import concertrip.sopt.com.concertrip.list.adapter.BasicListAdapter
import concertrip.sopt.com.concertrip.model.Artist
import concertrip.sopt.com.concertrip.model.Concert
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.INTENT_TAG_ID
import concertrip.sopt.com.concertrip.utillity.Secret
import kotlinx.android.synthetic.main.activity_artist.*

import kotlinx.android.synthetic.main.content_artist.*
import kotlinx.android.synthetic.main.content_header.*
import org.jetbrains.anko.startActivity

class ArtistActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener, OnItemClick {

    var dataListMember = arrayListOf<Artist>()
    lateinit var memberListAdapter : BasicListAdapter

    override fun onItemClick(root: RecyclerView.Adapter<out RecyclerView.ViewHolder>, idx: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        // 다가오는 공연을 담은 리사이클러뷰를 클릭했을때
        /*TODO 하트 or 종 convert + Toast 바꾸기*/
        Toast.makeText(this, "내 공연에 추가되었습니다!", Toast.LENGTH_LONG).show()
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

    var artist: Artist = Artist(0)
    var dataList = arrayListOf<Concert>() // 뭔가 서버에서 artist에 넣어서 한번에 전달해 줄듯

    private lateinit var mAdapter : BasicListAdapter
    private var artistId: Int? = null

    private fun showDialog() {
        val dialog = CustomDialog(this)
        dialog.show()
    }

    //TODO OnItemClick Interface로 구현
//    var onListItemClickListener: View.OnClickListener = View.OnClickListener {
//        startActivity<ConcertActivity>()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artist)
//        setSupportActionBar(toolbar)

        artistId = getIntent().getIntExtra(INTENT_TAG_ID, 0)

//        val mAdapter = ConcertListAdapter(this, Concert.getDummyArray())
        // connectRequestData
        mAdapter = BasicListAdapter(this, Concert.getDummyArray())
        recycler_view.adapter = mAdapter

        dataListMember = Artist.getDummyArray()
        memberListAdapter = BasicListAdapter(this, dataListMember, BasicListAdapter.MODE_THUMB)
        recycler_view_member.adapter = memberListAdapter

        updateUI()

        connectRequestData(artistId!!)


        getYouTubePlayerProvider().initialize(Secret.YOUTUBE_API_KEY, this);
        scroll_view.smoothScrollTo(0, 0)

        btn_follow.setOnClickListener {
            showDialog()
        }
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        initialUI()
//        connectRequestData(STATE_ARTIST)
//    }



    private fun updateUI(){
            if(dataListMember.size == 0)
                li_member.visibility = View.GONE
            else
                li_member.visibility = View.VISIBLE
    }



    fun updateArtistData(){
        // dataList로 mAdapter 데이터 바꿔버리기~
        // mAdapter notify
        mAdapter.notifyDataSetChanged()

        // &

        // Activity도 데이터 다시 세팅!

        // 좋아요 버튼 설정

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

        // updateArtistData 호출
        updateArtistData()
    }

    override fun onResume() {
        super.onResume()

        connectRequestData(artistId!!)
    }

    companion object {
        fun newInstance(): ArtistActivity = ArtistActivity()
    }
}

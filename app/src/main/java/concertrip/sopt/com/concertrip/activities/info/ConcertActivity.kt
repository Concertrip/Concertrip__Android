package concertrip.sopt.com.concertrip.activities.info

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.google.android.youtube.player.internal.v
import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.interfaces.OnItemClick
import concertrip.sopt.com.concertrip.dialog.CustomDialog
import concertrip.sopt.com.concertrip.list.adapter.BasicListAdapter
import concertrip.sopt.com.concertrip.model.Artist
import concertrip.sopt.com.concertrip.model.Concert
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.INTENT_TAG_ID
import concertrip.sopt.com.concertrip.utillity.Secret
import kotlinx.android.synthetic.main.activity_concert.*

import kotlinx.android.synthetic.main.content_concert.*
import kotlinx.android.synthetic.main.content_header.*
import org.jetbrains.anko.startActivity

class ConcertActivity  : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener, OnItemClick {

    override fun onItemClick(root: RecyclerView.Adapter<out RecyclerView.ViewHolder>, idx: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        // 출연진을 담은 리사이클러뷰를 클릭했을때
        val intent =  Intent(this, ArtistActivity::class.java)
        intent.putExtra(INTENT_TAG_ID, dataList[idx].idx)
        startActivity(intent)
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

    var concert : Concert = Concert()
    var dataList = arrayListOf<Artist>() // 이것도 서버에서 한번에 concert에 넣어서 전달해줄지도 모름!!
    // 현재 concert 클래스에 포함되어 있는 변수들은 정확하지 않음
    // ex. 티켓링크가 포함되어 있지 않음
    // >> 디비 완전히 나오면 나중에 더 추가하거나 제거할 예정

    private lateinit var mAdapter : BasicListAdapter
    private var concertId: Int? = null

//    var onListItemClickListener : View.OnClickListener = View.OnClickListener {
//        startActivity<ArtistActivity>()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_concert)
//        setSupportActionBar(toolbar)

        concertId = getIntent().getIntExtra(INTENT_TAG_ID, 0)

        mAdapter = BasicListAdapter(this, Artist.getDummyArray())
        recycler_view.adapter = mAdapter

        connectRequestData(concertId!!)

        getYouTubePlayerProvider().initialize(Secret.YOUTUBE_API_KEY,this);
        scroll_view.smoothScrollTo(0,0)

        btn_follow.setOnClickListener {
            showDialog()
        }
    }

    fun updateConcertData(){
        // dataList로 mAdapter 데이터 바꿔버리기~
        // mAdapter notify
        mAdapter.notifyDataSetChanged()

        // &

        // Activity도 데이터 다시 세팅!

        // 구독하기(종) 버튼 설정

        Glide.with(this).load(concert.backImg).into(iv_back)
        Glide.with(this).load(concert.profileImg).apply(RequestOptions.circleCropTransform()).into(iv_profile)
        tv_title.setText(concert.title)
        tv_tag.setText(concert.subscribeNum)
    }

    private fun connectRequestData(id : Int){
        // 서버에 데이터 request보내고
        // response 데이터를 이용해
        // 전역변수로 선언되어있는 concert, dataList 업데이트

        // dataList 업데이트
        //this.dataList.clear()
        //this.dataList.addAll(list)

        // updateArtistData 호출
        updateConcertData()
    }

    override fun onResume() {
        super.onResume()

        connectRequestData(concertId!!)
    }

     private fun showDialog(){
        val dialog = CustomDialog(this)
        dialog.show()


    }

    companion object {
        fun newInstance(): ConcertActivity = ConcertActivity()
    }
}

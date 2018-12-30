package concertrip.sopt.com.concertrip.activities.info

import android.content.Intent
import android.os.Bundle

import android.support.v7.widget.GridLayoutManager

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
import concertrip.sopt.com.concertrip.interfaces.OnItemClick
import concertrip.sopt.com.concertrip.dialog.CustomDialog
import concertrip.sopt.com.concertrip.list.adapter.BasicListAdapter
import concertrip.sopt.com.concertrip.model.Artist
import concertrip.sopt.com.concertrip.model.Caution
import concertrip.sopt.com.concertrip.model.Concert
import concertrip.sopt.com.concertrip.network.response.GetConcertResponse
import concertrip.sopt.com.concertrip.network.response.data.ConcertData
import concertrip.sopt.com.concertrip.network.response.data.MemberData
import concertrip.sopt.com.concertrip.network.response.data.PrecautionData
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.INTENT_TAG_ID
import concertrip.sopt.com.concertrip.utillity.Secret
import kotlinx.android.synthetic.main.activity_concert.*

import kotlinx.android.synthetic.main.content_concert.*
import kotlinx.android.synthetic.main.content_header.*

class ConcertActivity  : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener, OnItemClick {

    override fun onItemClick(root: RecyclerView.Adapter<out RecyclerView.ViewHolder>, idx: Int) {
        Toast.makeText(this, "내 공연에 추가되었습니다!", Toast.LENGTH_LONG).show()
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

    lateinit  var concert : Concert
    var dataList = arrayListOf<Artist>() // 이것도 서버에서 한번에 concert에 넣어서 전달해줄지도 모름!!
    // 현재 concert 클래스에 포함되어 있는 변수들은 정확하지 않음
    // ex. 티켓링크가 포함되어 있지 않음
    // >> 디비 완전히 나오면 나중에 더 추가하거나 제거할 예정

    private lateinit var adapter : BasicListAdapter

    private lateinit var cautionAdapter : BasicListAdapter

    private var concertId: Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_concert)
//        setSupportActionBar(toolbar)

        concertId = intent.getIntExtra(INTENT_TAG_ID, 0)

        initialUI()
        connectRequestData(concertId)


    }
    private fun initialUI(){
        adapter = BasicListAdapter(this, Artist.getDummyArray())
        recycler_view.adapter = adapter

        cautionAdapter = BasicListAdapter(this, Caution.getDummyArray() )
        recycler_view_caution.layoutManager = GridLayoutManager(applicationContext,3)
        recycler_view_caution.adapter = cautionAdapter


        getYouTubePlayerProvider().initialize(Secret.YOUTUBE_API_KEY,this);
        scroll_view.smoothScrollTo(0,0)


        btn_follow.setOnClickListener {
            showDialog()
        }

    }


    private fun updateArtistList(list : ArrayList<Artist>){

        // dataList로 adapter 데이터 바꿔버리기~
        // adapter notify
        adapter.notifyDataSetChanged()
        dataList.clear()
        dataList.addAll(list)
        adapter.notifyDataSetChanged()
    }

    private fun updateConcertData( concert : Concert){


        // Activity도 데이터 다시 세팅!

        // 구독하기(종) 버튼 설정


        if(URLUtil.isValidUrl(concert.backImg))
            Glide.with(this).load(concert.backImg).into(iv_back)

        if(URLUtil.isValidUrl(concert.profileImg))
            Glide.with(this).load(concert.profileImg).apply(RequestOptions.circleCropTransform()).into(iv_profile)
        tv_title.text = concert.title
        tv_tag.text  = concert.subscribeNum.toString()
    }

    private fun connectRequestData(id : Int){
        // 서버에 데이터 request보내고
        // response 데이터를 이용해
        // 전역변수로 선언되어있는 concert, dataList 업데이트

        // dataList 업데이트
        //this.dataList.clear()
        //this.dataList.addAll(list)




        val concertData : ConcertData = GetConcertResponse(ConcertData.getDummy()).data
        val c= concertData.toConcert()
        updateArtistList(ArrayList(c.artistList))

        // updateArtistData 호출
        updateConcertData(Concert.getDummy("temp"))
    }

    override fun onResume() {
        super.onResume()

        connectRequestData(concertId)
    }

     private fun showDialog(){
        val dialog = CustomDialog(this)
        dialog.show()


    }

    companion object {
        fun newInstance(): ConcertActivity = ConcertActivity()
    }
}

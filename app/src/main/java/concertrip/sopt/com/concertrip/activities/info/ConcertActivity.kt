package concertrip.sopt.com.concertrip.activities.info

import android.os.Bundle

import android.support.v7.widget.GridLayoutManager

import android.support.v7.widget.RecyclerView
import android.util.Log

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.webkit.URLUtil
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.dialog.ColorToast
import concertrip.sopt.com.concertrip.interfaces.OnItemClick
import concertrip.sopt.com.concertrip.dialog.CustomDialog
import concertrip.sopt.com.concertrip.interfaces.OnResponse
import concertrip.sopt.com.concertrip.list.adapter.BasicListAdapter
import concertrip.sopt.com.concertrip.list.adapter.SeatListAdapter
import concertrip.sopt.com.concertrip.model.Artist
import concertrip.sopt.com.concertrip.model.Caution
import concertrip.sopt.com.concertrip.model.Concert
import concertrip.sopt.com.concertrip.model.Seat
import concertrip.sopt.com.concertrip.network.ApplicationController
import concertrip.sopt.com.concertrip.network.NetworkService
import concertrip.sopt.com.concertrip.network.response.GetConcertResponse
import concertrip.sopt.com.concertrip.network.response.MessageResponse
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel
import concertrip.sopt.com.concertrip.utillity.Constants
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.INTENT_TAG_ID
import concertrip.sopt.com.concertrip.utillity.NetworkUtil
import concertrip.sopt.com.concertrip.utillity.Secret
import concertrip.sopt.com.concertrip.utillity.Secret.Companion.USER_TOKEN
import kotlinx.android.synthetic.main.activity_concert.*
import kotlinx.android.synthetic.main.content_concert.*
import kotlinx.android.synthetic.main.content_header.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ConcertActivity  : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener, OnItemClick, OnResponse {
    override fun onItemClick(root: RecyclerView.Adapter<out RecyclerView.ViewHolder>, position: Int) {
        ColorToast(this.applicationContext, "내 공연에 추가되었습니다!")
        /*TODO 하트 or 종 convert + Toast 바꾸기*/
    }

    private val RECOVERY_DIALOG_REQUEST = 1

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, youTubePlayer: YouTubePlayer?, b: Boolean) {
        if (!b && ::concert.isInitialized) {
//            val youtubeUrlList = concert.youtubeUrl!!.split("?v=")
//            youTubePlayer?.cueVideo(youtubeUrlList[youtubeUrlList.size-1])
            Log.d("~~~YOUTUBE URL : ", concert.youtubeUrl)
            youTubePlayer?.cueVideo(concert.youtubeUrl)
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
            ColorToast(this,errorMessage)
        }
    }

    private fun getYouTubePlayerProvider(): YouTubePlayer.Provider {
        return findViewById<View>(R.id.youtube) as YouTubePlayerView
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
//        if (requestCode == RECOVERY_DIALOG_REQUEST) {
//            getYouTubePlayerProvider().initialize(Secret.YOUTUBE_API_KEY, this)
//        }
//    }

    override fun onResume() {
        super.onResume()

        connectRequestData(concertId)
    }

    private var concertId: String= "5c28663f3eea39d2b003f94b"
    lateinit  var concert : Concert

    var dataListMember = arrayListOf<Artist>()
    private lateinit var memberAdapter : BasicListAdapter

    var dataListCaution = arrayListOf<Caution>()
    private lateinit var cautionAdapter : BasicListAdapter

    var dataListSeat = arrayListOf<Seat>()
    private lateinit var seatAdapter : SeatListAdapter

    private val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    public var mGlideRequestManager : RequestManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_concert)

        concertId = intent.getStringExtra(INTENT_TAG_ID)

        mGlideRequestManager = Glide.with(this)

        initialUI()
        connectRequestData(concertId)
    }

    private fun initialUI(){
        memberAdapter = BasicListAdapter(this, dataListMember, BasicListAdapter.MODE_THUMB)
        recycler_view.adapter = memberAdapter

        cautionAdapter = BasicListAdapter(this, dataListCaution)
        recycler_view_caution.layoutManager = GridLayoutManager(applicationContext,3)
        recycler_view_caution.adapter = cautionAdapter

        /*TODO have to implement memberList*/

        seatAdapter = SeatListAdapter(this, dataListSeat)
        recycler_view_seat.adapter = seatAdapter

        scroll_view.smoothScrollTo(0,0)
        scroll_view.viewTreeObserver.addOnScrollChangedListener {
            val scrollY = scroll_view.scrollY
            if(scrollY > 10 && btn_ticket.visibility == GONE){
                btn_ticket.visibility = VISIBLE
            }
            else if(scrollY <= 10 && btn_ticket.visibility == VISIBLE){
                btn_ticket.visibility = GONE
            }
        }

        btn_ticket.setOnClickListener{

        }

        btn_follow.setOnClickListener {
            NetworkUtil.subscribeConcert(networkService, this, concertId)
        }

        btn_back.setOnClickListener {
            finish()
        }
    }

    private fun updateArtistList(list : ArrayList<Artist>){
        dataListMember.clear()
        dataListMember.addAll(list)
        memberAdapter.notifyDataSetChanged()
    }

    private fun updateConcertData(){
        btn_follow.setImageDrawable(if (concert.subscribe)getDrawable(R.drawable.ic_header_heart_bell_selected)
                                    else getDrawable(R.drawable.ic_header_heart_bell_unselected))
        //iv_small_follow.setImageDrawable(if (artist.subscribe) getDrawable(R.drawable.ic_header_likes_selected) else getDrawable(R.drawable.ic_header_likes_unselected))

        // TODO 구독하기(종) 버튼 설정
        if(URLUtil.isValidUrl(concert.backImg))
            mGlideRequestManager?.load(concert.backImg)?.into(iv_back)
        if(URLUtil.isValidUrl(concert.profileImg))
            mGlideRequestManager?.load(concert.profileImg)?.apply(RequestOptions.circleCropTransform())?.into(iv_profile)
        if(URLUtil.isValidUrl(concert.eventInfoImg))
            mGlideRequestManager?.load(concert.eventInfoImg)?.into(iv_concert_info)

        tv_title.text = concert.title
        tv_tag.text  = concert.subscribeNum.toString()

        getYouTubePlayerProvider().initialize(Secret.YOUTUBE_API_KEY, this)
    }

    private fun updateCautionData(list : ArrayList<Caution>){
        dataListCaution.clear()
        dataListCaution.addAll(list)
        cautionAdapter.notifyDataSetChanged()
    }

    private fun updateSeatData(list : ArrayList<Seat>){
        dataListSeat.clear()
        dataListSeat.addAll(list)
        seatAdapter.notifyDataSetChanged()
    }

    private var LOG_TAG = "/api/concert/detail"

    private fun connectRequestData(id : String){

        progress_bar.visibility=View.VISIBLE
        val concertResponse : Call<GetConcertResponse> = networkService.getEvent(USER_TOKEN, concertId)

        concertResponse.enqueue(object : Callback<GetConcertResponse>
        {
            override fun onFailure(call: Call<GetConcertResponse>?, t: Throwable?) {
                progress_bar.visibility=View.GONE
                Log.e(Constants.LOG_NETWORK, "$LOG_TAG $t")
            }
            override fun onResponse(call: Call<GetConcertResponse>?, response: Response<GetConcertResponse>?) {

                progress_bar.visibility=View.GONE
                response?.let { res->
                    if (res.body()?.status == Secret.NETWORK_SUCCESS) {
                        Log.d(Constants.LOG_NETWORK, "$LOG_TAG :${response.body().toString()}")
                        res.body()!!.data?.let {
                            concert = it.toConcert()
                            updateArtistList(ArrayList(concert.artistList))
                            updateConcertData()
                            updateCautionData(ArrayList(concert.precaution))
                            updateSeatData(ArrayList(concert.seatList))
                        }

                    } else {
                        Log.d(Constants.LOG_NETWORK, "$LOG_TAG: fail ${response.body()?.message}")
                    }
                }

            }
        })
    }

    override fun onSuccess(obj: BaseModel, position: Int?) {
        if(obj is MessageResponse){
            concert.subscribe = !concert.subscribe

            btn_follow.setImageDrawable(if (concert.subscribe)getDrawable(R.drawable.ic_header_heart_bell_selected)
                                        else getDrawable(R.drawable.ic_header_heart_bell_unselected))
            //iv_small_follow.setImageDrawable(if (artist.subscribe) getDrawable(R.drawable.ic_header_likes_selected) else getDrawable(R.drawable.ic_header_likes_unselected))

            if (concert.subscribe)
                showDialog("캘린더에 추가했습니다")
            else
                showDialog("구독 취소했습니다")
        }
    }

    private fun showDialog(txt: String) {
        val dialog = CustomDialog(this, txt)
        dialog.show()
    }

    override fun onFail(status: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

     private fun showDialog(){
        val dialog = CustomDialog(this)
        dialog.show()
    }

    companion object {
        fun newInstance(): ConcertActivity = ConcertActivity()
    }
}

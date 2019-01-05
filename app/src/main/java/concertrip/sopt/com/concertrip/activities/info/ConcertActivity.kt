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
import com.bumptech.glide.request.RequestOptions
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.interfaces.OnItemClick
import concertrip.sopt.com.concertrip.dialog.CustomDialog
import concertrip.sopt.com.concertrip.list.adapter.BasicListAdapter
import concertrip.sopt.com.concertrip.list.adapter.SeatListAdapter
import concertrip.sopt.com.concertrip.model.Artist
import concertrip.sopt.com.concertrip.model.Caution
import concertrip.sopt.com.concertrip.model.Concert
import concertrip.sopt.com.concertrip.model.Seat
import concertrip.sopt.com.concertrip.network.ApplicationController
import concertrip.sopt.com.concertrip.network.NetworkService
import concertrip.sopt.com.concertrip.network.response.GetConcertResponse
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.INTENT_TAG_ID
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.USER_TOKEN
import concertrip.sopt.com.concertrip.utillity.Secret
import kotlinx.android.synthetic.main.activity_concert.*
import kotlinx.android.synthetic.main.content_concert.*
import kotlinx.android.synthetic.main.content_header.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ConcertActivity  : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener, OnItemClick {

    override fun onItemClick(root: RecyclerView.Adapter<out RecyclerView.ViewHolder>, position: Int) {
        Toast.makeText(this, "내 공연에 추가되었습니다!", Toast.LENGTH_LONG).show()
        /*TODO 하트 or 종 convert + Toast 바꾸기*/
    }

    private val RECOVERY_DIALOG_REQUEST = 1

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, youTubePlayer: YouTubePlayer?, b: Boolean) {
        if (!b && ::concert.isInitialized) {
            val youtubeUrlList = concert.youtubeUrl!!.split("?v=")
            youTubePlayer?.cueVideo(youtubeUrlList[youtubeUrlList.size-1])
            //youTubePlayer?.cueVideo(concert.youtubeUrl)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_concert)

        concertId = intent.getStringExtra(INTENT_TAG_ID)

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
            val scrollY = scroll_view.getScrollY() // For ScrollView
            // DO SOMETHING WITH THE SCROLL COORDINATES
            if(scrollY > 10 && btn_ticket.visibility == GONE){
                btn_ticket.visibility = VISIBLE
            }
            else if(scrollY <= 10 && btn_ticket.visibility == VISIBLE){
                btn_ticket.visibility = GONE
            }
        };

        btn_ticket.setOnClickListener{
            showDialog()
        }

        btn_follow.setOnClickListener {
            showDialog()
        }
    }

    private fun updateArtistList(list : ArrayList<Artist>){
        dataListMember.clear()
        dataListMember.addAll(list)
        memberAdapter.notifyDataSetChanged()
    }

    private fun updateConcertData(){

        // TODO 구독하기(종) 버튼 설정
        if(URLUtil.isValidUrl(concert.backImg))
            Glide.with(this).load(concert.backImg).into(iv_back)
        if(URLUtil.isValidUrl(concert.profileImg))
            Glide.with(this).load(concert.profileImg).apply(RequestOptions.circleCropTransform()).into(iv_profile)
        if(URLUtil.isValidUrl(concert.eventInfoImg))
            Glide.with(this).load(concert.eventInfoImg).into(iv_concert_info)

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

    private fun connectRequestData(id : String){
        val concertResponse : Call<GetConcertResponse> = networkService.getEvent(USER_TOKEN, concertId)

        concertResponse.enqueue(object : Callback<GetConcertResponse>
        {
            override fun onFailure(call: Call<GetConcertResponse>?, t: Throwable?) {
                Log.v("test0101", "getConcertResponse in onFailure" + t.toString())
            }
            override fun onResponse(call: Call<GetConcertResponse>?, response: Response<GetConcertResponse>?) {
                response?.let { res->
                    if (res.body()?.status == 200) {
                        res.body()!!.data?.let {
                            concert = it.toConcert()
                            updateArtistList(ArrayList(concert.artistList))
                            updateConcertData()
                            updateCautionData(ArrayList(concert.precaution))
                            updateSeatData(ArrayList(concert.seatList))
                        }

                    } else {
                        Log.v("test0101", "getConcertResponse in "+ response.body()?.status.toString())
                    }
                }

            }
        })
    }

     private fun showDialog(){
        val dialog = CustomDialog(this)
        dialog.show()
    }

    companion object {
        fun newInstance(): ConcertActivity = ConcertActivity()
    }
}

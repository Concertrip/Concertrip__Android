package concertrip.sopt.com.concertrip.activities.info

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.View
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.google.android.youtube.player.internal.v
import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.list.adapter.BasicListAdapter
import concertrip.sopt.com.concertrip.model.Artist
import concertrip.sopt.com.concertrip.model.Concert
import concertrip.sopt.com.concertrip.utillity.Secret
import kotlinx.android.synthetic.main.activity_concert.*

import kotlinx.android.synthetic.main.content_concert.*
import org.jetbrains.anko.startActivity

class ConcertActivity  : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener{

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


    //TODO OnItemClick Interface로 구현
    var onListItemClickListener : View.OnClickListener = View.OnClickListener {
        startActivity<ArtistActivity>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_concert)
//        setSupportActionBar(toolbar)

//        val mAdapter = ArtistThumbListAdapter(this, Artist.getDummyArray())
        val mAdapter = BasicListAdapter(this, Artist.getDummyArray())
        recycler_view.adapter = mAdapter


        getYouTubePlayerProvider().initialize(Secret.YOUTUBE_API_KEY,this);
        scroll_view.smoothScrollTo(0,0)
    }


    companion object {
        fun newInstance(): ConcertActivity = ConcertActivity()
    }
}

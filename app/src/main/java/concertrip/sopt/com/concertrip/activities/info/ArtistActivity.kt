package concertrip.sopt.com.concertrip.activities.info

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.View
import android.widget.ScrollView
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.R.id.recycler_view
import concertrip.sopt.com.concertrip.list.adapter.ArtistThumbListAdapter
import concertrip.sopt.com.concertrip.list.adapter.BasicListAdapter
import concertrip.sopt.com.concertrip.model.Artist
import concertrip.sopt.com.concertrip.model.Concert
import concertrip.sopt.com.concertrip.utillity.Secret
import kotlinx.android.synthetic.main.activity_artist.*

import kotlinx.android.synthetic.main.content_artist.*
import org.jetbrains.anko.startActivity

class ArtistActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener{

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

    var artist : Artist = Artist()
    var dataList = arrayListOf<Concert>() // 뭔가 서버에서 artist에 넣어서 한번에 전달해 줄듯

    //TODO OnItemClick Interface로 구현
    var onListItemClickListener : View.OnClickListener = View.OnClickListener {
        startActivity<ConcertActivity>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artist)
//        setSupportActionBar(toolbar)

//        val mAdapter = ConcertListAdapter(this, Concert.getDummyArray())
        val mAdapter = BasicListAdapter(this, Concert.getDummyArray())
        recycler_view.adapter = mAdapter


        getYouTubePlayerProvider().initialize(Secret.YOUTUBE_API_KEY,this);
        scroll_view.smoothScrollTo(0,0)
    }

    companion object {
        fun newInstance(): ArtistActivity = ArtistActivity()
    }
}

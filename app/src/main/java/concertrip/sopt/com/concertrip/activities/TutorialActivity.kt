package concertrip.sopt.com.concertrip.activities

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet
import android.util.Log
import android.util.Log.d
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.activities.main.MainActivity
import concertrip.sopt.com.concertrip.interfaces.OnResponse
import concertrip.sopt.com.concertrip.network.ApplicationController
import concertrip.sopt.com.concertrip.network.NetworkService
import concertrip.sopt.com.concertrip.network.response.GetSubscribedResponse
import concertrip.sopt.com.concertrip.network.response.MessageResponse
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.TYPE_GENRE
import concertrip.sopt.com.concertrip.utillity.NetworkUtil
import concertrip.sopt.com.concertrip.utillity.NetworkUtil.Companion.getSubscribedList
import concertrip.sopt.com.concertrip.utillity.Secret.Companion.GENRE_BALADRAB
import concertrip.sopt.com.concertrip.utillity.Secret.Companion.GENRE_EDM
import concertrip.sopt.com.concertrip.utillity.Secret.Companion.GENRE_HIPHOP
import concertrip.sopt.com.concertrip.utillity.Secret.Companion.GENRE_IDOL
import concertrip.sopt.com.concertrip.utillity.Secret.Companion.GENRE_INDI
import concertrip.sopt.com.concertrip.utillity.Secret.Companion.GENRE_JAZZ
import concertrip.sopt.com.concertrip.utillity.Secret.Companion.GENRE_KOREA
import concertrip.sopt.com.concertrip.utillity.Secret.Companion.GENRE_MUSICPES
import concertrip.sopt.com.concertrip.utillity.Secret.Companion.GENRE_ROCK

import kotlinx.android.synthetic.main.activity_tutorial.*
import org.jetbrains.anko.startActivity
import retrofit2.Call

class TutorialActivity : AppCompatActivity(), OnResponse {

    var clickTest = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
    var genreId = arrayOf(GENRE_MUSICPES, GENRE_IDOL, GENRE_HIPHOP, GENRE_BALADRAB,
        GENRE_KOREA, GENRE_EDM, GENRE_INDI, GENRE_JAZZ, GENRE_ROCK)

    lateinit var btnList : Array<LinearLayout>

    lateinit var ivSet : Array<RelativeLayout>

    lateinit var ivUnset : Array<RelativeLayout>
    //genreId넣어주기 인트면 다 바꿔줘야함
    // var genreList = arrayOf(1,2,3,4,5,6,7,8,9)

    var genreList = arrayListOf<String>()

    private val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        btnList = arrayOf(
            btn_tutorial_1, btn_tutorial_2, btn_tutorial_3, btn_tutorial_4,
            btn_tutorial_5, btn_tutorial_6, btn_tutorial_7, btn_tutorial_8, btn_tutorial_9
        )

        ivSet = arrayOf(
            iv_tutorial_click_1, iv_tutorial_click_2, iv_tutorial_click_3, iv_tutorial_click_4,
            iv_tutorial_click_5, iv_tutorial_click_6, iv_tutorial_click_7, iv_tutorial_click_8, iv_tutorial_click_9
        )

        ivUnset = arrayOf(
            iv_tutorial_noclick_1, iv_tutorial_noclick_2, iv_tutorial_noclick_3, iv_tutorial_noclick_4,
            iv_tutorial_noclick_5, iv_tutorial_noclick_6, iv_tutorial_noclick_7, iv_tutorial_noclick_8, iv_tutorial_noclick_9
        )

        InitialUI()
    }

    override fun onCreateView(name: String?, context: Context?, attrs: AttributeSet?): View? {
        return super.onCreateView(name, context, attrs)
    }

    private fun updateUI() {

        if (clickTest.sum() > 0) {
            btn_skip.text = "구독하기"
            iv_tutorial_heart_empty.visibility = View.GONE
            iv_tutorial_heart_activated.visibility = View.VISIBLE
        } else {
            btn_skip.text = "건너뛰기"
            iv_tutorial_heart_activated.visibility = View.GONE
            iv_tutorial_heart_empty.visibility = View.VISIBLE
        }
    }

//    private fun connectRequestData(i : Int) {
//        if (clickTest[i] == 1) {
//            if (genreId.lastIndex >= i)
//                NetworkUtil.subscribeGenre(networkService, this, genreId[i], i)
//        }else{
//            connectRequestData(i+1)
//        }
//    }

    private fun connectRequestData() {
        //이걸 각 버튼 누를때마다 통신을 할지 구독하기 버튼을 누르면 한꺼번에 통신을 시킬지 모르겠음
//        var genreId: String = ""
//
//        genreList.forEach {
//            genreId = it
//            NetworkUtil.subscribeGenre(networkService, this, genreId)
//        }

        for (i in 0 until btnList.size){
            if(clickTest[i] == 1) NetworkUtil.subscribeGenre(networkService, this, genreId[i])
        }

    }

    override fun onSuccess(obj: BaseModel, position: Int?) {


    }

    override fun onFail(status: Int) {

    }

    private fun InitialUI() {
        btn_skip.setOnClickListener {
            connectRequestData()
            startActivity<MainActivity>()

            Handler().postDelayed({
                finish()
            },10000)

        }

        for (i in 0..btnList.size - 1) {
            btnList[i].setOnClickListener {
                clickTest[i] = 1 - clickTest[i]

                if (clickTest[i] == 1) {
                    ivSet[i].visibility = View.VISIBLE
                    ivUnset[i].visibility = View.GONE
                } else {
                    ivSet[i].visibility = View.GONE
                    ivUnset[i].visibility = View.VISIBLE
                }

                //genreList에 아이디 넣어주기
                updateUI()
            }
        }

        btn_tutorial_2.setOnClickListener{
            clickTest[1] = 1-clickTest[1]

//        btn_tutorial_1.setOnClickListener{
//            clickTest[0] = 1-clickTest[0]
//
//            if(clickTest[0] == 1)
//            {iv_tutorial_click_1.visibility = View.VISIBLE
//            iv_tutorial_noclick_1.visibility = View.GONE}
//            else
//            {iv_tutorial_click_1.visibility = View.GONE
//                iv_tutorial_noclick_1.visibility =View.VISIBLE}
//
//            //genreList에 아이디 넣어주기
//
//            updateUI()
//
//        }
//
//        btn_tutorial_2.setOnClickListener{
//            clickTest[1] = 1-clickTest[1]
//
//            if(clickTest[1] == 1)
//            {iv_tutorial_click_2.visibility = View.VISIBLE
//                iv_tutorial_noclick_2.visibility = View.GONE}
//            else
//            {iv_tutorial_click_2.visibility = View.GONE
//                iv_tutorial_noclick_2.visibility =View.VISIBLE}
//
//            updateUI()
//        }
//
//
//        btn_tutorial_3.setOnClickListener{
//            clickTest[2] = 1-clickTest[2]
//
//            if(clickTest[2] == 1)
//            {iv_tutorial_click_3.visibility = View.VISIBLE
//                iv_tutorial_noclick_3.visibility = View.GONE}
//            else
//            {iv_tutorial_click_3.visibility = View.GONE
//                iv_tutorial_noclick_3.visibility =View.VISIBLE}
//
//            updateUI()
//        }
//
//        btn_tutorial_4.setOnClickListener{
//            clickTest[3] = 1-clickTest[3]
//
//            if(clickTest[3] == 1)
//            {iv_tutorial_click_4.visibility = View.VISIBLE
//                iv_tutorial_noclick_4.visibility = View.GONE}
//            else
//            {iv_tutorial_click_4.visibility = View.GONE
//                iv_tutorial_noclick_4.visibility =View.VISIBLE}
//
//            updateUI()
//        }
//
//
//        btn_tutorial_5.setOnClickListener{
//            clickTest[4] = 1-clickTest[4]
//
//            if(clickTest[4] == 1)
//            {iv_tutorial_click_5.visibility = View.VISIBLE
//                iv_tutorial_noclick_5.visibility = View.GONE}
//            else
//            {iv_tutorial_click_5.visibility = View.GONE
//                iv_tutorial_noclick_5.visibility =View.VISIBLE}
//
//            updateUI()
//        }
//
//
//        btn_tutorial_6.setOnClickListener{
//            clickTest[5] = 1-clickTest[5]
//
//            if(clickTest[5] == 1)
//            {iv_tutorial_click_6.visibility = View.VISIBLE
//                iv_tutorial_noclick_6.visibility = View.GONE}
//            else
//            {iv_tutorial_click_6.visibility = View.GONE
//                iv_tutorial_noclick_6.visibility =View.VISIBLE}
//
//            updateUI()
//        }
//
//
//        btn_tutorial_7.setOnClickListener{
//            clickTest[6] = 1-clickTest[6]
//
//            if(clickTest[6] == 1)
//            {iv_tutorial_click_7.visibility = View.VISIBLE
//                iv_tutorial_noclick_7.visibility = View.GONE}
//            else
//            {iv_tutorial_click_7.visibility = View.GONE
//                iv_tutorial_noclick_7.visibility =View.VISIBLE}
//
//        }
//
//
//        btn_tutorial_8.setOnClickListener{
//            clickTest[7] = 1-clickTest[7]
//
//            if(clickTest[7] == 1)
//            {iv_tutorial_click_8.visibility = View.VISIBLE
//                iv_tutorial_noclick_8.visibility = View.GONE}
//            else
//            {iv_tutorial_click_8.visibility = View.GONE
//                iv_tutorial_noclick_8.visibility =View.VISIBLE}
//
//            updateUI()
//        }
//
//
//        btn_tutorial_9.setOnClickListener{
//            clickTest[8] = 1-clickTest[8]
//
//            if(clickTest[8] == 1)
//            {iv_tutorial_click_9.visibility = View.VISIBLE
//                iv_tutorial_noclick_9.visibility = View.GONE}
//            else
//            {iv_tutorial_click_9.visibility = View.GONE
//                iv_tutorial_noclick_9.visibility =View.VISIBLE}
//
//            updateUI()
//        }

    }

}

package concertrip.sopt.com.concertrip.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity;
import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.activities.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*
import android.os.Looper.loop
import android.util.Log
import android.view.View
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.iid.FirebaseInstanceId


class SplashActivity : AppCompatActivity() {

    var c : Boolean =false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
//        setSupportActionBar(toolbar)

        Log.d("~~~~Firebase TOKEN" ,FirebaseInstanceId.getInstance().token)


        val animationView = findViewById<View>(R.id.animation_view) as LottieAnimationView
        animationView.imageAssetsFolder ="images"
        animationView.setAnimation("data.json")
        animationView.loop(true)
        animationView.playAnimation()

        splash_icon.setOnClickListener {
            c=true
            val i = Intent(applicationContext, TutorialActivity::class.java)
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
            startActivity(i)
            finish()
        }
        Handler().postDelayed({
            if(c) return@postDelayed
            val i = Intent(applicationContext, MainActivity::class.java)
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
            startActivity(i)
            finish()
        }, 3000)
    }
}

package concertrip.sopt.com.concertrip.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity;
import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.activities.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity() {

    var c : Boolean =false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
//        setSupportActionBar(toolbar)


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
        }, 1000)
    }
}

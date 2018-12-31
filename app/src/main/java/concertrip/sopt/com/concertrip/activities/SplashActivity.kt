package concertrip.sopt.com.concertrip.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity;
import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.activities.main.MainActivity


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
//        setSupportActionBar(toolbar)


        Handler().postDelayed({
            val i = Intent(applicationContext, MainActivity::class.java)
//            overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_down)
            startActivity(i)
            finish()
        }, 3000)
    }
}

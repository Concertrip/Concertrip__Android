package concertrip.sopt.com.concertrip.activities

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet
import android.view.View
import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.activities.main.MainActivity

import kotlinx.android.synthetic.main.activity_tutorial.*
import org.jetbrains.anko.startActivity

class TutorialActivity : AppCompatActivity() {

    var clickTest = arrayOf(0,0,0,0,0,0,0,0,0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)
        InitialUI()
    }

    override fun onCreateView(name: String?, context: Context?, attrs: AttributeSet?): View? {
        return super.onCreateView(name, context, attrs)
    }

    private fun InitialUI(){
        btn_skip.setOnClickListener {
            startActivity<MainActivity>()
        }

        btn_tutorial_1.setOnClickListener{
            clickTest[0] = 1-clickTest[0]

            if(clickTest[0] == 1)
            {iv_tutorial_click_1.visibility = View.VISIBLE
            iv_tutorial_noclick_1.visibility = View.GONE}
            else
            {iv_tutorial_click_1.visibility = View.GONE
                iv_tutorial_noclick_1.visibility =View.VISIBLE}

        }



    }

}

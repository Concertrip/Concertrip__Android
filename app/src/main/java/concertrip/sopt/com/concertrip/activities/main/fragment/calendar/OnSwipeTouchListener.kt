package concertrip.sopt.com.concertrip.activities.main.fragment.calendar

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import kotlin.properties.Delegates
import java.lang.Exception


public class OnSwipeTouchListener(mContext : Context) : View.OnTouchListener{
    private var gestureDetector : GestureDetector= GestureDetector(mContext, GestureListener())
    var mLastOnDownEvent  : MotionEvent?  = null

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return gestureDetector.onTouchEvent(event)
    }

    fun onSwipeRight() {
        Log.d("!!!!!", "onSwipeRight")
    }

    fun onSwipeLeft() {
        Log.d("!!!!!", "onSwipeLeft")
    }

    inner class GestureListener : GestureDetector.SimpleOnGestureListener(){
        val SWIPE_THRESHOLD = 100
        val SWIPE_VELOCITY_THRESHOLD = 100

        public override fun onDown(e : MotionEvent) : Boolean {
            mLastOnDownEvent = e
            Log.d("!!!!", "onDown e : " +e)
            return true
        }

        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
            //return super.onFling(e1, e2, velocityX, velocityY)

            var result : Boolean = false

            try {
                if(mLastOnDownEvent==null || e2==null) return false

                var e18 :MotionEvent= e1 ?: mLastOnDownEvent!!

                val diffY : Float = e2.y - e18.y
                val diffX : Float =  e2.x - e18.x

                Log.d("!!!!", "e18 : " +e18.toString() + "e2 : " + e2.toString())

                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                        result = true;
                    }
                }
            } catch (exception : Exception){
                exception.printStackTrace()
            }
            return result
        }
    }

}
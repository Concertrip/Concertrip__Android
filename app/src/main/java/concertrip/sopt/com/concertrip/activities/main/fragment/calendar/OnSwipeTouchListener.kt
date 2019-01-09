package concertrip.sopt.com.concertrip.activities.main.fragment.calendar

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import kotlin.properties.Delegates
import java.lang.Exception
import android.text.method.Touch.onTouchEvent
import concertrip.sopt.com.concertrip.interfaces.OnFling
import java.util.*


public class OnSwipeTouchListener(mContext : Context, var listener: OnFling) : View.OnTouchListener{
    private var gestureDetector : GestureDetector= GestureDetector(mContext, GestureListener())

    var mLastOnDownEvent : MotionEvent = MotionEvent.obtain(1, 1, MotionEvent.ACTION_DOWN, -5.0f, -5.0f, 1)
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        //Log.d("!!!!!", "onTouch")

        return gestureDetector.onTouchEvent(event)
    }

    public fun getGestureDetector() : GestureDetector{
        return gestureDetector
    }

//    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
//        Log.d("!!!!!", "dispatchTouchEvent")
//        return super.dispatchTouchEvent(ev)
//    }
//
//    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
//        Log.d("!!!!!", "onInterceptTouchEvnet")
//        return isTouchIntercept
////        return  if(gestureDetector.onTouchEvent(event)) true
////                else super.onInterceptTouchEvent(event)
//    }
//
//    override fun onTouchEvent(event: MotionEvent): Boolean {
//        return  if (gestureDetector.onTouchEvent(event)) true
//                else super.onTouchEvent(event)
//
//        when(action){
//            1-> {
//                onSwipeRight()
//                isTouchIntercept = false
//                return true
//            }
//            2-> {
//                onSwipeLeft()
//                isTouchIntercept = false
//                return true
//            }
//        }
//        return false
//    }

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
                Log.d("!!!!", "onFling 1 e2 : " + e2.toString())

                e2?.let{

                }

                if(mLastOnDownEvent==null || e2==null) return false

                var e18 :MotionEvent= e1 ?: mLastOnDownEvent!!

                val diffY : Float = e2.y - e18.y
                val diffX : Float =  e2.x - e18.x

                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            listener.onSwipeRight()
                        } else {
                            listener.onSwipeLeft()
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
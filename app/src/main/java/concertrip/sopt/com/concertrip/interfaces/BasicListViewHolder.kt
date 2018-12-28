package concertrip.sopt.com.concertrip.interfaces

import android.view.View
import android.widget.ImageView
import android.widget.TextView

interface BasicListViewHolder {
    fun getMainTitle() : TextView
    fun getSubTitle() : TextView?
    fun getBtn() : View?
    fun getIvIcon():ImageView
}
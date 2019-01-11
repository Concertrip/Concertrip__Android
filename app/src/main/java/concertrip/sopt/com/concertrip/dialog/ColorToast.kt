package concertrip.sopt.com.concertrip.dialog

import android.content.Context
import android.support.v4.content.ContextCompat
import android.widget.TextView
import android.widget.Toast
import concertrip.sopt.com.concertrip.R
import kotlin.properties.Delegates
import android.view.LayoutInflater
import android.view.ViewGroup
import org.jetbrains.anko.layoutInflater


class ColorToast(mContext: Context, txt: String) {

    private var toast: Toast by Delegates.notNull()

    init {
        toast = Toast.makeText(mContext, txt, Toast.LENGTH_LONG)
        val toastView = toast.view



        toastView.setBackgroundResource(R.drawable.shape_toast)
        val tv = toastView.findViewById<TextView>(android.R.id.message)
        tv.includeFontPadding=false
        tv.setTextColor(ContextCompat.getColor(mContext, R.color.white))
        tv.setPadding(
            mContext.resources.getDimensionPixelSize(R.dimen.toast_left_padding),
            mContext.resources.getDimensionPixelSize(R.dimen.toast_top_padding),
            mContext.resources.getDimensionPixelSize(R.dimen.toast_right_padding),
            mContext.resources.getDimensionPixelSize(R.dimen.toast_bottom_padding)
        )

        toast.setMargin(0.0f,0.1f)

        toast.show()
    }
}
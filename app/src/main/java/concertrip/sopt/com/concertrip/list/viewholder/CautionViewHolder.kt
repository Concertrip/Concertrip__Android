package concertrip.sopt.com.concertrip.list.viewholder

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.interfaces.BasicListViewHolder


class CautionViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) ,BasicListViewHolder{
    override fun getMainTitle(): TextView = tvcaution

    override fun getSubTitle(): TextView? = null

    override fun getBtn(): View? =null

    override fun getIvIcon(): ImageView = ivcaution


    override fun setButton(context :Context, b: Boolean?) {

    }

    var ivcaution :ImageView = itemView.findViewById(R.id.iv_caution) as ImageView
    var tvcaution :TextView =itemView.findViewById(R.id.tv_caution) as TextView
}
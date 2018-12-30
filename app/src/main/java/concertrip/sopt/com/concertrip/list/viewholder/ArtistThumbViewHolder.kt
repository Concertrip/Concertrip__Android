package concertrip.sopt.com.concertrip.list.viewholder

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextClock
import android.widget.TextView
import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.interfaces.BasicListViewHolder

class ArtistThumbViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) , BasicListViewHolder{

    override fun getMainTitle(): TextView =tvArtist

    override fun getSubTitle(): TextView?=null
    override fun getBtn(): View? =null
    override fun getIvIcon():ImageView =ivArtist

    override fun setButton(context : Context, b: Boolean?) {  }


    var ivArtist : ImageView = itemView.findViewById<ImageView>(R.id.iv_artist) as ImageView
    var tvArtist : TextView = itemView.findViewById<TextView>(R.id.tv_artist) as TextView

}
package concertrip.sopt.com.concertrip.list.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.interfaces.BasicListViewHolder

class ArtistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) , BasicListViewHolder{
    override fun getMainTitle(): TextView =tvArtist
    override fun getSubTitle(): TextView? =tvTag
    override fun getBtn(): View? =btnFollow
    override fun getIvIcon(): ImageView =ivArtist

    val tvArtist : TextView = itemView.findViewById(R.id.tv_artist)
    val tvTag : TextView = itemView.findViewById(R.id.tv_artist_tag)
    val ivArtist : ImageView = itemView.findViewById(R.id.iv_artist)
    val btnFollow : ImageView = itemView.findViewById(R.id.btn_follow)

}
package concertrip.sopt.com.concertrip.list.viewholder

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.interfaces.BasicListViewHolder

class ConcertViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)  ,BasicListViewHolder{
    override fun getMainTitle(): TextView =tvname

    override fun getSubTitle(): TextView? =tvTag

    override fun getBtn(): View? =ivLike

    override fun getIvIcon(): ImageView =ivConcert



    override fun setButton(context : Context, b: Boolean?) {
        ivLike.setImageDrawable(if(b==true) ContextCompat.getDrawable(context,R.drawable.ic_like)
        else ContextCompat.getDrawable(context,R.drawable.ic_unlike))
    }



    val ivConcert: ImageView = itemView.findViewById(R.id.iv_concert) as ImageView
    val tvname : TextView = itemView.findViewById(R.id.tv_concert) as TextView
    val tvTag : TextView = itemView.findViewById(R.id.tv_concert_tag) as TextView
    val ivLike : ImageButton = itemView.findViewById(R.id.btn_like) as ImageButton
}
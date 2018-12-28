package concertrip.sopt.com.concertrip.list.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.R.id.parent
import concertrip.sopt.com.concertrip.list.viewholder.ArtistThumbViewHolder
import concertrip.sopt.com.concertrip.list.viewholder.TicketViewHolder
import concertrip.sopt.com.concertrip.model.Artist
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import concertrip.sopt.com.concertrip.activities.info.ArtistActivity
import org.jetbrains.anko.startActivity

class ArtistThumbListAdapter(var mContext : Context, var dataList: ArrayList<Artist>) : RecyclerView.Adapter<ArtistThumbViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistThumbViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.li_artist_thumb, parent, false)
        return ArtistThumbViewHolder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ArtistThumbViewHolder, position: Int) {
        Glide.with(mContext).load(dataList.get(position).profileImg).apply(RequestOptions.circleCropTransform()).into(holder!!.ivArtist)
        holder.tvArtist.text = dataList.get(position).name
        holder.ivArtist.setOnClickListener{
            /*TODO
            * 클릭시 해당 아티스트의 디테일 뷰로 이동*/
        }

        holder.itemView.setOnClickListener {
            mContext.startActivity<ArtistActivity>()
        }
    }
}
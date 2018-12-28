package concertrip.sopt.com.concertrip.list.adapter

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Message
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.activities.info.ArtistActivity
import concertrip.sopt.com.concertrip.activities.info.ConcertActivity
import concertrip.sopt.com.concertrip.interfaces.BasicListViewHolder
import concertrip.sopt.com.concertrip.interfaces.ListData
import concertrip.sopt.com.concertrip.interfaces.OnItemClick
import concertrip.sopt.com.concertrip.list.viewholder.*
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.TYPE_ALARM
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.TYPE_ARTIST
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.TYPE_CONCERT
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.TYPE_THEME
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.TYPE_TICKET
import org.jetbrains.anko.startActivity

class BasicListAdapter(var mContext : Context, var dataList: ArrayList<out ListData>, var mode : Int, var listener : OnItemClick?) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    constructor(mContext: Context,dataList: ArrayList<out ListData>,mode: Int) : this(mContext,dataList,mode,null)
    constructor(mContext: Context,dataList: ArrayList<out ListData>,listener: OnItemClick?) : this(mContext,dataList, MODE_BASIC,listener)
    constructor(mContext: Context, dataList: ArrayList<out ListData>):this(mContext,dataList, MODE_BASIC,null)
    companion object {
        val MODE_BASIC = 0
        val MODE_THUMB =1
    }

    override fun getItemViewType(position: Int): Int {
        return dataList[position].getType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when(viewType){
            TYPE_ARTIST->{
                return when(mode){
                    MODE_BASIC->{
                        val view = LayoutInflater.from(mContext).inflate(R.layout.li_artist, parent, false)
                        ArtistViewHolder(view)
                    }
                    MODE_THUMB->{
                        val view = LayoutInflater.from(mContext).inflate(R.layout.li_artist_thumb, parent, false)
                        ArtistThumbViewHolder(view)
                    }
                    else->{
                        throw RuntimeException(mContext.toString() + " mode is strange number $mode")
                    }
                }
            }
            TYPE_CONCERT->{
                val view = LayoutInflater.from(mContext).inflate(R.layout.li_concert, parent, false)
                return ConcertViewHolder(view)
            }
//            TYPE_THEME ->{
//
//            }

            TYPE_TICKET -> {
                val view = LayoutInflater.from(mContext).inflate(R.layout.li_ticket, parent, false)
                return TicketViewHolder(view)
            }

            TYPE_ALARM -> {
                val view = LayoutInflater.from(mContext).inflate(R.layout.li_alarm, parent, false)
                return ConcertViewHolder(view)
            }
            else->{
                throw RuntimeException(mContext.toString() + " type is strange number $viewType")
            }
        }
    }

    override fun getItemCount(): Int = dataList.size

    var h : Handler? =null
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        val basicHolder = holder as BasicListViewHolder
        basicHolder.getMainTitle().text=dataList[position].getMainTitle()
        basicHolder.getSubTitle()?.text=dataList[position].getSubTitle()
        if( URLUtil.isValidUrl(dataList[position].getImageUrl())){
            Glide.with(mContext).load(dataList[position].getImageUrl()).apply(RequestOptions.circleCropTransform())
                .into(holder.getIvIcon())
        }

        holder.itemView.setOnClickListener {

            when (getItemViewType(position)) {
                TYPE_ARTIST -> {
                    val intent: Intent = Intent(mContext.applicationContext, ArtistActivity::class.java)
                    intent.putExtra("idx",dataList[position].getIndex())
                    mContext.startActivity(intent)
                }
                TYPE_CONCERT-> {

                    val intent: Intent = Intent(mContext.applicationContext, ConcertActivity::class.java)
                    intent.putExtra("idx",dataList[position].getIndex())
                    mContext.startActivity(intent)
                }

            }
        }
        basicHolder.getBtn()?.setOnClickListener{
            listener?.onItemClick(this,position)

        }

    }

}
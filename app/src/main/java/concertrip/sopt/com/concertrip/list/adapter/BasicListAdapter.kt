package concertrip.sopt.com.concertrip.list.adapter

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.activities.info.ArtistActivity
import concertrip.sopt.com.concertrip.activities.info.ConcertActivity
import concertrip.sopt.com.concertrip.dialog.ColorToast
import concertrip.sopt.com.concertrip.interfaces.BasicListViewHolder
import concertrip.sopt.com.concertrip.interfaces.ListData
import concertrip.sopt.com.concertrip.interfaces.OnItemClick
import concertrip.sopt.com.concertrip.interfaces.OnResponse
import concertrip.sopt.com.concertrip.list.viewholder.*
import concertrip.sopt.com.concertrip.model.Artist
import concertrip.sopt.com.concertrip.model.Concert
import concertrip.sopt.com.concertrip.model.Genre
import concertrip.sopt.com.concertrip.network.ApplicationController
import concertrip.sopt.com.concertrip.network.NetworkService
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.INTENT_ARTIST
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.INTENT_TAG_ID
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.TYPE_ALARM
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.TYPE_ARTIST
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.TYPE_CAUTION
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.TYPE_CONCERT
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.TYPE_GENRE
import concertrip.sopt.com.concertrip.utillity.Constants.Companion.TYPE_TICKET
import concertrip.sopt.com.concertrip.utillity.NetworkUtil

class BasicListAdapter(
    private var mContext: Context,
    var dataList: ArrayList<out ListData>,
    var mode: Int?= MODE_BASIC,
    var listener: OnItemClick?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), OnResponse {
    override fun onSuccess(obj: BaseModel, position: Int?) {
        position?.let {
            when (getItemViewType(position)) {
                TYPE_ARTIST -> {

                    val artist = dataList[position] as Artist
                    artist.subscribe = !artist.subscribe

                    ColorToast(mContext,obj.message?:"")
                    notifyDataSetChanged()

                }
                TYPE_CONCERT -> {
                    val concert = dataList[position] as Concert
                    concert.subscribe = !concert.subscribe


                    ColorToast(mContext,obj.message?:"")

                    notifyDataSetChanged()
                }
                TYPE_GENRE -> {
                    val genre = dataList[position] as Genre
                    genre.subscribe = !genre.subscribe

                    ColorToast(mContext,obj.message?:"")
                    notifyDataSetChanged()
                }

            }
        }
    }

    override fun onFail(status: Int) {
        ColorToast(mContext.applicationContext, "인터넷을 다시 확인해주세요.")
    }

    private val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }


    constructor(mContext: Context, dataList: ArrayList<out ListData>, mode: Int) : this(mContext, dataList, mode, null)
    constructor(mContext: Context, dataList: ArrayList<out ListData>, listener: OnItemClick?) : this(
        mContext,
        dataList,
        MODE_BASIC,
        listener
    )

    constructor(mContext: Context, dataList: ArrayList<out ListData>) : this(mContext, dataList, MODE_BASIC, null)

    companion object {
        const val MODE_BASIC = 0
        const val MODE_THUMB = 1
    }

    override fun getItemViewType(position: Int): Int {
        return dataList[position].getType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when (viewType) {
            TYPE_ARTIST -> {
                return when (mode) {
                    MODE_THUMB -> {
                        val view = LayoutInflater.from(mContext).inflate(R.layout.li_artist_thumb, parent, false)
                        ArtistThumbViewHolder(view)
                    }
                    else->{//MODE_BASIC -> {
                        val view = LayoutInflater.from(mContext).inflate(R.layout.li_artist, parent, false)
                        ArtistViewHolder(view)
                    }
                }
            }
            TYPE_CONCERT -> {
                val view = LayoutInflater.from(mContext).inflate(R.layout.li_concert, parent, false)
                return ConcertViewHolder(view)
            }
            TYPE_GENRE -> {
                val view = LayoutInflater.from(mContext).inflate(R.layout.li_artist, parent, false)
                return ArtistViewHolder(view)
            }

            TYPE_TICKET -> {
                val view = LayoutInflater.from(mContext).inflate(R.layout.li_ticket, parent, false)
                return TicketViewHolder(view)
            }

            TYPE_ALARM -> {
                val view = LayoutInflater.from(mContext).inflate(R.layout.li_alarm, parent, false)
                return AlarmViewHolder(view)
            }
            TYPE_CAUTION -> {
                val view = LayoutInflater.from(mContext).inflate(R.layout.li_caution, parent, false)
                return CautionViewHolder(view)
            }
            else -> {
                throw RuntimeException(mContext.toString() + " type is strange number $viewType")
            }
        }
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        val basicHolder = holder as BasicListViewHolder
        basicHolder.getMainTitle().text = dataList[position].getMainTitle()
        basicHolder.getSubTitle()?.text = dataList[position].getSubTitle()
        if (URLUtil.isValidUrl(dataList[position].getImageUrl())) {
            /*TODO 터지면 GlideRequestManager 달기*/
            Glide.with(mContext).load(dataList[position].getImageUrl()).apply(RequestOptions.circleCropTransform())
                .into(holder.getIvIcon())
        } else {
            holder.getIvIcon().setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_account_circle))
        }
        basicHolder.setButton(mContext, dataList[position].isSubscribe())


        holder.itemView.setOnClickListener {

            when (getItemViewType(position)){
                TYPE_ARTIST -> {
                    val intent: Intent = Intent(mContext.applicationContext, ArtistActivity::class.java)
                    intent.putExtra(INTENT_TAG_ID, dataList[position].getId())
                    intent.putExtra(INTENT_ARTIST, TYPE_ARTIST)
                    mContext.startActivity(intent)
                }
                TYPE_GENRE -> {
                    val intent: Intent = Intent(mContext.applicationContext, ArtistActivity::class.java)
                    intent.putExtra(INTENT_TAG_ID, dataList[position].getId())
                    intent.putExtra(INTENT_ARTIST, TYPE_GENRE)
                    mContext.startActivity(intent)
                }
                TYPE_CONCERT -> {

                    val intent: Intent = Intent(mContext.applicationContext, ConcertActivity::class.java)
                    intent.putExtra(INTENT_TAG_ID, dataList[position].getId())
                    mContext.startActivity(intent)
                }
            }
        }

        basicHolder.getBtn()?.setOnClickListener {
            when (getItemViewType(position)) {
                TYPE_ARTIST -> {
                    NetworkUtil.subscribeArtist(networkService, this, dataList[position].getId(), position)
                }

                TYPE_CONCERT -> {
                    NetworkUtil.subscribeConcert(networkService, this, dataList[position].getId(), position)
                }
                TYPE_GENRE -> {
                    NetworkUtil.subscribeGenre(networkService, this, dataList[position].getId(), position)
                }
            }
        }


    }

}
package concertrip.sopt.com.concertrip.list.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.interfaces.OnItemClick
import concertrip.sopt.com.concertrip.list.viewholder.HorizontalViewHolder


class HorizontalListAdapter(
    private val mContext: Context,
    var dataList: ArrayList<String>,
    var listener: OnItemClick?,
    var isUnderline: Boolean
) : RecyclerView.Adapter<HorizontalViewHolder>() {
    var selected: Int = 0

    constructor(mContext: Context, dataList: ArrayList<String>) : this(mContext, dataList, null, true)
    constructor(mContext: Context, dataList: ArrayList<String>, listener: OnItemClick?) : this(
        mContext,
        dataList,
        listener,
        true
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalViewHolder {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.li_tag, parent, false)
        return HorizontalViewHolder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: HorizontalViewHolder, position: Int) {
        holder.vUnderline.visibility = if (isUnderline && position == selected) View.VISIBLE else View.GONE

        if(position == selected){
            holder.tvtext.typeface = Typeface.DEFAULT_BOLD
            holder.tvtext.setTextColor(ContextCompat.getColor(mContext,R.color.white))
        }else{
            holder.tvtext.typeface = Typeface.DEFAULT
            holder.tvtext.setTextColor(ContextCompat.getColor(mContext, R.color.grayDark))
        }

        holder.tvtext.text = dataList[position]

        holder.itemView.setOnClickListener {
            listener?.onItemClick(this, position)
        }
    }

    fun setSelect(position: Int) {
        Log.v("1227", position.toString())
        this.selected = position
        notifyDataSetChanged()
    }
}
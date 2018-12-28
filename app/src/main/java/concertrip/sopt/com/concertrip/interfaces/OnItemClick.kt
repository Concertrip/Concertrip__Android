package concertrip.sopt.com.concertrip.interfaces

import android.support.v7.widget.RecyclerView

interface OnItemClick {
    fun onItemClick(root : RecyclerView.Adapter<out RecyclerView.ViewHolder>,idx: Int)
}
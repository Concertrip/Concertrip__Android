package concertrip.sopt.com.concertrip.activities.main.fragment.mypage

import android.content.Context
import android.graphics.drawable.Drawable
import android.icu.util.ValueIterator
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.R.id.*
import concertrip.sopt.com.concertrip.dialog.ColorToast
import concertrip.sopt.com.concertrip.interfaces.OnFragmentInteractionListener
import concertrip.sopt.com.concertrip.interfaces.OnItemClick
import concertrip.sopt.com.concertrip.interfaces.OnResponse
import concertrip.sopt.com.concertrip.list.adapter.TicketListAdapter

import concertrip.sopt.com.concertrip.utillity.Constants
import concertrip.sopt.com.concertrip.model.Ticket
import concertrip.sopt.com.concertrip.network.ApplicationController
import concertrip.sopt.com.concertrip.network.NetworkService
import concertrip.sopt.com.concertrip.network.response.GetTicketListResponse
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel
import concertrip.sopt.com.concertrip.utillity.NetworkUtil
import concertrip.sopt.com.concertrip.utillity.Secret
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_my_page.*

class MyPageFragment : Fragment(), OnItemClick, OnFragmentInteractionListener, OnResponse {


    private var listener: OnFragmentInteractionListener? = null

    private val networkServicce: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    var mGlideRequestManager: RequestManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mGlideRequestManager = Glide.with(this)

        initialUI()

    }


    override fun changeFragment(what: Int) {
        listener?.changeFragment(what)
    }


    private fun initialUI() {
//        btn_setting.setOnClickListener {
//            listener?.changeFragment(Constants.FRAGMENT_TICKET_LIST)
//        }

        tv_more_info.setOnClickListener {
            listener?.changeFragment(Constants.FRAGMENT_TICKET_LIST)
        }

        btn_setting.setOnClickListener {
            listener?.changeFragment(Constants.FRAGMENT_SETTING)
        }

        activity?.let {

            activity?.progress_bar?.visibility = View.VISIBLE
            NetworkUtil.getTicketList(networkServicce, this, "")
        }

    }

    override fun onSuccess(obj: BaseModel, position: Int?) {
        activity?.progress_bar?.visibility = View.GONE

        if (obj is GetTicketListResponse) {
            val responseBody = obj as GetTicketListResponse

            responseBody.let {
                val tickList = it.toTicketList()

                if (tickList.isNotEmpty()) {

                    val ticketInfo = tickList[0]
                    //tv_ticket_title.text = ticketInfo.name
                    //tv_ticket_place.text = ticketInfo.location
                    //tv_ticket_date.setText(ticketInfo.date)

                    if (URLUtil.isValidUrl(ticketInfo.img)) {

                        tv_empty.visibility=View.GONE
                        iv_ticket.background = null

                        if(tickList.size>1)
                            tv_more_info.visibility=View.VISIBLE
                        else
                            tv_more_info.visibility=View.GONE


                        mGlideRequestManager?.load(ticketInfo.img)?.into(iv_ticket)

                    } else {
                        iv_ticket.setAlpha(50)
                        tv_empty.visibility=View.VISIBLE
                        tv_empty.text="유효하지 않는 티켓 정보입니다."
                        tv_more_info.visibility=View.GONE
                    }

                } else {
                    iv_ticket.setAlpha(50)
                    tv_empty.visibility=View.VISIBLE
                    tv_empty.text="아직 구매한 티켓이 없습니다"
                    tv_more_info.visibility=View.GONE
                }

            }
        }
    }

    override fun onFail(status: Int) {
        activity?.progress_bar?.visibility = View.GONE
        Log.d("testTicket", "getTicketListResponse in onFailure ")

        ColorToast(activity?.applicationContext, getString(R.string.txt_try_again))
    }

    override fun onItemClick(root: RecyclerView.Adapter<out RecyclerView.ViewHolder>, position: Int) {

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


}

package concertrip.sopt.com.concertrip.activities.main.fragment.mypage.ticket

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.dialog.ColorToast
import concertrip.sopt.com.concertrip.interfaces.OnFragmentInteractionListener
import concertrip.sopt.com.concertrip.interfaces.OnResponse
import concertrip.sopt.com.concertrip.list.adapter.TicketListAdapter
import concertrip.sopt.com.concertrip.model.Ticket
import concertrip.sopt.com.concertrip.network.ApplicationController
import concertrip.sopt.com.concertrip.network.NetworkService
import concertrip.sopt.com.concertrip.network.response.GetTicket_ListResponse
import concertrip.sopt.com.concertrip.network.response.interfaces.BaseModel
import concertrip.sopt.com.concertrip.utillity.NetworkUtil.Companion.getTicketList
import kotlinx.android.synthetic.main.fragment_ticket_list.*

class TicketListFragment : Fragment() , OnFragmentInteractionListener, OnResponse{
    var dataListTicket = arrayListOf<Ticket>()
    lateinit var ticketAdapter : TicketListAdapter

    private val networkServicce : NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    private var listener: OnFragmentInteractionListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ticket_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialUI()
        connectRequestData()
    }

    private fun initialUI(){

        btn_ticket_list_back.setOnClickListener {
            activity?.onBackPressed()
        }


        activity?.let{
            ticketAdapter = TicketListAdapter(it.applicationContext, dataListTicket, this)
            recycler_view_ticket_list.adapter = ticketAdapter
        }
    }



    override fun changeFragment(what: Int) {
        listener?.changeFragment(what)
    }



    private fun updateListTicket(list : ArrayList<Ticket>){
        dataListTicket.clear()
        dataListTicket.addAll(list)
        ticketAdapter.notifyDataSetChanged()
    }

    private fun connectRequestData(){
        getTicketList(networkServicce, this, "")
    }

    override fun onSuccess(obj: BaseModel, position: Int?) {
        if(obj is GetTicket_ListResponse){
            val responseBody = obj as GetTicket_ListResponse

            responseBody.let{
                if(it.status  == 200){
                    val ticketList = it.toTicketList()
                    updateListTicket(ticketList)}
                else{
                    Log.d("testTicket", "getTicketListResponse in" + responseBody?.status.toString())
                }
            }
        }
    }

    override fun onFail(status : Int) {
        Log.d("testTicket", "getTicketListResponse in onFailure ")
        ColorToast(activity?.applicationContext,getString(R.string.txt_try_again))
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

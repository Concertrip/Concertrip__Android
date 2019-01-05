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
import concertrip.sopt.com.concertrip.interfaces.OnFragmentInteractionListener
import concertrip.sopt.com.concertrip.list.adapter.BasicListAdapter
import concertrip.sopt.com.concertrip.model.Artist
import concertrip.sopt.com.concertrip.model.Concert
import concertrip.sopt.com.concertrip.model.Ticket
import concertrip.sopt.com.concertrip.network.ApplicationController
import concertrip.sopt.com.concertrip.network.NetworkService
import concertrip.sopt.com.concertrip.network.response.GetTicketDetailResponse
import kotlinx.android.synthetic.main.fragment_ticket.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TicketFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [TicketFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class TicketFragment : Fragment() {

    var dataList = arrayListOf<Ticket>()
    private var ticketId : Int = 1

    private val networkServicce : NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ticket, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialUI()
        ConnectRequestData()
    }

    private fun initialUI(){
        tv_ticket_detail_name.text = ""
        tv_ticket_detail_date.text = ""
        tv_ticket_detail_location.text = ""
        tv_ticket_detail_seat.text = ""
    }

    private fun updateUI(ticket: Ticket){
        tv_ticket_detail_name.text = ticket.name
        tv_ticket_detail_date.text = convertDate(ticket.date)
        tv_ticket_detail_seat.text = ticket.seat
        tv_ticket_detail_location.text = ticket.location
    }

    private fun ConnectRequestData(){
        val getTicketDetailResponse : Call<GetTicketDetailResponse> = networkServicce.getTicketDetail(1, ticketId)

        getTicketDetailResponse.enqueue(object : Callback<GetTicketDetailResponse>{
            override fun onFailure(call: Call<GetTicketDetailResponse>, t: Throwable) {
                Log.d("testTicketDetail", "getTicketDetailResponse in onFailure " + t.toString())
            }

            override fun onResponse(call: Call<GetTicketDetailResponse>, response: Response<GetTicketDetailResponse>) {
                response.body()?.let{
                    if(it.status == 200){
                        val ticket = it.data.toTicket()
                        updateUI(ticket)
                    }else{
                        Log.d("testTicketDetail", "getTicketDetailResponse in" + response.body()?.status.toString())
                    }
                }
            }
        })

    }

    val dayNum : List<String> = listOf("일", "월", "화", "수", "목", "금", "토")

    private fun convertDate(input: String?) : String?{
        val convertedDate = StringBuilder()

        if(input != null){
            val dateInfoList = input.split("T")
            val dateFormat = SimpleDateFormat("yyyy-MM-dd").parse(input.split("T")[0])
            val instance : Calendar = Calendar.getInstance()
            instance.setTime(dateFormat)
            val dayNumIdx = instance.get(Calendar.DAY_OF_WEEK)

            val splitedList = dateInfoList[0].split("-")

            convertedDate.append(splitedList[0]+"."+splitedList[1]+"."+splitedList[2]+"("+dayNum[dayNumIdx-1]+")")

            return convertedDate.toString()
        }
        else{
            return convertedDate.toString()
        }
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TicketFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TicketFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

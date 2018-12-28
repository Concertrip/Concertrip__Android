package concertrip.sopt.com.concertrip.activities.main.fragment.mypage

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import concertrip.sopt.com.concertrip.R
import concertrip.sopt.com.concertrip.interfaces.OnFragmentInteractionListener
import concertrip.sopt.com.concertrip.interfaces.OnItemClick
import concertrip.sopt.com.concertrip.list.adapter.TicketListAdapter

import concertrip.sopt.com.concertrip.utillity.Constants
import concertrip.sopt.com.concertrip.model.Ticket
import kotlinx.android.synthetic.main.fragment_my_page.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MyPageFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MyPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MyPageFragment : Fragment(), OnItemClick, OnFragmentInteractionListener {

    var dataListTicket = arrayListOf<Ticket>()
    lateinit var ticketAdapter : TicketListAdapter

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
        return inflater.inflate(R.layout.fragment_my_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialUI()

    }

    // TODO: Rename method, update argument and hook method into UI event
    fun changeFragment(){
        listener?.changeFragment(Constants.FRAGMENT_SETTING)
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun changeFragment(what: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun changeFragment(what: Int, bundle: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun initialUI(){
            btn_setting.setOnClickListener {
                changeFragment()
            }

            activity?.let{
                dataListTicket = Ticket.getDummyArray()
                ticketAdapter = TicketListAdapter(it.applicationContext, dataListTicket)
                recycler_view_ticket.adapter = ticketAdapter
            }
    }

    override fun onItemClick(root: RecyclerView.Adapter<out RecyclerView.ViewHolder>, idx: Int){

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
         * @return A new instance of fragment MyPageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyPageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

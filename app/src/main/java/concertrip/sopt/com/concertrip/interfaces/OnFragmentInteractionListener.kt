package concertrip.sopt.com.concertrip.interfaces

import android.net.Uri
import android.os.Bundle


/**
 * This interface must be implemented by activities that contain this
 * fragment to allow an interaction in this fragment to be communicated
 * to the activity and potentially other fragments contained in that
 * activity.
 *
 *
 * See the Android Training lesson [Communicating with Other Fragments]
 * (http://developer.android.com/training/basics/fragments/communicating.html)
 * for more information.
 */
interface OnFragmentInteractionListener {
    fun onFragmentInteraction(uri: Uri)

    fun changeFragment(what :Int)
    fun changeFragment(what :Int,bundle: Bundle?)

    /*
    이 파일은 이번 프로젝트 내에서 사용 안 할 확률이 크므로 몰라도 됩니다.
    다만 궁금하다면 읽어봐도 되고 모르겠으면 물어보세요. -희성

    이거는 Fragment 만들때 자동으로 생성되는 Interface입니다.

    하지만 우리 프로젝트의 경우 하나의 Main Activity내에서 여러 Fragment를 사용하기 때문에 굳이 각 Fragment별로 선언해줄 필요가 없기 때문에 밖으로 뻇습니다.
    그럼으로써 MainActivity에서는 Fragment마다 Interface를 추가시킬 필요 없이 하나의 Interface만 추가해주면 됩니다,

    이거를 사용하는 가장 큰 목적은 하나의 Fragment에서 event가 발생 했을 때 MainActivity에서 어떠한 작업을 하고 싶을 때 이 인터페이스를 이용하게 됩니다.
    사용하는 방법은
    Fragment 소스에서 보면


    private var listener: OnFragmentInteractionListener? = null
    이는 이 Interface의 형태를 갖는 listner라는 변수를 선언하는 과정이고

    fun onButtonPressed(uri: Uri) {
    저 Function을 Fragment에서 잘 확인해보면 회색으로 표시, 즉 사용을 안한다는 표시가 되어있습니다.
    이는 개발자가 필요하면 이름을 변경해서 사용하라는 것입니다.

        listener?.onFragmentInteraction(uri)
    listener가 Null이 아니라면 Interface에서 약속한 함수를 호출하겠다는 의미이며 이때 Uri를 함께 보냅니다.
    굳이 Uri가 아닌 Int로 보내고 싶다면 이 Interface를 수정해서 Int로 주고 받는 함수로 만들면 됩니다.

    }


    override fun onAttach(context: Context) {

        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

       이 코드는 Fragment가 Attach 된 경우에 context를 listener에 저장을 하는 코드입니다.
       이때 context의 경우 Activity를 가리키게 되는데 Activity에 Interface를 추가한 상태이기 때문에 if에서 True를 반한하게 되며
       Activty의 Context를 Interface형태로 저장을 합니다.


    override fun onDetach() {
        super.onDetach()
        listener = null
    }
    listener를 붙였다면 떼어주는 과정도 필요합니다.



이제 Activity인데 Interface를 추가하게 되면 미리 약속한 함수를 구현을 해야합니다.
class MainActivity : AppCompatActivity() , OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {
    }
    이 메소드는 Fragment에서 특정 이벤트가 발생하였을 경우 메세지를 Uri에 담아서 보내주며
    이 Function을 부르게 됩니다.



     */

}
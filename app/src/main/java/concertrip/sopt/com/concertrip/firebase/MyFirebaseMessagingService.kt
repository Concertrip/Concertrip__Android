package concertrip.sopt.com.concertrip.firebase

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        Log.d(TAG, "From: "+ remoteMessage?.from)

        if(remoteMessage?.notification != null){
            Log.d(TAG, "Message Notification Title: " + remoteMessage.notification?.title)
            Log.d(TAG, "Message Notification Body: " + remoteMessage.notification?.body)
        }else{
            if(remoteMessage?.data?.isNotEmpty()==true){
                Log.d(TAG, "Message data payload: " +remoteMessage.data)
            }
        }
    }

    companion object {
        private val TAG = "~~~CONCERTRIP"
    }
}

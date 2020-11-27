package com.example.newsapp.authentication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.newsapp.R
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class UserProfile :Fragment() {
    private lateinit var userProfileView: View
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userProfileView=inflater.inflate(R.layout.user_profile, container, false)
        activity?.title="User Profile"
        val user=FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val userImage=userProfileView.findViewById<ImageView>(R.id.user_image)
            val imgUri=user.photoUrl
            Glide.with(userImage.context)
                .load(imgUri)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.progress_animation)
                        .error(R.drawable.ic_baseline_person_24)
                        .fitCenter().override(600, 200)
                )
                .into(userImage)
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            googleSignInClient = GoogleSignIn.getClient(this.requireActivity(), gso)
            val userName=userProfileView.findViewById<TextView>(R.id.profile_name)
            userName.text=user.displayName
            val userEmail=userProfileView.findViewById<TextView>(R.id.email_id_textView)
            userEmail.text=user.email
            val userUid=userProfileView.findViewById<TextView>(R.id.phone_no_textView)
            userUid.text=user.uid
            val signOut=userProfileView.findViewById<Button>(R.id.sign_out_button)
            signOut.setOnClickListener {
                signOutUser()
                    val intent=Intent(this.requireActivity(), LoginActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this.context, "Signed Out", Toast.LENGTH_SHORT).show()
            }
            }
        return userProfileView
    }
    private fun signOutUser(){
        val auth=FirebaseAuth.getInstance()
        auth.signOut()
        googleSignInClient.signOut()
        LoginManager.getInstance().logOut()

    }
}


//class MyFirebaseMessagingService : FirebaseMessagingService(){
//    private val TAG = "FireBaseMessagingService"
//    override fun onMessageReceived(p0: RemoteMessage) {
//
//        Log.d(TAG," onMessageReceived ")
//        if(p0.data.isNotEmpty()){
//            Log.d(TAG, " Data : " + p0.data.toString())
//        }
//
//        if(p0.notification != null){
//            Log.d(TAG," Notification : " + p0.notification!!.body.toString())
//        }
//
//    }
//
//}


//
//package com.example.newsapp
//
//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.app.PendingIntent
//import android.content.Context
//import android.content.Intent
//import android.media.RingtoneManager
//import android.os.Build
//import android.util.Log
//import androidx.core.app.NotificationCompat
//import androidx.work.OneTimeWorkRequest
//import androidx.work.WorkManager
//import com.google.firebase.messaging.FirebaseMessagingService
//import com.google.firebase.messaging.RemoteMessage
//
//class MyFirebaseMessagingService : FirebaseMessagingService() {
//
//    /**
//     * Called when message is received.
//     *
//     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
//     */
//    // [START receive_message]
//    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        // [START_EXCLUDE]
//        // There are two types of messages data messages and notification messages. Data messages are handled
//        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
//        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
//        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
//        // When the user taps on the notification they are returned to the app. Messages containing both notification
//        // and data payloads are treated as notification messages. The Firebase console always sends notification
//        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
//        // [END_EXCLUDE]
//
//        // TODO(developer): Handle FCM messages here.
//        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
//        Log.d(TAG, "From: ${remoteMessage.from}")
//
//        // Check if message contains a data payload.
//        if (remoteMessage.data.isNotEmpty()) {
//            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
//
//            if (/* Check if data needs to be processed by long running job */ true) {
//                // For long-running tasks (10 seconds or more) use WorkManager.
//                scheduleJob()
//            } else {
//                // Handle message within 10 seconds
//                handleNow()
//            }
//
//
//
//
//        }
//
//        // Check if message contains a notification payload.
//        remoteMessage.notification?.let {
//            Log.d(TAG, "Message Notification Body: ${it.body}")
//        }
//
//        // Also if you intend on generating your own notifications as a result of a received FCM
//        // message, here is where that should be initiated. See sendNotification method below.
//    }
//    // [END receive_message]
//
//    // [START on_new_token]
//    /**
//     * Called if the FCM registration token is updated. This may occur if the security of
//     * the previous token had been compromised. Note that this is called when the
//     * FCM registration token is initially generated so this is where you would retrieve the token.
//     */
//    override fun onNewToken(token: String) {
//        Log.d(TAG, "Refreshed token: $token")
//
//        // If you want to send messages to this application instance or
//        // manage this apps subscriptions on the server side, send the
//        // FCM registration token to your app server.
//        sendRegistrationToServer(token)
//    }
//    // [END on_new_token]
//
//    /**
//     * Schedule async work using WorkManager.
//     */
//    private fun scheduleJob() {
//        // [START dispatch_job]
//        val work = OneTimeWorkRequest.Builder(MyWorker::class.java).build()
//        WorkManager.getInstance().beginWith(work).enqueue()
//        // [END dispatch_job]
//    }
//
//    /**
//     * Handle time allotted to BroadcastReceivers.
//     */
//    private fun handleNow() {
//        Log.d(TAG, "Short lived task is done.")
//    }
//
//    /**
//     * Persist token to third-party servers.
//     *
//     * Modify this method to associate the user's FCM registration token with any server-side account
//     * maintained by your application.
//     *
//     * @param token The new token.
//     */
//    private fun sendRegistrationToServer(token: String?) {
//        // TODO: Implement this method to send token to your app server.
//        Log.d(TAG, "sendRegistrationTokenToServer($token)")
//    }
//
//    /**
//     * Create and show a simple notification containing the received FCM message.
//     *
//     * @param messageBody FCM message body received.
//     */
//    private fun sendNotification(messageBody: String) {
//        val intent = Intent(this, MainActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//            PendingIntent.FLAG_ONE_SHOT)
//
//        val channelId = getString(R.string.default_notification_channel_id)
//        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//        val notificationBuilder = NotificationCompat.Builder(this, channelId)
//            //.setSmallIcon(R.drawable.ic_stat_ic_notification)
//            //.setContentTitle(getString(R.string.fcm_message))
//            .setContentText(messageBody)
//            .setAutoCancel(true)
//            .setSound(defaultSoundUri)
//            .setContentIntent(pendingIntent)
//
//        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        // Since android Oreo notification channel is needed.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(channelId,
//                "Channel human readable title",
//                NotificationManager.IMPORTANCE_DEFAULT)
//            notificationManager.createNotificationChannel(channel)
//        }
//
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
//    }
//
//    companion object {
//        private const val TAG = "MyFirebaseMsgService"
//    }
//}
//
////}





//package com.example.newsapp
//
//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.app.PendingIntent
//import android.content.Context
//import android.content.Intent
//import android.media.RingtoneManager
//import android.os.Build
//import android.util.Log
//import androidx.core.app.NotificationCompat
//import androidx.core.content.ContextCompat
//import androidx.work.OneTimeWorkRequest
//import androidx.work.WorkManager
//import com.google.firebase.messaging.FirebaseMessagingService
//import com.google.firebase.messaging.RemoteMessage
//
//class MyFirebaseMessagingService : FirebaseMessagingService() {
//
//    /**
//     * Called when message is received.
//     *
//     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
//     */
//    // [START receive_message]
//    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
//        Log.d(TAG, "From: ${remoteMessage.from}")
//
//        // TODO Step 3.5 check messages for data
//        // Check if message contains a data payload.
//        remoteMessage.data.let {
//            Log.d(TAG, "Message data payload: " + remoteMessage.data)
//        }
//
//        // TODO Step 3.6 check messages for notification and call sendNotification
//        // Check if message contains a notification payload.
//        remoteMessage.notification?.let {
//            Log.d(TAG, "Message Notification Body: ${it.body}")
//            sendNotification(it.body!!)
//        }
//    }
//    // [END receive_message]
//
//    //TODO Step 3.2 log registration token
//    // [START on_new_token]
//    /**
//     * Called if InstanceID token is updated. This may occur if the security of
//     * the previous token had been compromised. Note that this is called when the InstanceID token
//     * is initially generated so this is where you would retrieve the token.
//     */
//    override fun onNewToken(p0: String) {
//        Log.d(TAG, "Refreshed token: $p0")
//
//        // If you want to send messages to this application instance or
//        // manage this apps subscriptions on the server side, send the
//        // Instance ID token to your app server.
//        sendRegistrationToServer(p0)
//    }
//    // [END on_new_token]
//
//
//    /**
//     * Persist token to third-party servers.
//     *
//     * @param token The new token.
//     */
//    private fun sendRegistrationToServer(token: String?) {
//        // TODO: Implement this method to send token to your app server.
//    }
//
//    /**
//     * Create and show a simple notification containing the received FCM message.
//     *
//     * @param messageBody FCM message body received.
//     */
////    private fun sendNotification(messageBody: String) {
////        Log.i("Notification",messageBody)
//////       val notificationManager = ContextCompat.getSystemService(applicationContext, NotificationManager::class.java) as NotificationManager
//////        notificationManager.(messageBody, applicationContext)
////
////    }
//    private fun sendNotification(messageBody: String) {
//        val intent = Intent(this, MainActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//            PendingIntent.FLAG_ONE_SHOT)
//
//        val channelId = getString(R.string.default_notification_channel_id)
//        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//        val notificationBuilder = NotificationCompat.Builder(this, channelId)
//            //.setSmallIcon(R.drawable.ic_stat_ic_notification)
//            //.setContentTitle(getString(R.string.fcm_message))
//            .setContentText(messageBody)
//            .setAutoCancel(true)
//            .setSound(defaultSoundUri)
//            .setContentIntent(pendingIntent)
//
//        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        // Since android Oreo notification channel is needed.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(channelId,
//                "Channel human readable title",
//                NotificationManager.IMPORTANCE_DEFAULT)
//            notificationManager.createNotificationChannel(channel)
//        }
//
//        //notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
//    }
//
//    companion object {
//        private const val TAG = "MyFirebaseMsgService"
//    }
//}
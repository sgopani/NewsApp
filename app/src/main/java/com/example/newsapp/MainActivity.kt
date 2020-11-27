package com.example.newsapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.newsapp.authentication.LoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration : AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( R.layout.activity_main)
        val topLevelDestinations: Set<Int> = setOf(R.id.newsList2,
            R.id.newsSearchFragment,
            R.id.newsFavFragment,
            R.id.userProfile)
        appBarConfiguration = AppBarConfiguration.Builder(topLevelDestinations).build()
        val navController=this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        //FirebaseMessaging.getInstance().isAutoInitEnabled=true
        val token=FirebaseMessaging.getInstance().token
        Log.i("Token ", token.toString())
        bottomNav.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController=this.findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onStart() {

            val user = FirebaseAuth.getInstance().currentUser
                if (user != null)
                {
                    Log.i("User Token ", user.uid)
                }
                else{
                   val intent=Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
//            }
//        }
      super.onStart()
    }
}

//class MyFirebaseMessagingService : FirebaseMessagingService() {
//
//    private val TAG = "FireBaseMessagingService"
//    var NOTIFICATION_CHANNEL_ID = "com.example.newsapp"
//    val NOTIFICATION_ID = 100
//
//    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        super.onMessageReceived(remoteMessage)
//
//        Log.e("message","Message Received ...");
//
//        if (remoteMessage.data.isNotEmpty()) {
//            val title = remoteMessage.data["title"]
//            val body = remoteMessage.data["body"]
//            showNotification(applicationContext, title, body)
//        } else {
//            val title = remoteMessage.notification!!.title
//            val body = remoteMessage.notification!!.body
//            showNotification(applicationContext, title, body)
//        }
//    }
//
//
//    override fun onNewToken(p0: String) {
//        super.onNewToken(p0)
//        Log.e("token","New Token")
//    }
//
//
//    private fun showNotification(
//        context: Context,
//        title: String?,
//        message: String?
//    ) {
//        val ii: Intent=Intent(context, MainActivity::class.java)
//        ii.data = Uri.parse("custom://" + System.currentTimeMillis())
//        ii.action = "actionstring" + System.currentTimeMillis()
//        ii.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
//        val pi =
//            PendingIntent.getActivity(context, 0, ii, PendingIntent.FLAG_UPDATE_CURRENT)
//        val notification: Notification
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            //Log.e("Notification", "Created in up to orio OS device");
//            notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
//                .setOngoing(true)
//                .setSmallIcon(getNotificationIcon())
//                .setContentText(message)
//                .setAutoCancel(true)
//                .setContentIntent(pi)
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setCategory(Notification.CATEGORY_SERVICE)
//                .setWhen(System.currentTimeMillis())
//                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
//                .setContentTitle(title).build()
//            val notificationManager = context.getSystemService(
//                Context.NOTIFICATION_SERVICE
//            ) as NotificationManager
//            val notificationChannel = NotificationChannel(
//                NOTIFICATION_CHANNEL_ID,
//                title,
//                NotificationManager.IMPORTANCE_DEFAULT
//            )
//            notificationManager.createNotificationChannel(notificationChannel)
//            notificationManager.notify(NOTIFICATION_ID, notification)
//        } else {
//            notification = NotificationCompat.Builder(context)
//                .setSmallIcon(getNotificationIcon())
//                .setAutoCancel(true)
//                .setContentText(message)
//                .setContentIntent(pi)
//                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
//                .setContentTitle(title).build()
//            val notificationManager = context.getSystemService(
//                Context.NOTIFICATION_SERVICE
//            ) as NotificationManager
//            notificationManager.notify(NOTIFICATION_ID, notification)
//        }
//    }
//
//    private fun getNotificationIcon(): Int {
//        val useWhiteIcon =
//            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
//        return if (useWhiteIcon) R.mipmap.ic_launcher else R.mipmap.ic_launcher
//    }
//
//}
package com.example.mvvm_template.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.mvvm_template.App
import com.example.mvvm_template.R
import com.example.mvvm_template.domain.interactor.account.UpdateFirBaseTokenUseCase
import com.example.mvvm_template.utils.LogUtil
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named


@AndroidEntryPoint
class FireBaseMessage : FirebaseMessagingService() {
    private val TAG = "MyFirebaseMsgService"
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages
        // are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data
        // messages are the type
        // traditionally used with GCM. Notification messages are only received here in
        // onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated
        // notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages
        // containing both notification
        // and data payloads are treated as notification messages. The Firebase console always
        // sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
//        Log.d(TAG, "From: " + remoteMessage.getFrom());
//        Log.e(TAG, "tittle: " + remoteMessage.getNotification().getTitle());
//        Log.e(TAG, "tittle: " + remoteMessage.getNotification().getBody());
        if (remoteMessage.notification != null)
            sendNotification(
            remoteMessage.notification!!.title,
            remoteMessage.notification!!.body,
            remoteMessage.data,
            remoteMessage.notification!!.clickAction
        )
        // Check if message contains a data payload.
        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            LogUtil.debug(
                TAG,
                "Message data payload: " + remoteMessage.data
            )
        }


    }

    // [END receive_message]
    // [START on_new_token]
    override fun onNewToken(token: String) {
        LogUtil.error(TAG, "Refreshed token: $token")


    }
    private fun sendNotification(
        tittle: String?,
        body: String?,
        params: Map<String, String>,
        clickAction: String?
    ) {
        var intent = Intent(clickAction)
        if (clickAction == null) intent = Intent("com.smartzone.edura")
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        val pendingIntent = PendingIntent.getActivity(
            this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT
        )
        val channelId = getString(R.string.default_notification_channel_id)
        //Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        val notificationBuilder =
            NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_logo)
                .setContentTitle(tittle)
                .setContentText(body)
                .setAutoCancel(true) /*  .setSound(defaultSoundUri)*/
                .setContentIntent(pendingIntent)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }
}
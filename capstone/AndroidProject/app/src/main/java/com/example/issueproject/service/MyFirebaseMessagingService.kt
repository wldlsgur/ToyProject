package com.example.issueproject.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.issueproject.R
import com.example.issueproject.dto.NotificationType
import com.example.issueproject.res.MainActivity
import com.example.issueproject.res.testActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        const val TAG = "MessagingService"
        private const val CHANNEL_NAME = "Push Notification"
        private const val CHANNEL_DESCRIPTION = "Push Notification 을 위한 채널"
        const val CHANNEL_ID = "Issue"
    }

    /* 토큰 생성 메서드 */
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "fire onNewToken: $token")
    }


    /* 메세지 수신 메서드 */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d(TAG, "onMessageReceived() - remoteMessage : $remoteMessage")
        Log.d(TAG, "onMessageReceived() - from : ${remoteMessage.from}")
        Log.d(TAG, "onMessageReceived() - notification : ${remoteMessage.notification?.body}")

        val type = remoteMessage.data["type"]?.let { NotificationType.valueOf(it) } ?: kotlin.run {
            NotificationType.NORMAL  //type 이 null 이면 NORMAL type 으로 처리
        }
        val title = remoteMessage.data["title"]
        val message = remoteMessage.data["message"]

        Log.d(TAG, "onMessageReceived() - type : $type")
        Log.d(TAG, "onMessageReceived() - title : $title")
        Log.d(TAG, "onMessageReceived() - message : $message")

        sendNotification(type, title, message)
    }


    /* 알림 생성 메서드 */
    private fun sendNotification(
        type: NotificationType,
        title: String?,
        message: String?
    ) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //Oreo(26) 이상 버전에는 channel 필요
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = CHANNEL_DESCRIPTION
            notificationManager.createNotificationChannel(channel)
        }

        //알림 생성
        NotificationManagerCompat.from(this)
            .notify((System.currentTimeMillis()/100).toInt(), createNotification(type, title, message))  //알림 여러개 표시되도록 requestCode 를 추가이
    }


    /* 알림 설정 메서드 */
    private fun createNotification(
        type: NotificationType,
        title: String?,
        message: String?
    ): Notification {

        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("notificationType", " ${type.title} 타입 ")
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        //val pendingIntent = PendingIntent.getActivity(this, (System.currentTimeMillis()/100).toInt(), intent, FLAG_UPDATE_CURRENT)  //알림이 여러개 표시되도록 requestCode 를 추가

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.more) //icon 변경 필요
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            //.setContentIntent(pendingIntent)  //알림 눌렀을 때 실행할 Intent 설정
            .setAutoCancel(true)  //클릭 시 자동으로 삭제되도록 설정

        //type 에 따라 style 설정
        when (type) {
            NotificationType.NORMAL -> Unit
            NotificationType.EXPANDABLE -> {
                notificationBuilder.setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText("$message \n 😀 😃 😄 😁 😆 😅 😂 🤣 🥲 ☺️ 😊 😇 🙂 🙃 😉 😌 😍 🥰 😘 😗 😙 😚 😋 😛 😝 😜 🤪 🤨 🧐 🤓 😎 🥸 🤩 🥳 😏 😒 😞 😔 😟 😕 🙁 ☹️ 😣 😖 😫 😩 🥺 😢 😭 😤 😠 😡 🤬 🤯 😳 🥵 🥶 😱 😨 😰 😥 😓 🤗 🤔 🤭 🤫 🤥 😶 😐 😑 😬 🙄 😯 😦 😧 😮 😲 🥱 😴 🤤 😪 😵 🤐 🥴 🤢 🤮 🤧 😷 🤒 🤕")
                )
            }
            NotificationType.CUSTOM -> {
                notificationBuilder.setStyle(
                    NotificationCompat.DecoratedCustomViewStyle()
                )
                    .setCustomContentView(
                        RemoteViews(
                            packageName,
                            R.layout.view_custom_notification
                        ).apply {
                            setTextViewText(R.id.tv_custom_title, title)
                            setTextViewText(R.id.tv_custom_message, message)
                        }
                    )
            }
        }
        return notificationBuilder.build()
    }


}
/*
    /* 메세지 수신 메서드 */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d(TAG, "onMessageReceived() - remoteMessage : $remoteMessage")
        Log.d(TAG, "onMessageReceived() - from : ${remoteMessage.from}")
        Log.d(TAG, "onMessageReceived() - notification : ${remoteMessage.notification?.body}")
        Log.d(TAG, "onMessageReceived() - notification : ${remoteMessage.data}")

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            sendNotification(CHANNEL_ID, "issue")
        }
        //sendNotification(type, title, message)

    }

    private fun getCustomDesign(title: String, message: String): RemoteViews? {
        val remoteViews = RemoteViews(applicationContext.packageName, R.layout.notification)
        remoteViews.setTextViewText(R.id.noti_title, title)
        remoteViews.setTextViewText(R.id.noti_message, message)
        remoteViews.setImageViewResource(R.id.noti_icon, R.mipmap.ic_launcher)
        return remoteViews
    }

    /* 알림 생성 메서드 */
    private fun sendNotification(title : String,message : String) {
        val intent = Intent(this, MainActivity::class.java)
        val channel_id : String = "issue";
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        val pendingIntent : PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        val uri : Uri  = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        val builder : NotificationCompat.Builder =  NotificationCompat.Builder(getApplicationContext(), channel_id)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setSound(uri)
            .setAutoCancel(true)
            .setVibrate(longArrayOf (1000, 1000, 1000, 1000, 1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        {
            builder = builder.setContent(getCustomDesign(title, message));
        }
        else
        {
            builder = builder.setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher);
        }

        val notificationManager : NotificationManager= getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val notificationChannel : NotificationChannel  = NotificationChannel(channel_id, "web_app", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setSound(uri, null);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        notificationManager.notify(0, builder.build());
    }
    }
}

     */

/*(
type: NotificationType,
title: String?,
message: String?
) {
val notificationManager =
getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

//Oreo(26) 이상 버전에는 channel 필요
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
val channel = NotificationChannel(
CHANNEL_ID,
CHANNEL_NAME,
NotificationManager.IMPORTANCE_DEFAULT
)
channel.description = CHANNEL_DESCRIPTION
notificationManager.createNotificationChannel(channel)
}

//알림 생성
NotificationManagerCompat.from(this)
.notify((System.currentTimeMillis()/100).toInt(), createNotification(type, title, message))  //알림 여러개 표시되도록 requestCode 를 추가이
}
*/

/* 알림 설정 메서드 */
/*
private fun createNotification(
    type: NotificationType,
    title: String?,
    message: String?
): Notification {

    val intent = Intent(this, testActivity::class.java).apply {
        putExtra("notificationType", " ${type.title} 타입 ")
        addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
    }
    val pendingIntent = PendingIntent.getActivity(this, (System.currentTimeMillis()/100).toInt(), intent, FLAG_UPDATE_CURRENT)  //알림이 여러개 표시되도록 requestCode 를 추가

    val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
        .setSmallIcon(R.drawable.more) //icon 변경 필요
        .setContentTitle(title)
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(pendingIntent)  //알림 눌렀을 때 실행할 Intent 설정
        .setAutoCancel(true)  //클릭 시 자동으로 삭제되도록 설정

    //type 에 따라 style 설정
    when (type) {
        NotificationType.NORMAL -> Unit
        NotificationType.EXPANDABLE -> {
            notificationBuilder.setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("$message \n 😀 😃 😄 😁 😆 😅 😂 🤣 🥲 ☺️ 😊 😇 🙂 🙃 😉 😌 😍 🥰 😘 😗 😙 😚 😋 😛 😝 😜 🤪 🤨 🧐 🤓 😎 🥸 🤩 🥳 😏 😒 😞 😔 😟 😕 🙁 ☹️ 😣 😖 😫 😩 🥺 😢 😭 😤 😠 😡 🤬 🤯 😳 🥵 🥶 😱 😨 😰 😥 😓 🤗 🤔 🤭 🤫 🤥 😶 😐 😑 😬 🙄 😯 😦 😧 😮 😲 🥱 😴 🤤 😪 😵 🤐 🥴 🤢 🤮 🤧 😷 🤒 🤕")
            )
        }
        NotificationType.CUSTOM -> {
            notificationBuilder.setStyle(
                NotificationCompat.DecoratedCustomViewStyle()
            )
                .setCustomContentView(
                    RemoteViews(
                        packageName,
                        R.layout.view_custom_notification
                    ).apply {
                        setTextViewText(R.id.tv_custom_title, title)
                        setTextViewText(R.id.tv_custom_message, message)
                    }
                )
        }
    }
    return notificationBuilder.build()
}
*/
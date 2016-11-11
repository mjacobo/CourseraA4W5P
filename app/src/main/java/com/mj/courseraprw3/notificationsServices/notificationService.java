package com.mj.courseraprw3.notificationsServices;

import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import android.util.Log;
import android.view.Gravity;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mj.courseraprw3.R;


/**
 * Created by leyenda on 10/27/16.
 */

public class notificationService extends FirebaseMessagingService {
    public static final String TAG = "FIREBASE";
    public static final int NOTIFICATION_ID = 001 ;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //super.onMessageReceived(remoteMessage);
        Log.d(TAG, "From:" + remoteMessage.getFrom());
        Log.d(TAG, "Notification Maessage Body: " + remoteMessage.getNotification().getBody());
        myNot(remoteMessage);
    }

    public void myNot(RemoteMessage remoteMessage){
        Intent i = new Intent();
        i.setAction("PETA_LIKE");
        i.putExtra("ChangeFrame", true);
        i.putExtra("id_usuario_instagram", remoteMessage.getData().get("id_usuario_instagram"));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri sonido = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent iprofile = new Intent();
        iprofile.setAction("SEE_MY_PROFILE");
        iprofile.putExtra("ChangeFrame", true);
        iprofile.putExtra("id_usuario_instagram", remoteMessage.getData().get("id_usuario_instagram"));
        PendingIntent profilePendingIntent = PendingIntent.getBroadcast(this, 0, iprofile, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action seeMyProfile =
                new NotificationCompat.Action.Builder(R.drawable.ic_full_ver_mi_perfil,
                        getString(R.string.texto_accion_ver_perfil), profilePendingIntent)
                .build();

        Intent ifUser = new Intent();
        ifUser.setAction("FOLLOW_USER");
        ifUser.putExtra("ChangeFrame", true);
        ifUser.putExtra("id_usuario_instagram", remoteMessage.getData().get("id_usuario_instagram"));
        PendingIntent fUserPendingIntent = PendingIntent.getBroadcast(this, 0, ifUser, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action followUser =
                new NotificationCompat.Action.Builder(R.drawable.ic_full_follow,
                        getString(R.string.texto_accion_seguir_usuario), fUserPendingIntent)
                        .build();

        Intent iufUser = new Intent();
        iufUser.setAction("UNFOLLOW_USER");
        iufUser.putExtra("ChangeFrame", true);
        iufUser.putExtra("id_usuario_instagram", remoteMessage.getData().get("id_usuario_instagram"));
        PendingIntent ufUserPendingIntent = PendingIntent.getBroadcast(this, 0, iufUser, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action unFollowUser =
                new NotificationCompat.Action.Builder(R.drawable.ic_full_unfollow,
                        getString(R.string.texto_accion_noSeguir_usuario), ufUserPendingIntent)
                        .build();

        Intent ichUser = new Intent();
        ichUser.setAction("CHECK_USER");
        ichUser.putExtra("ChangeFrame", true);
        ichUser.putExtra("id_usuario_instagram", remoteMessage.getData().get("id_usuario_instagram"));
        PendingIntent uchUserPendingIntent = PendingIntent.getBroadcast(this, 0, ichUser, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action checkUser =
                new NotificationCompat.Action.Builder(R.drawable.ic_full_ver_usuario,
                        getString(R.string.texto_accion_ver_usuario), uchUserPendingIntent)
                        .build();

        NotificationCompat.WearableExtender wearableExtender =
                new NotificationCompat.WearableExtender()
                .setHintHideIcon(true)
                .setBackground(BitmapFactory.decodeResource(getResources(),
                        R.drawable.back_600))
                .setGravity(Gravity.CENTER_VERTICAL);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.message_100)
                .setContentTitle("Notificación")
                .setContentText(remoteMessage.getNotification().getBody())
                .setAutoCancel(true)
                .setSound(sonido)
                .setContentIntent(pendingIntent)
                .extend(wearableExtender.addAction(seeMyProfile))
                .extend(wearableExtender.addAction(followUser))
                .extend(wearableExtender.addAction(unFollowUser))
                .extend(wearableExtender.addAction(checkUser))
                ;

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}

package com.appdow.quod;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {

    public static final String channel1ID = "channel1ID";
    public static final String channelName = "Channel 1";

    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super( base );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannels();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void createChannels() {
        NotificationChannel channel1 = new NotificationChannel( channel1ID, channelName, NotificationManager.IMPORTANCE_HIGH );
        channel1.enableLights( true );
        channel1.enableVibration( true );
        channel1.setLightColor( android.support.coreui.R.color.notification_icon_bg_color );

        channel1.setLockscreenVisibility( Notification.VISIBILITY_PRIVATE );
        getmManager().createNotificationChannel( channel1 );

    }

    public NotificationManager getmManager() {


        if (mManager == null) {

            mManager = (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE );
        }

        return mManager;
    }


    public NotificationCompat.Builder getChannelNotification(String title , String msg ){


        return new NotificationCompat.Builder( getApplicationContext(), channel1ID).setContentTitle(title  )
                .setContentText( msg )
                .setSmallIcon( R.drawable.ic_texture );
    }
}


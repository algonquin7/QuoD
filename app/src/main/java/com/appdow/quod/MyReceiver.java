package com.appdow.quod;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        context.sendBroadcast(new Intent("INTERNET_LOST"));

    }
}

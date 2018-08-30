package com.appdow.quod;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import in.mamga.carousalnotification.Carousal;
import in.mamga.carousalnotification.CarousalItem;

public class CarousalItemClickReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {  //meaning some item is clicked
            //Get the carousal item that is clicked . Use the same key.
            CarousalItem item = bundle.getParcelable( Carousal.CAROUSAL_ITEM_CLICKED_KEY);
            if (item != null) {
                //Now we need to know where to redirect event
                String id = item.getId();
                //Now start an intent or anything else from here

            } else {  //Meaning other region is clicked and isOtherRegionClick is set to true.
                //Again handle by anything suitable here.
                Toast.makeText(context, "Other region clicked", Toast.LENGTH_LONG).show();
            }

        }
    }
}
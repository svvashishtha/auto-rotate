package com.example.saurabh.auto;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Created by Saurabh on 1/7/2015.
 */
public class broadcastReceiver extends BroadcastReceiver {
    private int temp =0;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("on","receive");



        try {
            temp = Settings.System.getInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION);
        }
        catch (Settings.SettingNotFoundException se)
        {
            temp = 0;
            Log.i("in","catch");
        }
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.auto);
        if(intent.getAction().equals("Settings.System.ACCELEROMETER_ROTATION"))
        if(temp == 0){
        Settings.System.putInt(context.getContentResolver(),Settings.System.ACCELEROMETER_ROTATION,1);temp++;
        Log.i("in","if");
            remoteViews.setTextColor(R.id.image, Color.GREEN);

            Toast toast = Toast.makeText(context,"AutoRotation is on",Toast.LENGTH_SHORT);
            toast.show();

         }
        else
        {
            Settings.System.putInt(context.getContentResolver(),Settings.System.ACCELEROMETER_ROTATION,0);temp--;
            Log.i("in","ELSE");
            remoteViews.setTextColor(R.id.image, Color.GRAY);
            Toast toast = Toast.makeText(context,"AutoRotation is off",Toast.LENGTH_SHORT);
             toast.show();


        }
        remoteViews.setOnClickPendingIntent(R.layout.auto,Auto.buildPending(context));
        Auto.pushWidgetUpdate(context,remoteViews);



    }

}

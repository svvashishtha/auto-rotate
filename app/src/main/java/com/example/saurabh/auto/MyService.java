package com.example.saurabh.auto;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.RemoteViews;

public class MyService extends Service {
    int temp;
    public MyService() {
    }
    @Override
    public void onCreate()
    {
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent,int flags,int startId)
    {
        Log.i("service","updating ");
        buildUpdate();
        return super.onStartCommand(intent,flags,startId);
    }

    private void buildUpdate() {
        Context context = getApplicationContext();
        RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.auto);
        try {
            temp = Settings.System.getInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION);
        }
        catch (Settings.SettingNotFoundException se)
        {
            temp = -1;
            Log.i("in","catch");
        }
        Log.i("temp ",""+temp);
        if(temp ==0)
            remoteViews.setTextColor(R.id.image, Color.GRAY);
        else if(temp == 1)
            remoteViews.setTextColor(R.id.image,Color.GREEN);

        Auto.pushWidgetUpdate(getApplicationContext(),remoteViews);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

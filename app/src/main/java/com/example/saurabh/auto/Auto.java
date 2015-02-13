package com.example.saurabh.auto;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Calendar;


/**
 * Implementation of App Widget functionality.
 *
 */
public class Auto extends AppWidgetProvider {
    private int temp =0;
    AlarmManager alarmManager;
    PendingIntent service = null;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            Log.i("fk", "uoi");
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.auto);

            remoteViews.setOnClickPendingIntent(R.id.image, buildPending(context));


            pushWidgetUpdate(context,remoteViews);
        }

        alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE) ;
       Intent up_intent = new Intent(context,MyService.class);
       final Calendar time = Calendar.getInstance();
        time.set(Calendar.MINUTE,0);
        time.set(Calendar.SECOND,0);
        if(service==null)
            service = PendingIntent.getService(context, 10, up_intent, PendingIntent.FLAG_UPDATE_CURRENT);

       alarmManager.setRepeating(AlarmManager.RTC, time.getTime().getTime(),5 * 60 * 1000, service);

    }

    public static PendingIntent buildPending (Context context)
    {
        Intent intent = new Intent();

        intent.setAction("Settings.System.ACCELEROMETER_ROTATION");
        return PendingIntent.getBroadcast(context,0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        alarmManager.cancel(service);
    }

    public static void pushWidgetUpdate(Context context, RemoteViews remoteViews) {
        ComponentName myWidget = new ComponentName(context, Auto.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(myWidget, remoteViews);
    }
}



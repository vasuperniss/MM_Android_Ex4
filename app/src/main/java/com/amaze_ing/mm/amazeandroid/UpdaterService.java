package com.amaze_ing.mm.amazeandroid;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Binder;

/**
 * Created by Max on 24/06/2016.
 */
public class UpdaterService extends Service
{
    UpdaterAlarm alarm = new UpdaterAlarm();

    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        alarm.SetAlarm(this);
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        alarm.CancelAlarm(this);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

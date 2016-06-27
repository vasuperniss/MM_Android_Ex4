package com.amaze_ing.mm.amazeandroid;
/**
 * exe 4
 * @author Michael Vassernis 319582888 vaserm3
 * @author Max Anisimov 322068487 anisimm
 */
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Binder;

/**
 * Created by Max on 24/06/2016.
 */
public class UpdaterService extends Service
{
    /**
     * The Alarm.
     */
    UpdaterAlarm alarm = new UpdaterAlarm();

    /**
     * sets an alarm when service starts.
     *
     * @param intent the intent
     * @param flags the flags
     * @param startId the start id
     * @return sticky
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        alarm.SetAlarm(this);
        return START_STICKY;
    }

    /**
     * cancels the alarm and destroys self.
     */
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

package com.amaze_ing.mm.amazeandroid;
/**
 * exe 4
 * @author Michael Vassernis 319582888 vaserm3
 * @author Max Anisimov 322068487 anisimm
 */
import android.content.BroadcastReceiver;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import android.widget.Toast;

/**
 * Created by Max on 24/06/2016.
 */
public class UpdaterAlarm extends BroadcastReceiver {

    private boolean isRegistered = false;

    /**
     * when an alarm is triggered a broadcast is sent to messaging activity which notifices the user.
     *
     * @param context the context
     * @param intent the intent
     */
    @Override
    public void onReceive(Context context, Intent intent)
    {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();

        String message = context.getResources().getString(R.string.update_alarm_intent);
        context.sendBroadcast(new Intent(message));
        wl.release();
    }

    /**
     * Sets the alarm with an interval of 5 mins.
     *
     * @param context the context
     */
    public void SetAlarm(Context context)
    {
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, UpdaterAlarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        int interval = 1000 * 60 * 5;
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + interval, interval, pi); // Millisec * Second * Minute
    }

    /**
     * Cancels the alarm.
     *
     * @param context the context
     */
    public void CancelAlarm(Context context)
    {
        Intent intent = new Intent(context, UpdaterAlarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}

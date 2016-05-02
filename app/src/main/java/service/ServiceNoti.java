package service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import appsmaven.graph.com.voice_beat.MenuActivity;
import appsmaven.graph.com.voice_beat.R;

/**
 * Created by Umesh on 4/25/2016.
 */
public class ServiceNoti extends Service {

    private static int notificatn_counter = 0;
    private static int count = 0;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    final static int RQS_1 = 1;
    static Integer counter=0;
    /**
     * indicates how to behave if the service is killed
     */
    int mStartMode;
    /**
     * interface for clients that bind
     */
    IBinder mBinder;
    /**
     * indicates whether onRebind should be used
     */
    boolean mAllowRebind;
    private int mInterval = 36000000;
    private Handler mHandler;


    /**
     * Called when the service is being created.
     */
    @Override
    public void onCreate() {

        // TODO Auto-generated method stub
        preferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());


    }

    /**
     * The service is starting, due to a call to startService()
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        preferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());

        // TODO Auto-generated method stub

        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        if(dayOfWeek==Calendar.SATURDAY){
            if(counter.equals(0)){
                generatenotification();

            }
            else{
                counter++;
            }
        }else{
            counter=0;
            long delay= 1000*60*24;
            Handler h = new Handler();

            h.postDelayed(new Runnable() {
                public void run() {
                    Intent myIntent = new Intent(getApplicationContext(), ServiceNoti.class);
                    startService(myIntent);
                }
            }, delay);
        }
//            String time_ = "01:02 PM";


        return Service.START_STICKY;

    }

    /**
     * A client is binding to the service with bindService()
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /**
     * Called when all clients have unbound with unbindService()
     */
    @Override
    public boolean onUnbind(Intent intent) {
        return mAllowRebind;
    }

    /**
     * Called when a client is binding to the service with bindService()
     */
    @Override
    public void onRebind(Intent intent) {

    }

    /**
     * Called when The service is no longer used and is being destroyed
     */
    @Override
    public void onDestroy() {

    }




    private void generatenotification() {

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = null;
        notificationIntent = new Intent(getApplicationContext(), MenuActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent intent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if (defaultSound == null) {
            defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            if (defaultSound == null) {
                defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            }
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                getApplicationContext()).setContentTitle("BabyBeat").setContentText("Record baby's beat")
                .setContentIntent(intent).setSmallIcon(R.mipmap.ic_launcher)
                .setLights(Color.MAGENTA, 1, 2).setAutoCancel(true)
                .setSound(defaultSound);
        builder.setNumber(notificatn_counter);
        Notification not = new NotificationCompat.BigTextStyle(builder)
                .bigText("Enter your point").build();

//            	not.number=count++;

        if (defaultSound == null) {

        } else {
            not.defaults |= Notification.DEFAULT_VIBRATE;
            not.defaults |= Notification.DEFAULT_SOUND;
        }
        builder.setVisibility(View.VISIBLE);
        if(counter==0){
            notificationManager.notify(notificatn_counter, not);
        }
        counter++;



        Intent myIntent = new Intent(getApplicationContext(), ServiceNoti.class);
        startService(myIntent);
    }



}

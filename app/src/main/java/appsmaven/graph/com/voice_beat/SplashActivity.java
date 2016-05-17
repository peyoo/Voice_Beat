package appsmaven.graph.com.voice_beat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.flurry.android.FlurryAgent;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import service.ServiceNoti;


public class SplashActivity extends AppCompatActivity{
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String saved_date;
    int WRITE_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setContentView(R.layout.splash_activity);

        preferences = PreferenceManager
                .getDefaultSharedPreferences(SplashActivity.this);
        editor = preferences.edit();
        saved_date = preferences.getString("date",null);

        if(saved_date!=null)
        {
            Date end_=null;
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            try {
                 end_=sdf.parse(saved_date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int daysBetween = Days.daysBetween(new DateTime(method_get_current_date()), new DateTime(end_)).getDays();

            int day_passed = Integer.valueOf(daysBetween/7);
            int week_to_save = 40-day_passed;

            editor.putInt("week",week_to_save).commit();
        }

        Intent myIntent = new Intent(SplashActivity.this, ServiceNoti.class);
        startService(myIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("This app needs location access");
                builder.setMessage("Please grant location access so this app can detect ble devices.");
                builder.setPositiveButton("Ok", null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE);
                    }
                });
                builder.show();
            }
            else{
                File direct = new File(Environment.getExternalStorageDirectory()+"/BabyBeat");

                if(!direct.exists()) {
                    if(direct.mkdir()); //directory is created;
                }
            }

        }
        else{
            File direct = new File(Environment.getExternalStorageDirectory()+"/BabyBeat");

            if(!direct.exists()) {
                if(direct.mkdir()); //directory is created;
            }
        }

        android.os.Handler h = new android.os.Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MenuActivity.class));
                finish();
            }
        }, 2000);
    }


    public static Date method_get_current_date() {
        Date date = null;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int  month = calendar.get(Calendar.MONTH);
        int  day = calendar.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
        try {
            date = df1.parse(new StringBuilder().append(day)
                    .append("-").append(month + 1).append("-").append(year)
                    .append(" ").toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    System.out.println("permission granted");
                    File direct = new File(Environment.getExternalStorageDirectory()+"/BabyBeat");

                    if(!direct.exists()) {
                        if(direct.mkdir()); //directory is created;
                    }
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since read/write permission has not been granted for your device, please check Okay to allow.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
                return;
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        // starts a new session
        FlurryAgent.onStartSession(this);
        FlurryAgent.logEvent("Splash fragment");

    }

    @Override
    protected void onStop() {
        super.onStop();

        // ends current session
        FlurryAgent.onEndSession(this);
    }

}

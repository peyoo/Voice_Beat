package utils;

import android.app.Application;

import com.flurry.android.FlurryAgent;

/**
 * Created by Umesh on 16/5/2016.
 */
public class ApplicationActivity extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // configure Flurry
        FlurryAgent.setLogEnabled(true);

        // init Flurry
        FlurryAgent.init(this, "GTWV2B9MSF2CCPMZR3V3");
        FlurryAgent.onStartSession(this, "GTWV2B9MSF2CCPMZR3V3");
    }


}

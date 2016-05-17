package utils;

import android.app.Application;

import com.flurry.android.FlurryAgent;


public class ApplicationActivity extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // configure Flurry
        FlurryAgent.setLogEnabled(true);

        // init Flurry
        FlurryAgent.init(this, "DDY235BJH5P6XM5BTW3W");
        FlurryAgent.onStartSession(this, "DDY235BJH5P6XM5BTW3W");
    }


}

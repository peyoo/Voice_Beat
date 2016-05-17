package fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.flurry.android.FlurryAgent;

import appsmaven.graph.com.voice_beat.R;
import appsmaven.graph.com.voice_beat.VideoActivity;

public class Info_Fragment extends Fragment_Custom {
    View view;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String pref_get_color;
    LinearLayout more_main;
    RelativeLayout rel_week;

    public Info_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.moreinfo_fragment, container, false);

        preferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        editor = preferences.edit();


        findViewById(view);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try{
            final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        }catch (Exception ex){

        }

    }
    private void findViewById(View view) {
        more_main=(LinearLayout)view.findViewById(R.id.more_main);
        rel_week = (RelativeLayout) getActivity().findViewById(R.id.rel_week);

        pref_get_color=preferences.getString("color", "tur");

        rel_week.setVisibility(View.GONE);

        method_set_color(pref_get_color);

    }

    private void method_set_color(String pref_get_color) {
        if(pref_get_color.equals("tur")){
            more_main.setBackgroundResource(R.drawable.landing_one_bg);
        }
        else if(pref_get_color.equals("pink")){
            more_main.setBackgroundResource(R.drawable.pink_back);
        }
        else {
            more_main.setBackgroundResource(R.drawable.blue_back);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        FlurryAgent.onStartSession(getActivity());
        FlurryAgent.logEvent("Info fragment");
    }

    @Override
    public void onStop() {
        super.onStop();
        FlurryAgent.onEndSession(getActivity());
    }
}

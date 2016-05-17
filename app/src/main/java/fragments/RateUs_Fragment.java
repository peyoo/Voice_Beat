package fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;

import appsmaven.graph.com.voice_beat.R;

public class RateUs_Fragment extends Fragment_Custom implements View.OnClickListener {
    View view;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String pref_get_color;
    RelativeLayout more_main;
    private static String selected_start = "no";
    int sdkVersion;
    RelativeLayout rel_week;
    ImageView img_start_two, img_start_one, img_start_three, img_start_four, img_start_five;
    Button card_ok;

    public RateUs_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.rateus_fragment, container, false);

        preferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        editor = preferences.edit();

        sdkVersion = android.os.Build.VERSION.SDK_INT;

        findViewById(view);


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        } catch (Exception ex) {

        }

    }

    private void findViewById(View view) {
        more_main = (RelativeLayout) view.findViewById(R.id.rate_main);
        rel_week = (RelativeLayout) getActivity().findViewById(R.id.rel_week);
        img_start_one = (ImageView) view.findViewById(R.id.img_start_one);
        img_start_two = (ImageView) view.findViewById(R.id.img_start_two);
        img_start_three = (ImageView) view.findViewById(R.id.img_start_three);
        img_start_four = (ImageView) view.findViewById(R.id.img_start_four);
        img_start_five = (ImageView) view.findViewById(R.id.img_start_five);
        card_ok = (Button) view.findViewById(R.id.card_submit);

        pref_get_color = preferences.getString("color", "tur");

        rel_week.setVisibility(View.GONE);
        method_set_color(pref_get_color);

        img_start_one.setOnClickListener(this);
        img_start_two.setOnClickListener(this);
        img_start_three.setOnClickListener(this);
        img_start_four.setOnClickListener(this);
        img_start_five.setOnClickListener(this);
        card_ok.setOnClickListener(this);
    }

    private void method_set_color(String pref_get_color) {
        if (pref_get_color.equals("tur")) {
            more_main.setBackgroundResource(R.drawable.landing_one_bg);
        } else if (pref_get_color.equals("pink")) {
            more_main.setBackgroundResource(R.drawable.pink_back);
        } else {
            more_main.setBackgroundResource(R.drawable.blue_back);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        method_show_popup();
    }


    private void method_show_popup() {
      Dialog  dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_rate_us);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);

        final ImageView img_start_one = (ImageView) dialog.findViewById(R.id.img_start_one);
        final ImageView img_start_two = (ImageView) dialog.findViewById(R.id.img_start_two);
        final ImageView img_start_three = (ImageView) dialog.findViewById(R.id.img_start_three);
        final ImageView img_start_four = (ImageView) dialog.findViewById(R.id.img_start_four);
        final ImageView img_start_five = (ImageView) dialog.findViewById(R.id.img_start_five);

        Button card_ok = (Button) dialog.findViewById(R.id.card_submit);


        img_start_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected_start = "one";
                img_start_one.setBackgroundResource(R.drawable.heart_act);
                img_start_two.setBackgroundResource(R.drawable.heart_inact);
                img_start_three.setBackgroundResource(R.drawable.heart_inact);
                img_start_four.setBackgroundResource(R.drawable.heart_inact);
                img_start_five.setBackgroundResource(R.drawable.heart_inact);
            }
        });

        img_start_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected_start = "one";
                img_start_one.setBackgroundResource(R.drawable.heart_act);
                img_start_two.setBackgroundResource(R.drawable.heart_act);
                img_start_three.setBackgroundResource(R.drawable.heart_inact);
                img_start_four.setBackgroundResource(R.drawable.heart_inact);
                img_start_five.setBackgroundResource(R.drawable.heart_inact);
            }
        });

        img_start_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected_start = "one";
                img_start_one.setBackgroundResource(R.drawable.heart_act);
                img_start_two.setBackgroundResource(R.drawable.heart_act);
                img_start_three.setBackgroundResource(R.drawable.heart_act);
                img_start_four.setBackgroundResource(R.drawable.heart_inact);
                img_start_five.setBackgroundResource(R.drawable.heart_inact);

            }
        });

        img_start_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected_start = "yes";
                img_start_one.setBackgroundResource(R.drawable.heart_act);
                img_start_two.setBackgroundResource(R.drawable.heart_act);
                img_start_three.setBackgroundResource(R.drawable.heart_act);
                img_start_four.setBackgroundResource(R.drawable.heart_act);
                img_start_five.setBackgroundResource(R.drawable.heart_inact);
            }
        });

        img_start_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected_start = "yes";
                img_start_one.setBackgroundResource(R.drawable.heart_act);
                img_start_two.setBackgroundResource(R.drawable.heart_act);
                img_start_three.setBackgroundResource(R.drawable.heart_act);
                img_start_four.setBackgroundResource(R.drawable.heart_act);
                img_start_five.setBackgroundResource(R.drawable.heart_act);
            }
        });


        card_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selected_start.equals("no")) {
                    Toast.makeText(getActivity(), "Select Rating first!!", Toast.LENGTH_LONG).show();
                } else if (img_start_one.getBackground().equals(R.drawable.inactive_star)) {
                    Toast.makeText(getActivity(), "Select Rating first!!", Toast.LENGTH_LONG).show();
                } else if (selected_start.equals("one")) {
                    ////send email

                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.setType("plain/text");
                    sendIntent.setData(Uri.parse("test@gmail.com"));
                    sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                    sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"babyjoey@gmail.com"});
                    sendIntent.putExtra(Intent.EXTRA_SUBJECT, "My Baby's Beat - Customer feedback - Android device");
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "Android app information: \n Pregnancy Week : " + String.valueOf(preferences.getInt("week", 0)) + " \n App Version : 1.0 " +
                            "\n Device Model: " + getDeviceName() + "\nAndroid Version: " + sdkVersion + "");
                    startActivity(sendIntent);
                } else {
                    ////send to play store
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.matisinc.mbb1")));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.matisinc.mbb1")));
                    }
                }
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
               /* FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().;
                Home_Fragment fragment = new Home_Fragment();
                fragmentTransaction.add(R.id.main_fragment, fragment);
                fragmentTransaction.commit();*/


                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new Home_Fragment(), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

      /*  card_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                *//*FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().;
                Home_Fragment fragment = new Home_Fragment();
                fragmentTransaction.add(R.id.main_fragment, fragment);
                fragmentTransaction.commit();*//*

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment,new  Home_Fragment(), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });*/

        dialog.show();
    }


    ///////////////////get device name//////
    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;
        String phrase = "";
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase += Character.toUpperCase(c);
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase += c;
        }
        return phrase;
    }

    @Override
    public void onPause() {
        super.onPause();
        selected_start = "no";


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_start_one:
                selected_start = "one";
                img_start_one.setBackgroundResource(R.drawable.heart_act);
                img_start_two.setBackgroundResource(R.drawable.heart_inact);
                img_start_three.setBackgroundResource(R.drawable.heart_inact);
                img_start_four.setBackgroundResource(R.drawable.heart_inact);
                img_start_five.setBackgroundResource(R.drawable.heart_inact);
                break;

            case R.id.img_start_two:
                selected_start = "one";
                img_start_one.setBackgroundResource(R.drawable.heart_act);
                img_start_two.setBackgroundResource(R.drawable.heart_act);
                img_start_three.setBackgroundResource(R.drawable.heart_inact);
                img_start_four.setBackgroundResource(R.drawable.heart_inact);
                img_start_five.setBackgroundResource(R.drawable.heart_inact);
                break;

            case R.id.img_start_three:
                selected_start = "one";
                img_start_one.setBackgroundResource(R.drawable.heart_act);
                img_start_two.setBackgroundResource(R.drawable.heart_act);
                img_start_three.setBackgroundResource(R.drawable.heart_act);
                img_start_four.setBackgroundResource(R.drawable.heart_inact);
                img_start_five.setBackgroundResource(R.drawable.heart_inact);
                break;

            case R.id.img_start_four:
                selected_start = "yes";
                img_start_one.setBackgroundResource(R.drawable.heart_act);
                img_start_two.setBackgroundResource(R.drawable.heart_act);
                img_start_three.setBackgroundResource(R.drawable.heart_act);
                img_start_four.setBackgroundResource(R.drawable.heart_act);
                img_start_five.setBackgroundResource(R.drawable.heart_inact);
                break;

            case R.id.img_start_five:
                selected_start = "yes";
                img_start_one.setBackgroundResource(R.drawable.heart_act);
                img_start_two.setBackgroundResource(R.drawable.heart_act);
                img_start_three.setBackgroundResource(R.drawable.heart_act);
                img_start_four.setBackgroundResource(R.drawable.heart_act);
                img_start_five.setBackgroundResource(R.drawable.heart_act);
                break;

            case R.id.card_submit:
                if (selected_start.equals("no")) {
                    Toast.makeText(getActivity(), "Select Rating first!!", Toast.LENGTH_LONG).show();
                } else if (img_start_one.getBackground().equals(R.drawable.inactive_star)) {
                    Toast.makeText(getActivity(), "Select Rating first!!", Toast.LENGTH_LONG).show();
                } else if (selected_start.equals("one")) {
                    ////send email

                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.setType("plain/text");
                    sendIntent.setData(Uri.parse("babyjoey@gmail.com"));
                    sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                    sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"babyjoey@gmail.com"});
                    sendIntent.putExtra(Intent.EXTRA_SUBJECT, "My Baby's Beat - Customer feedback - Android device");
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "Android app information: \n Pregnancy Week : " + String.valueOf(preferences.getInt("week", 1)) + " \n App Version : 1.0 " +
                            "\n Device Model: " + getDeviceName() + "\nAndroid Version: " + sdkVersion + "");
                    startActivity(sendIntent);
                } else {
                    ////send to play store

                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.matisinc.mbb1")));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.matisinc.mbb1")));
                    }
                }

                break;


        }
    }

    @Override
    public void onStart() {
        super.onStart();
        FlurryAgent.onStartSession(getActivity());
        FlurryAgent.logEvent("Rateus fragment");
    }

    @Override
    public void onStop() {
        super.onStop();
        FlurryAgent.onEndSession(getActivity());
    }
}

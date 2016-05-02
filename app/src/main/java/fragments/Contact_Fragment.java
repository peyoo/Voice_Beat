package fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import appsmaven.graph.com.voice_beat.R;


public class Contact_Fragment extends Fragment_Custom implements View.OnClickListener {
    View view, view_activity_txt;
    TextView txt_enter_details, txt_week;
    Typeface tf;
    EditText edt_email,edt_query;
    RelativeLayout contact_main, rel_week;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String pref_get_color;
    Button act_chang_color;
    LinearLayout lnr_fb_contact,lnr_send_contact;
    Context ctx;
    Snackbar snackbar;

    public Contact_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.contactus_fragment, container, false);

        ctx = getActivity();
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
        txt_enter_details = (TextView) view.findViewById(R.id.txt_enter_details);
        edt_email = (EditText) view.findViewById(R.id.edt_email);
        edt_query = (EditText) view.findViewById(R.id.edt_query);
        contact_main = (RelativeLayout) view.findViewById(R.id.contact_main);
        act_chang_color = (Button) getActivity().findViewById(R.id.menu_color);
        rel_week = (RelativeLayout) getActivity().findViewById(R.id.rel_week);
        lnr_fb_contact = (LinearLayout) view.findViewById(R.id.lnr_fb_contact);
        lnr_send_contact = (LinearLayout) view.findViewById(R.id.lnr_send_contact);
        act_chang_color.setVisibility(View.GONE);


        pref_get_color = preferences.getString("color", "tur");

        method_set_color(pref_get_color);
        rel_week.setVisibility(View.GONE);

        tf = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/Roboto-Medium.ttf");
        txt_enter_details.setTypeface(tf);

        lnr_fb_contact.setOnClickListener(this);
        lnr_send_contact.setOnClickListener(this);

    }

    private void method_set_color(String pref_get_color) {
        if (pref_get_color.equals("tur")) {
            contact_main.setBackgroundResource(R.drawable.landing_one_bg);
        } else if (pref_get_color.equals("pink")) {
            contact_main.setBackgroundResource(R.drawable.pink_back);
        } else {
            contact_main.setBackgroundResource(R.drawable.blue_back);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lnr_fb_contact:
                getOpenFacebookIntent(ctx);
                break;

            case R.id.lnr_send_contact:
                if(edt_query.getText().toString().trim().length()>0){
                    opengmail();
                }
                else{
                snackbar_method("Enter your query to send");
            }

                break;
        }
    }


    public static void getOpenFacebookIntent(Context context) {
        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
           Intent i =  new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/mybabysbeat"));
            context.startActivity(i);
        } catch (Exception e) {
            Intent i =  new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"));
            context.startActivity(i);
        }

    }


    private void opengmail(){
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setType("plain/text");
        sendIntent.setData(Uri.parse("test@gmail.com"));
        sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
        sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "test@gmail.com" });
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "My Baby's Beat - User queries - Android device"+"\n"+edt_query.getText().toString());
        startActivity(sendIntent);
    }


    private void snackbar_method(String text){
        snackbar = Snackbar
                .make(view, text, Snackbar.LENGTH_LONG)
                .setAction("Ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbar.dismiss();
                    }
                });

        snackbar.setActionTextColor(Color.RED);

        View sbView2 = snackbar.getView();
        TextView textView2 = (TextView) sbView2.findViewById(android.support.design.R.id.snackbar_text);
        textView2.setTextColor(Color.YELLOW);
        snackbar.show();
    }
}

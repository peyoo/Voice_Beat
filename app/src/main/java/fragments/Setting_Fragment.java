package fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import appsmaven.graph.com.voice_beat.R;


public class Setting_Fragment extends Fragment_Custom implements View.OnClickListener, View.OnTouchListener {
    View view, view_actvity;
    LinearLayout frame_temp;
    RelativeLayout lnr_setng;
    com.sa90.materialarcmenu.ArcMenu arcMenu;
    FloatingActionButton fab_recordng, fab_home, fab_help;
    TextView txt_vw_reside, txt_due_date;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    RelativeLayout setng_main, rel_week;
    String pref_get_color, saved_date;
    Button act_chang_color, btn_settng;
    EditText edt_email;
    Snackbar snackbar;

    public Setting_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.setting_fragment, container, false);

        preferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        editor = preferences.edit();

        findViewById(view);

        if (preferences.getBoolean("my_first_time_setng", true)) {
            preferences.edit().putBoolean("my_first_time_setng", false).commit();
        } else {
            view_actvity.setVisibility(View.VISIBLE);
            lnr_setng.setVisibility(View.GONE);
            frame_temp.setVisibility(View.GONE);
            btn_settng.setVisibility(View.GONE);
            fab_home.setEnabled(true);
            fab_recordng.setEnabled(true);
            fab_help.setEnabled(true);
            txt_due_date.setEnabled(true);
            edt_email.setEnabled(true);
        }
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
        view_actvity = getActivity().findViewById(R.id.layout_top);
        act_chang_color = (Button) getActivity().findViewById(R.id.menu_color);
        act_chang_color.setVisibility(View.VISIBLE);
        view_actvity.setVisibility(View.GONE);
        act_chang_color.setBackgroundResource(R.drawable.color_switch);
        rel_week = (RelativeLayout) getActivity().findViewById(R.id.rel_week);
        txt_vw_reside = (TextView) getActivity().findViewById(R.id.txt_vw_reside);
        txt_vw_reside.setText("Settings");

        lnr_setng = (RelativeLayout) view.findViewById(R.id.lnr_setng);
        txt_due_date = (TextView) view.findViewById(R.id.txt_due_date);
        frame_temp = (LinearLayout) view.findViewById(R.id.frame_temp);
        arcMenu = (com.sa90.materialarcmenu.ArcMenu) view.findViewById(R.id.arcMenu);
        fab_help = (FloatingActionButton) view.findViewById(R.id.fab_help);
        fab_recordng = (FloatingActionButton) view.findViewById(R.id.fab_recordng);
        fab_home = (FloatingActionButton) view.findViewById(R.id.fab_home);
        btn_settng = (Button) view.findViewById(R.id.btn_setng);
        edt_email = (EditText) view.findViewById(R.id.edt_email);

        setng_main = (RelativeLayout) view.findViewById(R.id.setng_main);

        pref_get_color = preferences.getString("color", "tur");
        method_set_color(pref_get_color);

        rel_week.setVisibility(View.GONE);

        fab_recordng.setOnClickListener(this);
        fab_help.setOnClickListener(this);
        fab_home.setOnClickListener(this);
        act_chang_color.setOnClickListener(this);
//        btn_settng.setOnClickListener(this);
        txt_due_date.setOnClickListener(this);
        lnr_setng.setOnTouchListener(this);

        //////set disable buttons///////
        fab_home.setEnabled(false);
        fab_recordng.setEnabled(false);
        fab_help.setEnabled(false);
        txt_due_date.setEnabled(false);
        edt_email.setEnabled(false);
        saved_date = preferences.getString("date", null);
        if (saved_date != null) {
            txt_due_date.setText(saved_date);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_home:
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Home_Fragment fragment = new Home_Fragment();
                fragmentTransaction.add(R.id.main_fragment, fragment);
                fragmentTransaction.commit();
                break;

            case R.id.fab_help:
                FragmentManager fragmentManager3 = getFragmentManager();
                FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
                HelpFragment fragment3 = new HelpFragment();
                fragmentTransaction3.add(R.id.main_fragment, fragment3);
                fragmentTransaction3.commit();
                break;

            case R.id.fab_recordng:
                FragmentManager fragmentManager2 = getFragmentManager();
                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                Recording_fragment fragment2 = new Recording_fragment();
                fragmentTransaction2.add(R.id.main_fragment, fragment2);
                fragmentTransaction2.commit();
                break;

            case R.id.menu_color:
                openBottomSheet(view);
                break;

            case R.id.btn_setng:
                view_actvity.setVisibility(View.VISIBLE);
                lnr_setng.setVisibility(View.GONE);
                frame_temp.setVisibility(View.GONE);
                btn_settng.setVisibility(View.GONE);
                fab_home.setEnabled(true);
                fab_recordng.setEnabled(true);
                fab_help.setEnabled(true);
                txt_due_date.setEnabled(true);
                edt_email.setEnabled(true);
                break;

            case R.id.txt_due_date:
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                        new mDateSetListener_depart(), year, month, day);
                dialog.show();
                break;
        }

    }


    //////////////////////bottom sheet..........
    public void openBottomSheet(View v) {

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_menu_color_select, null);
        RelativeLayout txtBackup = (RelativeLayout) view.findViewById(R.id.lnr_dialog_cancel);

        LinearLayout lnr_dialog_three = (LinearLayout) view.findViewById(R.id.lnr_dialog_three);
        LinearLayout lnr_dialog_two = (LinearLayout) view.findViewById(R.id.lnr_dialog_two);
        LinearLayout lnr_dialog_one = (LinearLayout) view.findViewById(R.id.lnr_dialog_one);

        final ImageView img_tick_one = (ImageView) view.findViewById(R.id.img_tick_one);
        final ImageView img_tick_two = (ImageView) view.findViewById(R.id.img_tick_two);
        final ImageView img_tick_three = (ImageView) view.findViewById(R.id.img_tick_three);


        final Dialog mBottomSheetDialog = new Dialog(getActivity(),
                R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(false);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();

        pref_get_color = preferences.getString("color", "tur");

        if (pref_get_color.equals("tur")) {
            img_tick_one.setVisibility(View.VISIBLE);
            img_tick_three.setVisibility(View.GONE);
            img_tick_two.setVisibility(View.GONE);

        } else if (pref_get_color.equals("blue")) {
            img_tick_three.setVisibility(View.VISIBLE);
            img_tick_one.setVisibility(View.GONE);
            img_tick_two.setVisibility(View.GONE);
        } else {
            img_tick_two.setVisibility(View.VISIBLE);
            img_tick_one.setVisibility(View.GONE);
            img_tick_three.setVisibility(View.GONE);
        }

        txtBackup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });

        lnr_dialog_three.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                editor.putString("color", "blue");
                editor.commit();
                editor.apply();
                setng_main.setBackgroundResource(R.drawable.blue_back);
                img_tick_three.setVisibility(View.VISIBLE);
                img_tick_one.setVisibility(View.GONE);
                img_tick_two.setVisibility(View.GONE);
                mBottomSheetDialog.dismiss();

            }
        });
        lnr_dialog_two.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                editor.putString("color", "pink");
                editor.commit();
                editor.apply();
                setng_main.setBackgroundResource(R.drawable.pink_back);
                img_tick_two.setVisibility(View.VISIBLE);
                img_tick_one.setVisibility(View.GONE);
                img_tick_three.setVisibility(View.GONE);
                mBottomSheetDialog.dismiss();

            }
        });
        lnr_dialog_one.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                editor.putString("color", "tur");
                editor.commit();
                editor.apply();
                setng_main.setBackgroundResource(R.drawable.landing_one_bg);
                img_tick_one.setVisibility(View.VISIBLE);
                img_tick_three.setVisibility(View.GONE);
                img_tick_two.setVisibility(View.GONE);
                mBottomSheetDialog.dismiss();

            }
        });


    }


    private void method_set_color(String pref_get_color) {
        if (pref_get_color.equals("tur")) {
            setng_main.setBackgroundResource(R.drawable.landing_one_bg);
        } else if (pref_get_color.equals("pink")) {
            setng_main.setBackgroundResource(R.drawable.pink_back);
        } else {
            setng_main.setBackgroundResource(R.drawable.blue_back);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                view_actvity.setVisibility(View.VISIBLE);
                lnr_setng.setVisibility(View.GONE);
                frame_temp.setVisibility(View.GONE);
                btn_settng.setVisibility(View.GONE);
                fab_home.setEnabled(true);
                fab_recordng.setEnabled(true);
                fab_help.setEnabled(true);
                txt_due_date.setEnabled(true);
                edt_email.setEnabled(true);
                break;
        }
        return true;
    }

    public static Date method_get_current_date() {
        Date date = null;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
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

    class mDateSetListener_depart implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year_, int monthOfYear,
                              int dayOfMonth) {
            int mYear = year_;
            int mMonth = monthOfYear;
            int mDay = dayOfMonth;
            {
                Date start_date = null;
                Date end_date = null;
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); // Set your date format

                    start_date = method_get_current_date();
                    end_date = sdf.parse(new StringBuilder().append(mDay)
                            .append("-").append(mMonth + 1).append("-").append(mYear)
                            .append(" ").toString());
                } catch (Exception ex) {

                }
                if (end_date.before(start_date)) {
                    snackbar_method("Due date has to be more than current...");
                    txt_due_date.setText("");

                } else {
                    int daysBetween = Days.daysBetween(new DateTime(start_date), new DateTime(end_date)).getDays();
                    Log.e("days", daysBetween + "");
                    if (daysBetween > 274) {
                        snackbar_method("Sorry!!Its more than 9 months, enter due date again");
                        txt_due_date.setText("");
                    } else {
                        int day_passed = Integer.valueOf(daysBetween / 7);
                        int week_to_save = 40-day_passed;
                        Log.e("week", day_passed + "");

                        editor.putInt("week", week_to_save).commit();
                        editor.putString("date", String.valueOf(new StringBuilder().append(mDay)
                                .append("-").append(mMonth + 1).append("-").append(mYear)
                                .append(" ").toString())).commit();
                        txt_due_date.setText(new StringBuilder().append(mDay)
                                .append("-").append(mMonth + 1).append("-").append(mYear)
                                .append(" "));
                    }

                }
            }

        }
    }


    private void snackbar_method(String text) {
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


    @Override
    public void onStart() {
        super.onStart();
        FlurryAgent.onStartSession(getActivity());
        FlurryAgent.logEvent("Setting fragment");
    }

    @Override
    public void onStop() {
        super.onStop();
        FlurryAgent.onEndSession(getActivity());
    }
}

package fragments;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kennyc.bottomsheet.BottomSheet;
import com.kennyc.bottomsheet.BottomSheetListener;

import adapter.MyPagerAdapter;
import appsmaven.graph.com.voice_beat.MainActivity;
import appsmaven.graph.com.voice_beat.R;


public class HelpFragment extends Fragment_Custom implements View.OnClickListener, View.OnTouchListener{
    View view,view_actvity;
    ViewPager vpPager;
    Context ctx;
    MyPagerAdapter adapterViewPager;
    LinearLayout frame_temp;
    LinearLayout lnr_setng;
    com.sa90.materialarcmenu.ArcMenu arcMenu;
    FloatingActionButton fab_recordng,fab_home,fab_setng;
    Button btn_actvity;
    TextView txt_vw_reside;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ImageView img_one,img_two,img_three,img_four,img_five,img_six,img_seven,img_eight;
    RelativeLayout help_main,rel_week;
    String pref_get_color;
    Button btn_help;
    private float x1,x2;
    static final int MIN_DISTANCE = 150;
    MediaPlayer mp;
    static String mp_play="";
    boolean isPlaying= false;

    public HelpFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.help_fragmnt, container, false);
        ctx=getActivity();

        preferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        editor = preferences.edit();

        findViewById(view);

        if (preferences.getBoolean("my_first_time_help", true)){
            preferences.edit().putBoolean("my_first_time_help", false).commit();
        }
        else{
            view_actvity.setVisibility(View.VISIBLE);
            lnr_setng.setVisibility(View.GONE);
            frame_temp.setVisibility(View.GONE);
            btn_help.setVisibility(View.GONE);
            fab_home.setEnabled(true);
            fab_recordng.setEnabled(true);
            fab_setng.setEnabled(true);
        }
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
        view_actvity = getActivity().findViewById(R.id.layout_top);
        vpPager=(ViewPager)view.findViewById(R.id.vpPager);
        btn_actvity = (Button) getActivity().findViewById(R.id.menu_color);
        txt_vw_reside = (TextView) getActivity().findViewById(R.id.txt_vw_reside);
        img_one = (ImageView) view.findViewById(R.id.img_one);
        img_two = (ImageView) view.findViewById(R.id.img_two);
        img_three = (ImageView) view.findViewById(R.id.img_three);
        img_four = (ImageView) view.findViewById(R.id.img_four);
        img_five = (ImageView) view.findViewById(R.id.img_five);
        img_six = (ImageView) view.findViewById(R.id.img_six);
        img_seven = (ImageView) view.findViewById(R.id.img_seven);
        img_eight = (ImageView) view.findViewById(R.id.img_eight);
        help_main = (RelativeLayout) view.findViewById(R.id.help_main);
        btn_help = (Button) view.findViewById(R.id.btn_help);
        rel_week = (RelativeLayout) getActivity().findViewById(R.id.rel_week);

        btn_actvity.setVisibility(View.VISIBLE);
        btn_actvity.setBackgroundResource(R.drawable.sound);
        txt_vw_reside.setText("Help");

        view_actvity.setVisibility(View.GONE);
        rel_week.setVisibility(View.GONE);
        adapterViewPager = new MyPagerAdapter(ctx);

        pref_get_color=preferences.getString("color", "tur");
        method_set_color(pref_get_color);

        vpPager.setAdapter(adapterViewPager);
        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               if(position==0){
                   img_one.setBackgroundResource(R.drawable.active_intro);
                   img_two.setBackgroundResource(R.drawable.circleinactive);
                   img_three.setBackgroundResource(R.drawable.circleinactive);
                   img_four.setBackgroundResource(R.drawable.circleinactive);
                   img_five.setBackgroundResource(R.drawable.circleinactive);
                   img_six.setBackgroundResource(R.drawable.circleinactive);
                   img_seven.setBackgroundResource(R.drawable.circleinactive);
                   img_eight.setBackgroundResource(R.drawable.circleinactive);
               }
                else if(position==1){
                   img_one.setBackgroundResource(R.drawable.circleinactive);
                   img_two.setBackgroundResource(R.drawable.active_intro);
                   img_three.setBackgroundResource(R.drawable.circleinactive);
                   img_four.setBackgroundResource(R.drawable.circleinactive);
                   img_five.setBackgroundResource(R.drawable.circleinactive);
                   img_six.setBackgroundResource(R.drawable.circleinactive);
                   img_seven.setBackgroundResource(R.drawable.circleinactive);
                   img_eight.setBackgroundResource(R.drawable.circleinactive);
               }
                else if(position==2){
                   img_one.setBackgroundResource(R.drawable.circleinactive);
                   img_two.setBackgroundResource(R.drawable.circleinactive);
                   img_three.setBackgroundResource(R.drawable.active_intro);
                   img_four.setBackgroundResource(R.drawable.circleinactive);
                   img_five.setBackgroundResource(R.drawable.circleinactive);
                   img_six.setBackgroundResource(R.drawable.circleinactive);
                   img_seven.setBackgroundResource(R.drawable.circleinactive);
                   img_eight.setBackgroundResource(R.drawable.circleinactive);
               }
                else if(position==3) {
                   img_one.setBackgroundResource(R.drawable.circleinactive);
                   img_two.setBackgroundResource(R.drawable.circleinactive);
                   img_three.setBackgroundResource(R.drawable.circleinactive);
                   img_four.setBackgroundResource(R.drawable.active_intro);
                   img_five.setBackgroundResource(R.drawable.circleinactive);
                   img_six.setBackgroundResource(R.drawable.circleinactive);
                   img_seven.setBackgroundResource(R.drawable.circleinactive);
                   img_eight.setBackgroundResource(R.drawable.circleinactive);
               }
                else if(position==4) {
                   img_one.setBackgroundResource(R.drawable.circleinactive);
                   img_two.setBackgroundResource(R.drawable.circleinactive);
                   img_three.setBackgroundResource(R.drawable.circleinactive);
                   img_four.setBackgroundResource(R.drawable.circleinactive);
                   img_five.setBackgroundResource(R.drawable.active_intro);
                   img_six.setBackgroundResource(R.drawable.circleinactive);
                   img_seven.setBackgroundResource(R.drawable.circleinactive);
                   img_eight.setBackgroundResource(R.drawable.circleinactive);
               }
                else if(position==5) {
                   img_one.setBackgroundResource(R.drawable.circleinactive);
                   img_two.setBackgroundResource(R.drawable.circleinactive);
                   img_three.setBackgroundResource(R.drawable.circleinactive);
                   img_four.setBackgroundResource(R.drawable.circleinactive);
                   img_five.setBackgroundResource(R.drawable.circleinactive);
                   img_six.setBackgroundResource(R.drawable.active_intro);
                   img_seven.setBackgroundResource(R.drawable.circleinactive);
                   img_eight.setBackgroundResource(R.drawable.circleinactive);
               }
                else if(position==6) {
                   img_one.setBackgroundResource(R.drawable.circleinactive);
                   img_two.setBackgroundResource(R.drawable.circleinactive);
                   img_three.setBackgroundResource(R.drawable.circleinactive);
                   img_four.setBackgroundResource(R.drawable.circleinactive);
                   img_five.setBackgroundResource(R.drawable.circleinactive);
                   img_six.setBackgroundResource(R.drawable.circleinactive);
                   img_seven.setBackgroundResource(R.drawable.active_intro);
                   img_eight.setBackgroundResource(R.drawable.circleinactive);
               }
                else if(position==7) {
                   img_one.setBackgroundResource(R.drawable.circleinactive);
                   img_two.setBackgroundResource(R.drawable.circleinactive);
                   img_three.setBackgroundResource(R.drawable.circleinactive);
                   img_four.setBackgroundResource(R.drawable.circleinactive);
                   img_five.setBackgroundResource(R.drawable.circleinactive);
                   img_six.setBackgroundResource(R.drawable.circleinactive);
                   img_seven.setBackgroundResource(R.drawable.circleinactive);
                   img_eight.setBackgroundResource(R.drawable.active_intro);
               }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        lnr_setng=(LinearLayout)view.findViewById(R.id.lnr_help);
        frame_temp=(LinearLayout)view.findViewById(R.id.frame_temp);

        arcMenu=(com.sa90.materialarcmenu.ArcMenu)view.findViewById(R.id.arcMenu);
        fab_setng=(FloatingActionButton)view.findViewById(R.id.fab_setng);
        fab_recordng=(FloatingActionButton)view.findViewById(R.id.fab_recordng);
        fab_home=(FloatingActionButton)view.findViewById(R.id.fab_home);

        fab_home.setOnClickListener(this);
        fab_recordng.setOnClickListener(this);
        fab_setng.setOnClickListener(this);
        btn_actvity.setOnClickListener(this);
//        btn_help.setOnClickListener(this);
        lnr_setng.setOnTouchListener(this);

        fab_home.setEnabled(false);
        fab_recordng.setEnabled(false);
        fab_setng.setEnabled(false);


    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()){
            case R.id.menu_color:

                openBottomSheet(view);

                break;

            case R.id.fab_home:
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Home_Fragment fragment = new Home_Fragment();
                fragmentTransaction.add(R.id.main_fragment, fragment);
                fragmentTransaction.commit();
                break;

            case R.id.fab_recordng:
                FragmentManager fragmentManager2 = getFragmentManager();
                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                Recording_fragment fragment2 = new Recording_fragment();
                fragmentTransaction2.add(R.id.main_fragment, fragment2);
                fragmentTransaction2.commit();
                break;


            case R.id.fab_setng:

                FragmentManager fragmentManager3 = getFragmentManager();
                FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
                Setting_Fragment fragment3 = new Setting_Fragment();
                fragmentTransaction3.add(R.id.main_fragment, fragment3);
                fragmentTransaction3.commit();
                break;

            case R.id.btn_help:

                break;
        }
    }



    public void openBottomSheet (View v) {

        View view = getActivity().getLayoutInflater ().inflate(R.layout.dialog_menu_color_select, null);
        RelativeLayout txtBackup = (RelativeLayout)view.findViewById( R.id.lnr_dialog_cancel);
        LinearLayout lnr_three = (LinearLayout)view.findViewById( R.id.lnr_dialog_three);
      final  LinearLayout lnr_two = (LinearLayout)view.findViewById( R.id.lnr_dialog_two);
        final  LinearLayout lnr_one = (LinearLayout)view.findViewById( R.id.lnr_dialog_one);
        TextView txt_dialog_one = (TextView)view.findViewById( R.id.txt_dialog_one);
        TextView txt_dialog_two = (TextView)view.findViewById( R.id.txt_dialog_two);
        RelativeLayout btn_mom = (RelativeLayout)view.findViewById( R.id.btn_one_tur);
        RelativeLayout btn_baby = (RelativeLayout)view.findViewById( R.id.btn_two_pink);
       final RelativeLayout rel_three_help = (RelativeLayout)view.findViewById( R.id.rel_three_help);
       final RelativeLayout rel_two_help = (RelativeLayout)view.findViewById( R.id.rel_two_help);
       final RelativeLayout rel_one_help = (RelativeLayout)view.findViewById( R.id.rel_one_help);

        lnr_three.setVisibility(View.GONE);
        txt_dialog_one.setText("Mom");
        txt_dialog_two.setText("Baby");

        btn_mom.setBackgroundResource(R.drawable.mom);
        btn_baby.setBackgroundResource(R.drawable.baby);

        final Dialog mBottomSheetDialog = new Dialog (getActivity(),
                R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView (view);
        mBottomSheetDialog.setCancelable (false);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show ();


        btn_baby.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                try{
                    mp.stop();
                    mp.release();
//                    mp.reset();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        rel_one_help.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_transparnt));
                        rel_two_help.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_transparnt));
                    }
                    else{
                        rel_one_help.setBackground(getResources().getDrawable(R.drawable.selector_transparnt));
                        rel_two_help.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_transparnt));
                    }
                }catch(Exception ex){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                    alertDialogBuilder.setTitle("Information");
                    alertDialogBuilder.setMessage("Increase your volume to max to hear sound samples");

                    alertDialogBuilder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            arg0.dismiss();
                            mp = MediaPlayer.create(getActivity(), R.raw.baby);
                            mp.start();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                rel_one_help.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_transparnt));
                                rel_two_help.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_bg));
                            }
                            else{
                                rel_one_help.setBackground(getResources().getDrawable(R.drawable.selector_transparnt));
                                rel_two_help.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_bg));
                            }
                        }
                    });


                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

//                    Toast.makeText(getActivity(),"Turn volume to max to hear sound.",Toast.LENGTH_LONG).show();

                }
            }
        });
        btn_mom.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                try{
                    mp.stop();
                    mp.release();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        rel_one_help.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_transparnt));
                        rel_two_help.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_transparnt));
                    }
                    else{
                        rel_one_help.setBackground(getResources().getDrawable(R.drawable.selector_transparnt));
                        rel_two_help.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_transparnt));
                    }
                }catch(Exception ex){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                    alertDialogBuilder.setTitle("Information");
                    alertDialogBuilder.setMessage("Increase your volume to max to hear sound samples");

                    alertDialogBuilder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            arg0.dismiss();
                            mp = MediaPlayer.create(getActivity(), R.raw.mom);
                            mp.start();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                rel_one_help.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_bg));
                                rel_two_help.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_transparnt));
                            }
                            else{
                                rel_one_help.setBackground(getResources().getDrawable(R.drawable.selector_bg));
                                rel_two_help.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_transparnt));
                            }
                        }
                    });


                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }
            }
        });

        txtBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    mp.stop();
                    mp.release();
                }
                catch(Exception ex){

                }

                mBottomSheetDialog.dismiss();
            }
        });


    }


    private void method_set_color(String pref_get_color) {
        if(pref_get_color.equals("tur")){
            help_main.setBackgroundResource(R.drawable.landing_one_bg);
        }
        else if(pref_get_color.equals("pink")){
            help_main.setBackgroundResource(R.drawable.pink_back);
        }
        else {
            help_main.setBackgroundResource(R.drawable.blue_back);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_UP:
                view_actvity.setVisibility(View.VISIBLE);
                lnr_setng.setVisibility(View.GONE);
                frame_temp.setVisibility(View.GONE);
                btn_help.setVisibility(View.GONE);
                fab_home.setEnabled(true);
                fab_recordng.setEnabled(true);
                fab_setng.setEnabled(true);
                break;
        }
        return true;
    }

    @Override
    public void onPause() {
        super.onPause();
        try{
            mp.stop();
            mp.release();
        }
        catch(Exception ex){

        }
    }


}

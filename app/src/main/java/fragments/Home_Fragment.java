package fragments;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.audiofx.NoiseSuppressor;
import android.media.audiofx.Visualizer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.newventuresoftware.waveform.WaveformView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import appsmaven.graph.com.voice_beat.MainActivity;
import appsmaven.graph.com.voice_beat.R;
import recording.AudioDataReceivedListener;
import recording.RecordingThread;
import recording.WavAudioRecorder;
import utils.MyApplication;
import utils.VisualizerView;


public class Home_Fragment extends Fragment_Custom implements View.OnClickListener {
    View view;
    ImageView img_vw_audio_mode;
    private static int image = 0;
    com.sa90.materialarcmenu.ArcMenu fab_home;
    FloatingActionButton fab_recordng, fab_setng, fab_help;
    Context ctx;
    LinearLayout lnr_vw_record, lnr_vw_visualisatn;
    TextView txt_stop;

    private static final String AUDIO_RECORDER_FILE_EXT_MP3 = ".mp3";
    private static final String AUDIO_RECORDER_FOLDER = "AudioRecorder";
    private MediaRecorder recorder = null;
    private int currentFormat = 0;
    private int output_formats[] = {MediaRecorder.OutputFormat.MPEG_4,
            MediaRecorder.OutputFormat.THREE_GPP};
    private String file_exts[] = {AUDIO_RECORDER_FILE_EXT_MP3};
    TextView txt_vw_reside,txt_week,txt_txp_to_rcrd;
    Button btn_actvity;
    private static int play=0;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String pref_get_color;
    RelativeLayout home_main,rel_stop,rel_week;
    Chronometer myChronometer;
    Snackbar snackbar;
    View view_actvity;

    /////////////////////////changes
    File recordingFile;
    File recordingFile_raw;
    boolean isRecording = false,isPlaying = false;
    int frequency = 11025,channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
    int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
    ///////Waveform
    private WaveformView mRealtimeWaveformView;
    private static String file_name="";
    short[] buffer;
    private AudioDataReceivedListener mListener;
    private WavAudioRecorder mRecorder;
    private RecordingThread mRecordingThread;
    AudioManager am;
    ImageView img_fav;
     String record_file_name="";

    public Home_Fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragmnt, container, false);
        ctx = getActivity();

        preferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        editor = preferences.edit();

        findViewById(view);
        am = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);

        return view;
    }


    private void findViewById(View view) {
        view_actvity = getActivity().findViewById(R.id.layout_top);
        img_vw_audio_mode = (ImageView) view.findViewById(R.id.img_vw_audio_mode);
        fab_home = (com.sa90.materialarcmenu.ArcMenu) view.findViewById(R.id.arcMenu);
        fab_help = (FloatingActionButton) view.findViewById(R.id.fab_help);
        fab_recordng = (FloatingActionButton) view.findViewById(R.id.fab_recordng);
        fab_setng = (FloatingActionButton) view.findViewById(R.id.fab_setng);
        lnr_vw_record = (LinearLayout) view.findViewById(R.id.lnr_vw_record);
        lnr_vw_visualisatn = (LinearLayout) view.findViewById(R.id.lnr_vw_visualisatn);
        txt_stop = (TextView) view.findViewById(R.id.txt_stop);
        btn_actvity = (Button) getActivity().findViewById(R.id.menu_color);
        rel_week = (RelativeLayout) getActivity().findViewById(R.id.rel_week);
        txt_week = (TextView) getActivity().findViewById(R.id.txt_week);
        txt_txp_to_rcrd = (TextView) view.findViewById(R.id.txt_txp_to_rcrd);
        home_main=(RelativeLayout)view.findViewById(R.id.home_main);
        rel_stop=(RelativeLayout)view.findViewById(R.id.rel_stop);
        img_fav=(ImageView) view.findViewById(R.id.img_fav);
        myChronometer = (Chronometer)view.findViewById(R.id.chronometer);

        /*Typeface    tf = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/Roboto-Medium.ttf");
        txt_txp_to_rcrd.setTypeface(tf);*/


        view_actvity.setVisibility(View.VISIBLE);
        rel_week.setVisibility(View.VISIBLE);
        txt_week.setVisibility(View.VISIBLE);
        String week_pref =  String.valueOf(preferences.getInt("week",0));
       /* if(!week_pref.equals("0")){
            txt_week.setText(String.valueOf(preferences.getInt("week",1)));
        }
        else{
            rel_week.setVisibility(View.GONE);
        }*/
        txt_week.setText(String.valueOf(preferences.getInt("week",0)));

        mRealtimeWaveformView = (WaveformView)view. findViewById(R.id.waveformView);
        mRecordingThread = new RecordingThread(new AudioDataReceivedListener() {
            @Override
            public void onAudioDataReceived(short[] data) {
                mRealtimeWaveformView.setSamples(data);
            }
        });

        txt_vw_reside = (TextView) getActivity().findViewById(R.id.txt_vw_reside);
        txt_vw_reside.setText("Week");
        btn_actvity.setVisibility(View.GONE);

        pref_get_color=preferences.getString("color", "tur");
        method_set_color(pref_get_color);

        lnr_vw_record.setVisibility(View.VISIBLE);
        img_vw_audio_mode.setOnClickListener(this);
        fab_home.setOnClickListener(this);
        fab_help.setOnClickListener(this);
        fab_setng.setOnClickListener(this);
        fab_recordng.setOnClickListener(this);
        lnr_vw_record.setOnClickListener(this);
        lnr_vw_visualisatn.setOnClickListener(this);
        lnr_vw_visualisatn.setOnClickListener(this);
        rel_stop.setOnClickListener(this);

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
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_vw_audio_mode:
                if (image == 0) {
                    image = 1;
                    img_vw_audio_mode.setBackgroundResource(R.drawable.synthesized);
                } else {
                    image = 0;
                    img_vw_audio_mode.setBackgroundResource(R.drawable.classic);
                }

                break;

            case R.id.lnr_vw_record:

                if(am.isWiredHeadsetOn()) {
                   Toast.makeText(getActivity(),"Please plug out headphone before recording and keep device near to heart of baby!",Toast.LENGTH_LONG).show();
                } else{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        int hasLocationPermission = getActivity().checkSelfPermission( Manifest.permission.RECORD_AUDIO );
                        int hasSMSPermission = getActivity().checkSelfPermission( Manifest.permission.MODIFY_AUDIO_SETTINGS );
                        List<String> permissions = new ArrayList<String>();
                        if( hasLocationPermission != PackageManager.PERMISSION_GRANTED ) {
                            permissions.add( Manifest.permission.RECORD_AUDIO );
                        }

                        if( hasSMSPermission != PackageManager.PERMISSION_GRANTED ) {
                            permissions.add( Manifest.permission.MODIFY_AUDIO_SETTINGS );
                        }

                        if( !permissions.isEmpty() ) {
                            requestPermissions( permissions.toArray( new String[permissions.size()] ), 13 );
                        }

                        else{
                            lnr_vw_record.setVisibility(View.GONE);
                            lnr_vw_visualisatn.setVisibility(View.VISIBLE);
                            showdialog_filename();
                        }
                    }
                    else{
                        lnr_vw_record.setVisibility(View.GONE);
                        lnr_vw_visualisatn.setVisibility(View.VISIBLE);
                        showdialog_filename();
                    }
                }
                break;


            case R.id.rel_stop:
                myChronometer.setBase(SystemClock.elapsedRealtime());
                try{
                    myChronometer.stop();
                    stopRecording();
                    lnr_vw_record.setVisibility(View.VISIBLE);
                    lnr_vw_visualisatn.setVisibility(View.GONE);
                    snackbar_method(view, "recording saved");
                }catch (Exception ex){
                    lnr_vw_record.setVisibility(View.VISIBLE);
                    lnr_vw_visualisatn.setVisibility(View.GONE);
                }
                play=1;
                method_play_now();
                break;


            case R.id.fab_setng:
                if (lnr_vw_visualisatn.getVisibility() == View.VISIBLE) {
                    if(play == 0) {
                        snackbar_method(view,"Stop recording first");
                    }else {
                        lnr_vw_record.setVisibility(View.GONE);
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        Setting_Fragment fragment = new Setting_Fragment();
                        fragmentTransaction.add(R.id.main_fragment, fragment);
                        fragmentTransaction.commit();
                    }
                } else {
                    lnr_vw_record.setVisibility(View.GONE);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Setting_Fragment fragment = new Setting_Fragment();
                    fragmentTransaction.add(R.id.main_fragment, fragment);
                    fragmentTransaction.commit();
                }
                break;


            case R.id.fab_recordng:
                if (lnr_vw_visualisatn.getVisibility() == View.VISIBLE) {
                    if(play==0){
                        snackbar_method(view,"Stop recording first");

                    }else {
                        lnr_vw_record.setVisibility(View.GONE);
                        FragmentManager fragmentManager2 = getFragmentManager();
                        FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                        Recording_fragment fragment2 = new Recording_fragment();
                        fragmentTransaction2.add(R.id.main_fragment, fragment2);
                        fragmentTransaction2.commit();
                    }
                } else {
                    lnr_vw_record.setVisibility(View.GONE);
                    FragmentManager fragmentManager2 = getFragmentManager();
                    FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                    Recording_fragment fragment2 = new Recording_fragment();
                    fragmentTransaction2.add(R.id.main_fragment, fragment2);
                    fragmentTransaction2.commit();
                }

                break;


            case R.id.fab_help:
                if (lnr_vw_visualisatn.getVisibility() == View.VISIBLE) {
                    if(play==0){
                        snackbar_method(view,"Stop recording first");
                    }else {
                        lnr_vw_record.setVisibility(View.GONE);
                        FragmentManager fragmentManager3 = getFragmentManager();
                        FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
                        HelpFragment fragment3 = new HelpFragment();
                        fragmentTransaction3.add(R.id.main_fragment, fragment3);
                        fragmentTransaction3.commit();
                    }
                } else {
                    lnr_vw_record.setVisibility(View.GONE);
                    FragmentManager fragmentManager3 = getFragmentManager();
                    FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
                    HelpFragment fragment3 = new HelpFragment();
                    fragmentTransaction3.add(R.id.main_fragment, fragment3);
                    fragmentTransaction3.commit();
                }

                break;
        }
    }


    public void stopRecording() {
        isRecording = false;
        mRecordingThread.stopRecording();
        mRecorder.stop();
        mRecorder.reset();
        mRecorder.release();
    }


    ////////////////////show dialog////////////////
    private void showdialog_filename() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom_recording_name);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);

        final EditText edt_file_name = (EditText) dialog.findViewById(R.id.edt_recordng_name);

        edt_file_name.requestFocus();
       /* InputMethodManager inputMethodManager=(InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null){
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
        }*/


        Button card_cancel = (Button) dialog.findViewById(R.id.card_cancel);
        Button card_ok = (Button) dialog.findViewById(R.id.card_ok);

        card_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edt_file_name.getText().toString().trim().length() > 3) {

                    file_name = edt_file_name.getText().toString().trim();

                    mRecorder = WavAudioRecorder.getInstanse();
                    record_file_name = Environment.getExternalStorageDirectory() + "/BabyBeat/"+file_name+"-"+String.valueOf(preferences.getInt("week",0))+".wav";
                    mRecorder.setOutputFile(record_file_name);

                    if (WavAudioRecorder.State.INITIALIZING == mRecorder.getState()) {
                        mRecorder.prepare();
                        mRecorder.start();
                        myChronometer.setBase(SystemClock.elapsedRealtime());
                        myChronometer.start();
                    }
                    else if (WavAudioRecorder.State.ERROR == mRecorder.getState()) {
                        mRecorder.release();
                        mRecorder = WavAudioRecorder.getInstanse();
                    }
                    mRecordingThread.startRecording();
                    dialog.dismiss();

                } else {
                    if(edt_file_name.getText().toString().length()>0){
                        snackbar_method(view,"Enter file name  atleast 4 digits");
                    }else{
                        snackbar_method(view,"Enter file name");
                    }


                }

            }
        });
        card_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play=1;
                dialog.dismiss();
                lnr_vw_record.setVisibility(View.VISIBLE);
                lnr_vw_visualisatn.setVisibility(View.GONE);
            }
        });

        dialog.show();
    }




    @Override
    public void onPause() {
        super.onPause();
        try{
            if(play==0){
                lnr_vw_record.setVisibility(View.VISIBLE);
                lnr_vw_visualisatn.setVisibility(View.GONE);
                play=1;
            }
        }catch (Exception ex){

        }
    }


    private void method_set_color(String pref_get_color) {
        if(pref_get_color.equals("tur")){
            home_main.setBackgroundResource(R.drawable.landing_one_bg);
        }
        else if(pref_get_color.equals("pink")){
            home_main.setBackgroundResource(R.drawable.pink_back);
        }
        else {
            home_main.setBackgroundResource(R.drawable.blue_back);
        }
    }


















    private void snackbar_method(View view,String text){
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



    //////////////////////////////////////////////

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 13 && grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            mRecordingThread.stopRecording();
            lnr_vw_record.setVisibility(View.GONE);
            lnr_vw_visualisatn.setVisibility(View.VISIBLE);
            showdialog_filename();
        }
        else if(requestCode == 14 && grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED){
            lnr_vw_record.setVisibility(View.GONE);
            lnr_vw_visualisatn.setVisibility(View.VISIBLE);
            showdialog_filename();
        }
        else{
            snackbar_method(view,"Permission not granted to record audio!!");
        }
    }
    @Override
    public void onResume() {
        super.onResume();
//        MyApplication.getInstance().trackScreenView("Footer Fragment");
    }


    private void method_play_now(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Recording Saved");
        alertDialogBuilder.setMessage("Do you want to play this recording now?");

        alertDialogBuilder.setPositiveButton("Play", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.dismiss();
                Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);
                File file = new File(record_file_name);
                intent.setDataAndType(Uri.fromFile(file), "audio");
                ctx.startActivity(intent);
                Toast.makeText(getActivity(),"You can listen to recording again in recording list",Toast.LENGTH_LONG).show();

            }
        });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.dismiss();
            }
        });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}


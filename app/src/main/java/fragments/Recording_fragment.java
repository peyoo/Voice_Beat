package fragments;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hudomju.swipe.OnItemClickListener;
import com.hudomju.swipe.SwipeToDismissTouchListener;
import com.hudomju.swipe.SwipeableItemClickListener;
import com.hudomju.swipe.adapter.RecyclerViewAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import adapter.Recording_adapter;
import appsmaven.graph.com.voice_beat.R;
import utils.SongGetter_Setter;

import static android.widget.Toast.LENGTH_SHORT;


public class Recording_fragment extends Fragment_Custom implements View.OnClickListener {
    View view;
    Context ctx;
    RecyclerView recycle_vw_recordng;
    FloatingActionButton fab_setng, fab_home, fab_help;
    private static final String AUDIO_RECORDER_FOLDER = "AudioRecorder";
    private static final String AUDIO_RECORDER_FILE_EXT_MP3 = ".mp3";
    ArrayList<String> list_file = new ArrayList<String>();
    ArrayList<String> list_file_title = new ArrayList<String>();
    ArrayList<String> list_file_duration = new ArrayList<String>();
    ArrayList<String> list_file_week_entered = new ArrayList<String>();
    ArrayList<String> list_file_name_full = new ArrayList<String>();
    private String file_exts[] = {AUDIO_RECORDER_FILE_EXT_MP3};
    TextView txt_vw_reside;
    String filepath, file_title, duration_to_add;
    MediaPlayer mp;
    int duration;
    Button btn_actvity;
    FrameLayout recording_main;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String pref_get_color;
    Snackbar snackbar;
    public static int play = 0;
    View view_actvity;
    RelativeLayout rel_week;
    ArrayList<SongGetter_Setter> array_album = new ArrayList<>();
    SongGetter_Setter getter_obj;
    String week_saved;
    Recording_adapter adapter;
    SwipeToDismissTouchListener<RecyclerViewAdapter> touchListener;
    public Recording_fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.recordng_fragment, container, false);
        ctx = getActivity();

        preferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        editor = preferences.edit();
        findViewById(view);



        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
//        itemTouchHelper.attachToRecyclerView(recycle_vw_recordng);
        return view;
    }

    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }


        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            //Remove swiped item from list and notify the RecyclerView
//            Toast.makeText(getActivity(),"pos",5).show();
        }
    };
    private void init_swipe(){
        touchListener =
                new SwipeToDismissTouchListener<>(
                        new RecyclerViewAdapter(recycle_vw_recordng),
                        new SwipeToDismissTouchListener.DismissCallbacks<RecyclerViewAdapter>() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }
                            @Override
                            public void onPendingDismiss(RecyclerViewAdapter recyclerView, int position) {

                            }

                            @Override
                            public void onDismiss(RecyclerViewAdapter view, final int position) {
//                                adapter.remove(position);
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                                alertDialogBuilder.setTitle("Alert!!");
                                alertDialogBuilder.setMessage("Are you sure you want to delete this recording?");

                                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        arg0.dismiss();
                                        touchListener.processPendingDismisses();

                                        adapter.remove(position);
                                        try{
                                            File file = new File(filepath, "/BabyBeat/"+list_file_name_full.get(position));
                                            file.delete();
                                        }catch(Exception ex){
                                           Toast.makeText(getActivity(),"Sorry, not able to delete file!!",Toast.LENGTH_LONG).show();
                                        }


                                    }
                                });
                                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        arg0.dismiss();
                                        touchListener.undoPendingDismiss();
                                    }
                                });


                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            }
                        });
        touchListener.setDismissDelay(0);
        recycle_vw_recordng.setOnTouchListener(touchListener);
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        recycle_vw_recordng.setOnScrollListener((RecyclerView.OnScrollListener) touchListener.makeScrollListener());
       /* recycle_vw_recordng.addOnItemTouchListener(new SwipeableItemClickListener(getActivity(),
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {
                        if (view.getId() == R.id.txt_delete) {


                        } else if (view.getId() == R.id.txt_undo) {
                            touchListener.undoPendingDismiss();
                        } else { // R.id.txt_data
                            Toast.makeText(getActivity(), "Position " + position, LENGTH_SHORT).show();
                        }
                    }
                }));*/
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
        recycle_vw_recordng = (RecyclerView) view.findViewById(R.id.recycle_vw_recordng);
        fab_help = (FloatingActionButton) view.findViewById(R.id.fab_help);
        fab_setng = (FloatingActionButton) view.findViewById(R.id.fab_setng);
        fab_home = (FloatingActionButton) view.findViewById(R.id.fab_home);
        btn_actvity = (Button) getActivity().findViewById(R.id.menu_color);
        recording_main = (FrameLayout) view.findViewById(R.id.recording_main);
        rel_week = (RelativeLayout) getActivity().findViewById(R.id.rel_week);

        pref_get_color = preferences.getString("color", "tur");
        method_set_color(pref_get_color);
        view_actvity = getActivity().findViewById(R.id.layout_top);

        LinearLayoutManager llm = new LinearLayoutManager(ctx);
        recycle_vw_recordng.setLayoutManager(llm);
        btn_actvity.setVisibility(View.GONE);

        txt_vw_reside = (TextView) getActivity().findViewById(R.id.txt_vw_reside);
        txt_vw_reside.setText("Recordings");

        rel_week.setVisibility(View.GONE);

        view_actvity.setVisibility(View.VISIBLE);
        fab_home.setOnClickListener(this);
        fab_setng.setOnClickListener(this);
        fab_help.setOnClickListener(this);
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


            case R.id.fab_setng:
                FragmentManager fragmentManager2 = getFragmentManager();
                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                Setting_Fragment fragment2 = new Setting_Fragment();
                fragmentTransaction2.add(R.id.main_fragment, fragment2);
                fragmentTransaction2.commit();
                break;

            case R.id.fab_help:
                FragmentManager fragmentManager3 = getFragmentManager();
                FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
                HelpFragment fragment3 = new HelpFragment();
                fragmentTransaction3.add(R.id.main_fragment, fragment3);
                fragmentTransaction3.commit();
                break;
        }
    }


    //////////////list populisation///////
    private void getFilename() {
        filepath = Environment.getExternalStorageDirectory().getPath();
//        final String mRcordFilePath = Environment.getExternalStorageDirectory() + "/BabyBeat/"+file_name+".wav";
//        File file = new File(filepath, AUDIO_RECORDER_FOLDER);
      /*  File file = new File(
                Environment.getExternalStorageDirectory().getAbsolutePath()
                        + "/Android/data/appsmaven.graph.com.voice_beat/files/");
        String filepath = Environment.getExternalStorageDirectory().getPath();*/
        File file = new File(filepath, "/BabyBeat");


        if (!file.exists()) {
//            Snackbar.make(view, "No Recording", Snackbar.LENGTH_LONG).setActionTextColor(Color.WHITE).show();

            snackbar = Snackbar
                    .make(view, "No Recording", Snackbar.LENGTH_LONG)
                    .setAction("Ok", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            snackbar.dismiss();
                        }
                    });

            snackbar.setActionTextColor(Color.RED);

            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        } else {
            File file_[] = file.listFiles();
            for (File f : file.listFiles()) {
                if (f.isFile()) {
                    file_title = f.getName();
                    list_file_name_full.add(file_title);
                    if (file_title.contains("#")) {
                        String[] separated = file_title.split("#");
                        list_file_title.add(separated[0]);
                        Log.e(".wav week",separated[1]);
                        if(separated[1].contains(".wav")){
                            String separated_week = separated[1].replace(".wav","");
                            list_file_week_entered.add("Week "+separated_week.trim());
                        }else {
                            list_file_week_entered.add("Week "+separated[1]);
                        }
                    } else {
                        list_file_week_entered.add("Week " + "1");
                        if(file_title.contains(".wav")){
                            file_title = file_title.replace(".wav","");
                        }
                        list_file_title.add(file_title.trim());
                    }
                 /*   try{
                        String[] separated = file_title.split("#");
                        list_file_title.add(separated[0]);
//                        list_file_week_entered.add("Week "+separated[1]);
                        String[] separated_wav = separated[1].split(".");
                        Log.e(".wav",separated[1]);
                        Log.e(".wav",separated_wav[0]);
                        list_file_week_entered.add("Week "+separated_wav[0]);
                    }catch(Exception ex){

                        list_file_week_entered.add("Week "+"0");
                        list_file_title.add(file_title);
                    }*/

                }
            }

            for (int i = 0; i < file_.length; i++) {
                list_file.add(String.valueOf(file_[i]));
                String name_file = String.valueOf(file_[i]);

                try {
                    mp = MediaPlayer.create(getActivity(), Uri.parse(name_file));
                    duration = mp.getDuration();
                    duration_to_add = String.format("%d:%d",
                            TimeUnit.MILLISECONDS.toMinutes(duration),
                            TimeUnit.MILLISECONDS.toSeconds(duration) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));

                    list_file_duration.add(duration_to_add);
                } catch (Exception ex) {
                    list_file_duration.add("00:00");
                }
            }

        }


    }


    private void method_set_color(String pref_get_color) {
        if (pref_get_color.equals("tur")) {
            recording_main.setBackgroundResource(R.drawable.landing_one_bg);
        } else if (pref_get_color.equals("pink")) {
            recording_main.setBackgroundResource(R.drawable.pink_back);
        } else {
            recording_main.setBackgroundResource(R.drawable.blue_back);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        play = 0;

    }


    public void ListAllSongs() {
        String[] STAR = {"*"};
        getter_obj = new SongGetter_Setter();
        Uri allsongsuri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        Cursor cursor = getActivity().managedQuery(allsongsuri, STAR, selection, null, null);
        int totalSongs = cursor.getCount();
        Log.e("totalSongs", totalSongs + "");
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String songname = cursor
                            .getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    int song_id = cursor.getInt(cursor
                            .getColumnIndex(MediaStore.Audio.Media._ID));
                    String fullpath = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Audio.Media.DATA));
//                        fullsongpath.add(fullpath);
                    String albumname = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    int album_id = cursor
                            .getInt(cursor
                                    .getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                    String artistname = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Audio.Media.ARTIST));


                    int artist_id = cursor
                            .getInt(cursor
                                    .getColumnIndex(MediaStore.Audio.Media.ARTIST_ID));
                    Uri songCover = Uri.parse("content://media/external/audio/albumart");

                    Uri uriSongCover = ContentUris.withAppendedId(songCover, album_id);
                    Uri sArtworkUri = Uri
                            .parse("content://media/external/audio/albumart");

                    Uri uri = ContentUris.withAppendedId(sArtworkUri,
                            album_id);
                    for (int i = 0; i < list_file_title.size(); i++) {
                        if (list_file_title.get(i).equals(albumname)) {
                            getter_obj.setAlbum_id(String.valueOf(album_id));
                            getter_obj.setAlbum_name(albumname);
                            array_album.add(getter_obj);
                        }
                    }


                    Log.e("songname", songname + "");
                    Log.e("songid", album_id + "");
                    Log.e("uri", uri + "");
                    Log.e("uriSongCover", uriSongCover + "");
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("This app needs location access");
                builder.setMessage("Please grant location access so this app can detect ble devices.");
                builder.setPositiveButton("Ok", null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    }
                });
                builder.show();
            }
            else{
                getFilename();
                adapter = new Recording_adapter(list_file, list_file_title, list_file_duration, list_file_week_entered,list_file_name_full);
                recycle_vw_recordng.setAdapter(adapter);
                init_swipe();
            }
        }
        else{
            getFilename();
            adapter = new Recording_adapter(list_file, list_file_title, list_file_duration, list_file_week_entered,list_file_name_full);
            recycle_vw_recordng.setAdapter(adapter);
            init_swipe();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    System.out.println("permission granted");
                    getFilename();
                    adapter = new Recording_adapter(list_file, list_file_title, list_file_duration, list_file_week_entered,list_file_name_full);
                    recycle_vw_recordng.setAdapter(adapter);
                    init_swipe();
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
}

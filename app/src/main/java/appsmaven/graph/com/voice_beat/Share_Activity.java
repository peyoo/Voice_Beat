package appsmaven.graph.com.voice_beat;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import utils.CircleTransform;

import static android.R.color.transparent;


public class Share_Activity extends AppCompatActivity implements View.OnClickListener {
    ImageView img_vw_share, img_vw_add_pic;
    String get_file_name, get_file_title, get_file_duration;
    TextView txt_week, txt_add_pic,txt_title;
    RelativeLayout rel_add_image, rel_util_round, share_main;
    int Camera_Code = 100;
    int Gallery_Code = 200;
    Uri selectedImageUri;
    String selectedPath, changed_photopath, pref_get_color;
    utils.RoundedImage profile_round_img;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String get_week;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share);

        get_file_name = getIntent().getStringExtra("file_name");
        get_file_title = getIntent().getStringExtra("file_title");
        get_file_duration = getIntent().getStringExtra("file_duration");
        get_week = getIntent().getStringExtra("file_week");

        String[] separated = get_file_title.split(".mp3");
        get_file_title = separated[0];

        preferences = PreferenceManager
                .getDefaultSharedPreferences(Share_Activity.this);
        editor = preferences.edit();

        findViewByid();
        setActionBar();
    }

    private void findViewByid() {
        img_vw_share = (ImageView) findViewById(R.id.img_vw_share);
        img_vw_add_pic = (ImageView) findViewById(R.id.img_add_pic);
        rel_add_image = (RelativeLayout) findViewById(R.id.rel_add_image);
        share_main = (RelativeLayout) findViewById(R.id.share_main);
        rel_util_round = (RelativeLayout) findViewById(R.id.util_round);
        txt_week = (TextView) findViewById(R.id.txt_week);
        txt_add_pic = (TextView) findViewById(R.id.txt_add_pic);
        txt_title = (TextView) findViewById(R.id.txt_title);
        profile_round_img = (utils.RoundedImage) findViewById(R.id.profile_img);

        txt_week.setText(get_week + " - " + get_file_duration );
        txt_title.setText(get_file_title);
        pref_get_color = preferences.getString("color", "tur");

        method_set_color(pref_get_color);

        img_vw_share.setOnClickListener(this);
        rel_add_image.setOnClickListener(this);
        rel_util_round.setOnClickListener(this);
    }

    private void method_set_color(String pref_get_color) {
        if (pref_get_color.equals("tur")) {
            share_main.setBackgroundResource(R.drawable.landing_one_bg);
        } else if (pref_get_color.equals("pink")) {
            share_main.setBackgroundResource(R.drawable.pink_back);
        } else {
            share_main.setBackgroundResource(R.drawable.blue_back);
        }
    }


    private void setActionBar() {
        try {
            final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            android.support.v7.app.ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                    supportFinishAfterTransition();
                } else {
                    Share_Activity.this.finish();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_vw_share:
                method_share_audio();

                break;

            case R.id.rel_add_image:
                method_show_image_selector_popup();

                break;

            case R.id.util_round:
                method_show_image_selector_popup();

                break;
        }
    }

    private void method_show_image_selector_popup() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 100);
    }


    private void method_share_audio() {
        String sharePath = Environment.getExternalStorageDirectory().getPath()
                + get_file_name;
        Uri uri = Uri.parse(sharePath);
       /* Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("audio*//**//*");
        share.setType("audio*//**//*");
        share.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(share, "Share Sound File"));*/


        Intent intent = null;
        ArrayList<Uri> files = new ArrayList<Uri>();



        files.add(uri);
        if(selectedPath!=null){
            Uri uri2 = Uri.parse(selectedPath);
            files.add(uri2);
        }



        intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
        intent.setType("audio*//*");
        if(selectedPath!=null){
            intent.setType("image/*");
        }


        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(Intent.EXTRA_TEXT, "Some message");
        startActivity(Intent.createChooser(intent, "compatible apps:"));
      /*  Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/html");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml("<p>Baby Beat</p>"));
        startActivity(Intent.createChooser(sharingIntent,"Share using"));*/
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            txt_add_pic.setVisibility(View.GONE);
            img_vw_add_pic.setVisibility(View.GONE);
            profile_round_img.setVisibility(View.VISIBLE);
            rel_util_round.setVisibility(View.VISIBLE);
            rel_add_image.setBackgroundColor(getResources().getColor(transparent));
            if (data.getData() != null) {
                selectedImageUri = data.getData();
            } else {

            }

            if (requestCode == 100 && resultCode == RESULT_OK) {
                selectedPath = getPath(selectedImageUri);
                Picasso.with(Share_Activity.this).load(new File(selectedPath)).skipMemoryCache().
                        transform(new CircleTransform()).error(R.drawable.baby2).
                        into(profile_round_img);
                base64(selectedPath);
                Log.e("selectedPath1 : ", selectedPath);

            }

            if (requestCode == 10) {
                selectedPath = getPath(selectedImageUri);


                profile_round_img.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));


                Picasso.with(Share_Activity.this).load(selectedPath).skipMemoryCache().
                        transform(new CircleTransform())
                        .error(R.drawable.baby2);
                base64(selectedPath);
                Log.e("selectedPath1 : ", selectedPath);
            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);

    }

    /**
     * converting to Base64
     */
    public String base64(String path) {
        File imageFile = new File(path);
        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
        byte[] image = stream.toByteArray();
        String img_str = Base64.encodeToString(image, 0);
        changed_photopath = img_str;
        return img_str;
    }


    @Override
    protected void onResume() {
        super.onResume();


    }

    public void getRealPathFromURI(Context context) {
        Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
        ContentResolver res = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put("album_id", 3);
        values.put("_data", selectedPath);
        Uri newuri = res.insert(sArtworkUri, values);
    }



    @Override
    protected void onStart() {
        super.onStart();

        // starts a new session
        FlurryAgent.onStartSession(this);
        FlurryAgent.logEvent("Share fragment");

    }

    @Override
    protected void onStop() {
        super.onStop();

        // ends current session
        FlurryAgent.onEndSession(this);
    }

}

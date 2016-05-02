package appsmaven.graph.com.voice_beat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;

/**
 * Created by Umesh on 4/13/2016.
 */
public class VideoActivity extends AppCompatActivity {
    VideoView vido_vw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videoclass);

        vido_vw=(VideoView)findViewById(R.id.vidoe_vw);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(VideoActivity.this,MenuActivity.class);
        startActivity(i);
        finish();
    }
}

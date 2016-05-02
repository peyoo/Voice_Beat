package adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import appsmaven.graph.com.voice_beat.R;
import appsmaven.graph.com.voice_beat.Share_Activity;


public class Recording_adapter extends RecyclerView.Adapter<Recording_adapter.PersonViewHolder> {
    Context ctx;
    ArrayList<String> list_file = new ArrayList<String>();
    ArrayList<String> list_file_title = new ArrayList<String>();
    ArrayList<String> list_file_duration = new ArrayList<String>();
    ArrayList<String> list_file_week_entered = new ArrayList<String>();
    ArrayList<String> list_file_name_full = new ArrayList<String>();

    int frequency = 11025, channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
    int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
    String file_name;
    boolean isPlaying = false;
    File recordingFile;
    AudioManager am;
    Snackbar snackbar;

    public Recording_adapter(ArrayList<String> list_file, ArrayList<String> list_file_title,
                             ArrayList<String> list_file_duration, ArrayList<String> list_file_week_entered, ArrayList<String> list_file_name_full) {
        this.list_file = list_file;
        this.list_file_title = list_file_title;
        this.list_file_duration = list_file_duration;
        this.list_file_week_entered = list_file_week_entered;
        this.list_file_name_full = list_file_week_entered;
    }


    @Override
    public Recording_adapter.PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_recording_adapter, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        ctx = viewGroup.getContext();
        am = (AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE);
        return pvh;
    }

    public void remove(int position) {
        list_file.remove(position);
        list_file_title.remove(position);
        list_file_duration.remove(position);
        list_file_week_entered.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(final Recording_adapter.PersonViewHolder holder, final int position) {
        holder.lnr_vw_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ctx, Share_Activity.class);
                i.putExtra("file_name", list_file.get(position));
                i.putExtra("file_duration", list_file_duration.get(position));
                i.putExtra("file_title", list_file_title.get(position));
                i.putExtra("file_full_name", list_file_name_full.get(position));
                ctx.startActivity(i);

            }
        });


        holder.rel_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (am.isWiredHeadsetOn()) {
                    Intent intent = new Intent();
                    intent.setAction(android.content.Intent.ACTION_VIEW);
                    File file = new File(list_file.get(position));
                    intent.setDataAndType(Uri.fromFile(file), "audio");
                    ctx.startActivity(intent);
                } else {
                    snackbar = Snackbar
                            .make(view, "Plug in headphones first to hear..", Snackbar.LENGTH_LONG)
                            .setAction("Ok", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    snackbar.dismiss();
                                    Intent intent = new Intent();
                                    intent.setAction(android.content.Intent.ACTION_VIEW);
                                    File file = new File(list_file.get(position));
                                    intent.setDataAndType(Uri.fromFile(file), "audio");
                                    ctx.startActivity(intent);
                                }
                            });

                    snackbar.setActionTextColor(Color.RED);

                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                }


            }
        });

        holder.txt_week.setText(list_file_week_entered.get(position));
//        holder.txt_week.setText(list_file_week_entered.get(position));
        holder.txt_time.setText(list_file_duration.get(position));
        holder.txt_title.setText(list_file_title.get(position));
//        holder.txt_time.setText(list_file_title.get(position));

    }


    @Override
    public int getItemCount() {
        return list_file.size();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        LinearLayout lnr_vw_share;
        RelativeLayout rel_play;
        TextView txt_title, txt_week, txt_time;
        public ImageView img_play;

        PersonViewHolder(View itemView) {
            super(itemView);
            lnr_vw_share = (LinearLayout) itemView.findViewById(R.id.lnr_vw_share);
            rel_play = (RelativeLayout) itemView.findViewById(R.id.rel_play);
            txt_title = (TextView) itemView.findViewById(R.id.txt_title);
            txt_week = (TextView) itemView.findViewById(R.id.txt_week);
            txt_time = (TextView) itemView.findViewById(R.id.txt_time);
            img_play = (ImageView) itemView.findViewById(R.id.img_play);

        }
    }


}
package fragments;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import appsmaven.graph.com.voice_beat.R;


public class Fragment_Custom extends Fragment
{


    public void setActionBar(View rootView,String Title)
    {
        try
        {
            final Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
           /* android.support.v7.app.ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
            TextView title = (TextView)toolbar.findViewById(R.id.txt_titleTV);
            title.setText(Title);*/
//            actionBar.setTitle(Title);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


}

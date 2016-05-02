package appsmaven.graph.com.voice_beat;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kennyc.bottomsheet.BottomSheet;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import fragments.Contact_Fragment;
import fragments.Home_Fragment;
import fragments.Info_Fragment;
import fragments.RateUs_Fragment;


public class MenuActivity extends FragmentActivity implements View.OnClickListener{

    private ResideMenu resideMenu;
    private MenuActivity mContext;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemProfile;
    private ResideMenuItem itemSettings;
    TextView txt_vw_reside;
    Button menu_color;
    boolean doubleBackToExitPressedOnce = false;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reside_menu);
        mContext = this;
        setUpMenu();
        if( savedInstanceState == null )
            changeFragment(new Home_Fragment());
    }

    private void setUpMenu() {

        //////find view by id.////
        txt_vw_reside=(TextView)findViewById(R.id.txt_vw_reside);
        menu_color=(Button)findViewById(R.id.menu_color);

        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setUse3D(true);
        resideMenu.setBackground(R.drawable.nav_bg);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
        resideMenu.setScaleValue(0.6f);

        // create menu items;
        itemHome     = new ResideMenuItem(this, R.drawable.home,     R.string.nav_item_home);
        itemProfile  = new ResideMenuItem(this, R.drawable.rate,  R.string.nav_rate_us);
//        itemCalendar = new ResideMenuItem(this, R.drawable.info, R.string.nav_info);
        itemSettings = new ResideMenuItem(this, R.drawable.contact, R.string.nav_contct);

        itemHome.setOnClickListener(this);
        itemProfile.setOnClickListener(this);
//        itemCalendar.setOnClickListener(this);
        itemSettings.setOnClickListener(this);
        menu_color.setOnClickListener(this);

        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemProfile, ResideMenu.DIRECTION_LEFT);
//        resideMenu.addMenuItem(itemCalendar, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_LEFT);

        // You can disable a direction by setting ->
         resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
         resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_LEFT);

        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {

        if (view == itemHome){
            changeFragment(new Home_Fragment());
            txt_vw_reside.setText("Week");
        }else if (view == itemProfile){
            changeFragment(new RateUs_Fragment());
            txt_vw_reside.setText("Rate Us");
        }else if (view == itemSettings){
            changeFragment(new Contact_Fragment());
            txt_vw_reside.setText("Contact Us");
        }else if (view == menu_color){
            new BottomSheet.Builder(getApplicationContext())
                    .setView(R.layout.dialog_menu_color_select)
                    .show();
        }

        resideMenu.closeMenu();
    }

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
        }

        @Override
        public void closeMenu() {
        }
    };

    private void changeFragment(Fragment targetFragment){
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    // What good method is to access resideMenu？
    public ResideMenu getResideMenu(){
        return resideMenu;
    }


    @Override
    public void onBackPressed() {
        if(resideMenu.isOpened()){
            resideMenu.closeMenu();
        }
        else{
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press Back again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }

    }
}

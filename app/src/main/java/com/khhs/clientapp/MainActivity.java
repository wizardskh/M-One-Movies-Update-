package com.khhs.clientapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import es.dmoral.toasty.Toasty;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    static Toolbar toolbar;
    static String currentFrag="";
    static String preFrag = "";
    DrawerLayout drawer;
    LinearLayout numberpin,maindata;
    EditText edtnumber;
    Button sure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      requestWindowFeature(Window.FEATURE_NO_TITLE);
getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        numberpin =findViewById(R.id.numberpin);
        edtnumber = findViewById(R.id.number);
        sure = findViewById(R.id.sure);
        maindata = findViewById(R.id.maindata);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtnumber.getText().toString().trim().equals(getString(R.string.number_pin)))
                {
                    setFragment(new HomeFragment());
                    numberpin.setVisibility(View.GONE);

                    maindata.setVisibility(View.VISIBLE);
                }
                else {
                    Toasty.error(getApplicationContext(),"နံပါတ်မှားနေပါသည်",Toasty.LENGTH_LONG).show();
                }
            }
        });
        drawer = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Home");
        currentFrag = getString(R.string.home_frag);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer,
                toolbar,
                R.string.openDrawer,
                R.string.closeDrawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navView);

        View headerView  = navigationView.getHeaderView(0);
        TextView txtVersion = headerView.findViewById(R.id.versionName);
        txtVersion.setText("Version " +BuildConfig.VERSION_NAME);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.home_menu)
                {
                    setFragment(new HomeFragment());
                    setTitle(getString(R.string.home_frag));
                    currentFrag=getString(R.string.home_frag);
                }
                else if(item.getItemId() == R.id.movie_menu)
                {
                    setFragment(new MovieFragment());
                    setTitle(getString(R.string.movie_frag));
                    currentFrag = getString(R.string.movie_frag);
                }
                else if(item.getItemId() == R.id.series_menu)
                {
                    setFragment(new SeriesFragment());
                    setTitle(getString(R.string.series_frag));
                    currentFrag =getString( R.string.series_frag);
                }

                else if(item.getItemId() == R.id.share_menu)
                {
                    Intent shareIntent = new Intent();

                    currentFrag = getString(R.string.share_frag);
                    shareIntent.setAction(Intent.ACTION_SEND);

                    shareIntent.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id="+BuildConfig.APPLICATION_ID);


                   /* shareIntent.putExtra(Intent.EXTRA_TEXT,"https://www.mediafire.com/file/2p2k8wtb1vvamii/cartoon_cafev2.apk/file");*/
                    shareIntent.setType("text/plain");
                    startActivity(shareIntent);
                }
                else if(item.getItemId()==R.id.search_menu)
                {
                    currentFrag=getString(R.string.search_frag);
                    setFragment(new SearchFragment());
                    setTitle(currentFrag);

                }
                else if(item.getItemId() == R.id.request_menu)
                {
                    currentFrag  = getString(R.string.request_str);
                    setFragment(new RequestFragment());
                    setTitle(getString(R.string.request_str));
                }

                /*else if(item.getItemId() == R.id.about_menu)
                {
                    setFragment(new AboutFragment());
                    setTitle(getString(R.string.about_frag)
                    );
                    setTitle(getString(R.string.about_frag));
                }*/

                if(VideoDetailFragment.player!=null)
                {
                    VideoDetailFragment.player.stop();;
                }
                drawer.closeDrawer(Gravity.LEFT);
                return false;
            }
        });

    }

    public void setFragment(Fragment f)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.navContent,f);
        ft.commit();

    }


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    public void onBackPressed() {
        if(currentFrag.equals(getString(R.string.home_frag)))
        {
            if(drawer.isDrawerOpen(Gravity.LEFT))
            {
                drawer.closeDrawer(Gravity.LEFT);
            }
            else
            {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Confirmation");
                alert.setMessage("Are You Sure To EXit?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();;
                        finishAffinity();;
                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
            }
        }
        else if(currentFrag.equals(getString(R.string.movie_frag)) ||
        currentFrag.equals(getString(R.string.request_str))
        || currentFrag.equals(getString(R.string.series_frag))
        || currentFrag.equals(getString(R.string.about_frag))
        ||currentFrag.equals(getString(R.string.search_frag))
        )
        {
            setFragment(new HomeFragment());
            currentFrag = getString(R.string.home_frag);
            setTitle(currentFrag);
        }
        else if(currentFrag.equals(getString(R.string.series_det_frag)))
        {
            if(preFrag.equals(getString(R.string.home_frag)))
            {
                setFragment(new HomeFragment());
                currentFrag = getString(R.string.home_frag);
                setTitle(currentFrag);
            }
            else if(preFrag.equals(getString(R.string.series_frag)))
            {

                setFragment(new SeriesFragment());
                currentFrag  = getString(R.string.series_frag);
                setTitle(currentFrag);

            }

            else if(preFrag.equals(getString(R.string.movie_det_frag)))
            {
                setFragment(new SeriesFragment());
                currentFrag = getString(R.string.series_frag);
                setTitle(currentFrag);
                preFrag = getString(R.string.series_det_frag);
                VideoDetailFragment.player.stop();
            }
            else if(preFrag.equals(getString(R.string.search_frag)))
            {
                setFragment(new SearchFragment());
                currentFrag=getString(R.string.search_frag);
                setTitle(currentFrag);
            }
        }

        else if(currentFrag.equals(getString(R.string.movie_det_frag)))
        {
            if(preFrag.equals(getString(R.string.home_frag)))
            {
                setFragment(new HomeFragment());
                currentFrag=getString(R.string.home_frag);
                setTitle(currentFrag);
            }
            else if(preFrag.equals(getString(R.string.movie_frag)))
            {
                setFragment(new MovieFragment());
                currentFrag = getString(R.string.movie_frag);
                setTitle(currentFrag);
            }
            else if(preFrag.equals(getString(R.string.series_det_frag)))
            {
                SeriesDetailFragment detfrag = new SeriesDetailFragment();
                setFragment(detfrag);
                currentFrag = getString(R.string.series_det_frag);
                preFrag = getString(R.string.movie_det_frag);
                setTitle(SeriesDetailFragment.model.seriesName);

            }


            else if(preFrag.equals(getString(R.string.search_frag)))
            {
                setFragment(new SearchFragment());
                currentFrag = getString(R.string.search_frag);
                setTitle(currentFrag);
                preFrag = getString(R.string.movie_det_frag);
            }
            else
            {
                setFragment(new SeriesFragment());
                currentFrag = getString(R.string.series_frag);
                setTitle(currentFrag);
            }
            VideoDetailFragment.player.stop();
           setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
           MainActivity.toolbar.setVisibility(View.VISIBLE);
        }
        else
        {
            super.onBackPressed();;
        }


        if(VideoDetailFragment.player!=null)
        {
            VideoDetailFragment.player.stop();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if(VideoDetailFragment.player!=null)
        {
            VideoDetailFragment.player.setPlayWhenReady(false);
        }



    }

    @Override
    protected void onStop() {
        super.onStop();
        if(VideoDetailFragment.player!=null)
        {
            VideoDetailFragment.player.setPlayWhenReady(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(VideoDetailFragment.player!=null)
        {
            VideoDetailFragment.player.setPlayWhenReady(true);
        }
    }
}

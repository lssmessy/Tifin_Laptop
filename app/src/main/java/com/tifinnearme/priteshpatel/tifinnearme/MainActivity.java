package com.tifinnearme.priteshpatel.tifinnearme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tifinnearme.priteshpatel.tifinnearme.fragments.NavigationFragment;


public class MainActivity extends ActionBarActivity {
    public static Toolbar toolbar;
    boolean doubleBackToExitPressedOnce;
    private HomeFragment homeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_appbar);
        NavigationFragment navFrag=(NavigationFragment)getSupportFragmentManager().findFragmentById(R.id.drawer_fragment);
        DrawerLayout drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);//Get the object of drawer layout
        toolbar=(Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        navFrag.setUp(R.id.drawer_fragment,drawerLayout,toolbar);
        //startActivity(new Intent(this,Main_Map.class));

        //Create object for drawer layout



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id==R.id.navigate)
        {
            //startActivity(new Intent(this,Main_Map.class));
            Main_Map mapview=new Main_Map();
            mapview.checkLocation();
        }

        return super.onOptionsItemSelected(item);
    }
    public static class HomeFragment extends Fragment {
        private static final String SECTION_NUMBER="section number";

        public static HomeFragment newInstance(int section_number){
            HomeFragment homeFragment=new HomeFragment();

            Bundle args=new Bundle();
            args.putInt(SECTION_NUMBER,section_number);
            homeFragment.setArguments(args);
            return homeFragment;
        }
        public HomeFragment(){

        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View rootview=inflater.inflate(R.layout.mapview,container,false);
            return rootview;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach((MainActivity)activity);
           //((MainActivity)activity).onSectionAttched(getArguments().getInt(SECTION_NUMBER));
        }
    }
    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}

package com.google.sites.lostandfoundapp.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.sites.lostandfoundapp.R;
import com.google.sites.lostandfoundapp.fragments.CameraFragment;
import com.google.sites.lostandfoundapp.fragments.HomeFragment;
import com.google.sites.lostandfoundapp.fragments.LocateFragment;
import com.google.sites.lostandfoundapp.fragments.SettingsFragment;

public class Main extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment = HomeFragment.getInstance();
                    break;
                case R.id.navigation_locate:
                    selectedFragment = LocateFragment.getInstance();
                    break;
                case R.id.navigation_camera:
                    selectedFragment = CameraFragment.getInstance();
                    break;
                case R.id.navigation_settings:
                    selectedFragment = SettingsFragment.getInstance();
                    break;
                default:
                    return false;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment, null);
            transaction.commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, HomeFragment.getInstance());
        transaction.commit();
    }

}

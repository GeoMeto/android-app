package com.tu.challengeyourself;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.tu.challengeyourself.fragments.ChallengesFragment;
import com.tu.challengeyourself.fragments.SettingsFragment;
import com.tu.challengeyourself.fragments.SharingGroupFragment;
import com.tu.challengeyourself.utils.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!isHostReachable()) {
            showOfflineAlert();
        }
        setNavigation();
    }

    private void setNavigation() {
        TabLayout tabLayout = findViewById(R.id.mainTabLayout);
        ViewPager viewPager = findViewById(R.id.mainPager);

        ChallengesFragment challengesFragment = new ChallengesFragment();
        SharingGroupFragment sharingGroupFragment = new SharingGroupFragment();
        SettingsFragment settingsFragment = new SettingsFragment();

        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(challengesFragment, "CHALLENGES");
        viewPagerAdapter.addFragment(sharingGroupFragment, "SHARING GROUPS");
        viewPagerAdapter.addFragment(settingsFragment, "SETTINGS");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_challenge_navigation);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_groups);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_settings);
    }

    private boolean isHostReachable() {
        return false;
    }

    private void showOfflineAlert() {
        LayoutInflater inflater = getLayoutInflater();
        View offlineAlertView = inflater.inflate(R.layout.offline_alert_box, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(offlineAlertView);
        AlertDialog offlineAlert = builder.create();
        offlineAlert.show();
        Button dismissBtn = offlineAlert.findViewById(R.id.dismissOfflineAlertBtn);
        dismissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offlineAlert.dismiss();
            }
        });
    }
}
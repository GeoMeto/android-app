package com.tu.challengeyourself;

import static com.tu.challengeyourself.constants.Keys.GET_USERNAME_URL;
import static com.tu.challengeyourself.constants.Keys.TOKEN;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.tabs.TabLayout;
import com.tu.challengeyourself.fragments.ChallengesFragment;
import com.tu.challengeyourself.fragments.SettingsFragment;
import com.tu.challengeyourself.fragments.SharingGroupFragment;
import com.tu.challengeyourself.requests.AuthorizedJsonRequest;
import com.tu.challengeyourself.requests.VolleyManager;
import com.tu.challengeyourself.utils.ViewPagerAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setHeader();
        setNavigation();
    }

    private void setHeader() {
        TextView headerView = findViewById(R.id.mainHeading);
        String token = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(TOKEN, "");

        VolleyManager.getInstance().addToRequestQueue(
                new AuthorizedJsonRequest(Request.Method.GET, GET_USERNAME_URL, token, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String username = response.getString("username");
                            headerView.setText(createHeading(username));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            headerView.setText(createHeading("user"));
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse.statusCode == 403) {
                            navigateToLogin();
                            showToast("You have logged out!");
                        } else {
                            showToast("There was a loading your username!");
                        }
                    }
                }).getRequest());
    }

    private void navigateToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private String createHeading(String username) {
        String headingTemplate = "%s, %s!";
        String[] greetings = {"Hello", "Hey"};
        return String.format(headingTemplate, greetings[new Random().nextInt(2)], username);
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

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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
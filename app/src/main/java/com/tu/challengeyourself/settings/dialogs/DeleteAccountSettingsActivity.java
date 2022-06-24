package com.tu.challengeyourself.settings.dialogs;

import static com.tu.challengeyourself.constants.Keys.DELETE_ACCOUNT_URL;
import static com.tu.challengeyourself.constants.Keys.TOKEN;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tu.challengeyourself.LoginActivity;
import com.tu.challengeyourself.MainActivity;
import com.tu.challengeyourself.R;
import com.tu.challengeyourself.requests.AuthorizedJsonRequest;
import com.tu.challengeyourself.requests.VolleyManager;

import org.json.JSONObject;

public class DeleteAccountSettingsActivity extends AppCompatActivity {
    private Button yesBtn, noBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);
        yesBtn = findViewById(R.id.yesDeleteAccountBtn);
        noBtn = findViewById(R.id.noDeleteAccountBtn);
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(TOKEN, "");

                VolleyManager.getInstance().addToRequestQueue(
                        new AuthorizedJsonRequest(Request.Method.POST, DELETE_ACCOUNT_URL, token, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                clearToken();
                                navigateToLogin();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error.networkResponse.statusCode == 403) {
                                    navigateToLogin();
                                }
                            }
                        }).getRequest());
            }
        });
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMain();
            }
        });
    }

    private void clearToken() {
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString(TOKEN, "").commit();
    }

    private void navigateToLogin() {
        Intent intent = new Intent(DeleteAccountSettingsActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void navigateToMain() {
        Intent mainIntent = new Intent(DeleteAccountSettingsActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
}
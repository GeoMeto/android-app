package com.tu.challengeyourself;

import static com.tu.challengeyourself.constants.Keys.TOKEN;
import static com.tu.challengeyourself.constants.Keys.VALIDATE_URL;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.GsonBuilder;
import com.tu.challengeyourself.models.AuthToken;
import com.tu.challengeyourself.requests.VolleyManager;

import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        AuthToken authToken = new AuthToken();
        authToken.setToken(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(TOKEN, ""));
        VolleyManager.getInstance(this);
        if (authToken.getToken().equals("")) {
            navigateToLogin();
            return;
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(new GsonBuilder().create().toJson(authToken));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        VolleyManager.getInstance().getRequestQueue().add(new JsonObjectRequest(Request.Method.POST, VALIDATE_URL, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        navigateToMain();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        navigateToLogin();
                    }
                }) {
        });
    }

    private void navigateToLogin() {
        new Handler().postDelayed(() -> {
            Intent loginIntent = new Intent(SplashActivity.this, LoginActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(loginIntent);
            finish();
        }, 1000);
    }

    private void navigateToMain() {
        new Handler().postDelayed(() -> {
            Intent loginIntent = new Intent(SplashActivity.this, MainActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(loginIntent);
            finish();
        }, 1000);
    }
}

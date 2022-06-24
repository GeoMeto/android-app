package com.tu.challengeyourself.settings.dialogs;

import static com.tu.challengeyourself.constants.Keys.CHANGE_USERNAME_URL;
import static com.tu.challengeyourself.constants.Keys.TOKEN;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tu.challengeyourself.MainActivity;
import com.tu.challengeyourself.R;
import com.tu.challengeyourself.models.Username;
import com.tu.challengeyourself.requests.AuthorizedJsonRequest;
import com.tu.challengeyourself.requests.VolleyManager;

import org.json.JSONObject;

public class ChangeUsernameSettingsActivity extends AppCompatActivity {

    private Button changeUsernameBtn;
    private EditText usernameEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_username_setting);
        changeUsernameBtn = findViewById(R.id.usernameSettingsButton);
        usernameEdit = findViewById(R.id.usernameSettingsEdit);
        changeUsernameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(TOKEN, "");
                Username user = new Username(usernameEdit.getText().toString());

                VolleyManager.getInstance().addToRequestQueue(
                        new AuthorizedJsonRequest(Request.Method.POST, CHANGE_USERNAME_URL, token, user, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                navigateToMain();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                showToast("There was a problem with updating your username!");
                            }
                        }).getRequest());
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void navigateToMain() {
        Intent mainIntent = new Intent(ChangeUsernameSettingsActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
}
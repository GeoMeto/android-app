package com.tu.challengeyourself.settings.dialogs;

import static com.tu.challengeyourself.constants.Keys.CHANGE_PASSWORD_URL;
import static com.tu.challengeyourself.constants.Keys.TOKEN;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tu.challengeyourself.MainActivity;
import com.tu.challengeyourself.R;
import com.tu.challengeyourself.models.ChangePassword;
import com.tu.challengeyourself.requests.AuthorizedJsonRequest;
import com.tu.challengeyourself.requests.VolleyManager;
import com.tu.challengeyourself.utils.EncryptionUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class ResetPasswordSettingsActivity extends AppCompatActivity {
    private EditText currentPassEdit, newPassEdit, repeatPassEdit;
    private String currentPass, newPass, repeatPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_setting);

        currentPassEdit = findViewById(R.id.oldPassEdit);
        newPassEdit = findViewById(R.id.newPassEdit);
        repeatPassEdit = findViewById(R.id.confirmPassEdit);

        Button changeButton = findViewById(R.id.confirmChangePassSettingBtn);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                initPasswords();
                if (validatePasswords()) {
                    String token = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(TOKEN, "");
                    EncryptionUtils encUtil = new EncryptionUtils();
                    VolleyManager.getInstance().addToRequestQueue(
                            new AuthorizedJsonRequest(Request.Method.POST, CHANGE_PASSWORD_URL, token, new ChangePassword(encUtil.encrypt(currentPass), encUtil.encrypt(newPass)),
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            saveToken(response);
                                            navigateToMain();
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    showToast("Please enter your current password!");
                                }
                            }).getRequest());

                } else {
                    showToast("New password don't match or too short!");
                }
            }
        });
    }

    private void saveToken(JSONObject response) {
        try {
            String token = response.getString("token");
            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString(TOKEN, token).commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void navigateToMain() {
        Intent mainIntent = new Intent(ResetPasswordSettingsActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    private boolean validatePasswords() {
        return currentPass.length() > 5 && newPass.length() > 5 && repeatPass.length() > 5 && newPass.equals(repeatPass);
    }

    private void initPasswords() {
        currentPass = currentPassEdit.getText().toString();
        newPass = newPassEdit.getText().toString();
        repeatPass = repeatPassEdit.getText().toString();
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
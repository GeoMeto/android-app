package com.tu.challengeyourself;

import static com.tu.challengeyourself.constants.Keys.LOGIN_URL;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.GsonBuilder;
import com.tu.challengeyourself.models.Login;
import com.tu.challengeyourself.requests.VolleyManager;
import com.tu.challengeyourself.utils.EncryptionUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setLoginListener();
        setCreateAccListener();
        setResetPassListener();
    }

    private void setLoginListener() {
        EditText emailEdit = findViewById(R.id.emailTxt);
        EditText passEdit = findViewById(R.id.passTxt);
        Button loginBtn = findViewById(R.id.btnLogin);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                try {
                    String email = emailEdit.getText().toString();
                    String password = passEdit.getText().toString();
                    login(new Login(email, password));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void login(Login login) {
        if (login.getEmail().equals("") || login.getPass().equals("")) {
            showToast("Empty credentials!");
            return;
        }
        final boolean[] isLoginSuccessful = {false};
        login.setPass(new EncryptionUtils().encrypt(login.getPass()));
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(new GsonBuilder().create().toJson(login));
        } catch (JSONException e) {
            e.printStackTrace();
            showToast("There was an error on login!");
            return;
        }

        VolleyManager.getInstance().getRequestQueue().add(new JsonObjectRequest(Request.Method.POST, LOGIN_URL, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String token = response.getString("token");
                            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString(TOKEN, token).commit();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(mainIntent);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showToast("Invalid credentials!");
                    }
                }));
    }

    private void showToast(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void setCreateAccListener() {
        Button createAccBtn = findViewById(R.id.btnCreateAccount);
        createAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createAccIntent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(createAccIntent);
            }
        });
    }

    private void setResetPassListener() {
        Button resetPassBtn = findViewById(R.id.btnResetPass);
        resetPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}

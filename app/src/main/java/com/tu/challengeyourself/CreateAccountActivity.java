package com.tu.challengeyourself;

import static com.tu.challengeyourself.constants.Keys.CREATE_USER_URL;
import static com.tu.challengeyourself.constants.Keys.TOKEN;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.GsonBuilder;
import com.tu.challengeyourself.models.User;
import com.tu.challengeyourself.requests.VolleyManager;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateAccountActivity extends AppCompatActivity {
    private boolean isTermsAndConditionsChecked = false;
    private EditText passwordEdit, emailEdit, usernameEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        emailEdit = findViewById(R.id.editCreateEmail);
        passwordEdit = findViewById(R.id.editCreatePassword);
        usernameEdit = findViewById(R.id.editCreateUserName);

        setTermsListener();
        setCreateAccListener();
    }

    private void setCreateAccListener() {
        Button createAccBtn = findViewById(R.id.btnCACreateAccount);
        createAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isTermsAndConditionsChecked) {
                    showToast("You have to check the terms and conditions!");
                    return;
                } if(!isUsernameValid()) {
                    showToast("All fields are mandatory!");
                    return;
                }
                createAccount();
            }
        });
    }

    private boolean isUsernameValid() {
        String username = usernameEdit.getText().toString();
        String email = emailEdit.getText().toString();
        String password = passwordEdit.getText().toString();

        return !username.isEmpty() && !email.isEmpty() && !password.isEmpty();
    }

    private void createAccount() {
        String email = emailEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        String username = usernameEdit.getText().toString();
        User newUser = new User(email, password, username);
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(new GsonBuilder().create().toJson(newUser));
        } catch (JSONException e) {
            e.printStackTrace();
            showToast("There was an error on serialization!");
            return;
        }

        VolleyManager.getInstance().getRequestQueue().add(new JsonObjectRequest(Request.Method.POST, CREATE_USER_URL, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        saveToken(response);
                        startMainActivity();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showToast("Please fill email in correct format and at least 6 characters password");
                    }
                }));
    }

    private void startMainActivity() {
        Intent mainIntent = new Intent(CreateAccountActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    private void saveToken(JSONObject response) {
        try {
            String token = response.getString("token");
            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString(TOKEN, token).commit();
        } catch (JSONException e) {
            showToast("There was an error on deserialization!");
            e.printStackTrace();
        }
    }

    private void showToast(String message) {
        Toast.makeText(CreateAccountActivity.this, message, Toast.LENGTH_LONG).show();
    }

    private void setTermsListener() {
        CheckBox termsCheckBox = findViewById(R.id.termsCheckBox);
        termsCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isTermsAndConditionsChecked) {
                    LayoutInflater inflater = getLayoutInflater();
                    View termsLayout = inflater.inflate(R.layout.terms_text_box, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateAccountActivity.this);
                    builder.setView(termsLayout);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    Button dismissAlertBtn = termsLayout.findViewById(R.id.acceptTermsAlertBtn);
                    dismissAlertBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                }
                isTermsAndConditionsChecked = termsCheckBox.isChecked();
            }
        });
    }
}
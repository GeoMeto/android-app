package com.tu.challengeyourself;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setLoginListener();
        setCreateAccListener();
        setResetPassListener();
    }

    private void setLoginListener() {
        Button loginBtn = findViewById(R.id.btnLogin);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (successfulLogin()) {
                    Intent mainIntent = new Intent(LoginActivity.this, ChallengesActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(mainIntent);
                } else {
                    showToastWrongCredentials();
                }
            }
        });
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
                Intent createAccIntent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                startActivity(createAccIntent);
            }
        });
    }

    private void showToastWrongCredentials() {
        Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_LONG).show();
    }

    private boolean successfulLogin() {
        return false;
    }
}

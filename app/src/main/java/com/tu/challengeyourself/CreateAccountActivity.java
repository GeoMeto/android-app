package com.tu.challengeyourself;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        if (isEmailAlreadyUsed()) {
            showToast("This email is already used!");
        } else if (isEmailCorrupt()) {
            showToast("Invalid email! Please use the pattern john@example.com.");
        }
        if (isPasswordWeak()) {
            showToast("Password is weak! Please use a stronger password.");
        }
    }

    private boolean isPasswordWeak() {
        return false;
    }

    private boolean isEmailAlreadyUsed() {
        return false;
    }

    private void showToast(String message) {
        Toast.makeText(CreateAccountActivity.this, message, Toast.LENGTH_LONG).show();
    }


    private boolean isEmailCorrupt() {
        return false;
    }
}
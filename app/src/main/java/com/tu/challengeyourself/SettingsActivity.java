package com.tu.challengeyourself;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tu.challengeyourself.settings.dialogs.ChangeUsernameSettingsActivity;
import com.tu.challengeyourself.settings.dialogs.DeleteAccountSettingsActivity;
import com.tu.challengeyourself.settings.dialogs.LogOutSettingsActivity;
import com.tu.challengeyourself.settings.dialogs.NotificationSettingsActivity;
import com.tu.challengeyourself.settings.dialogs.ResetPasswordSettingsActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setResetPassListener();
        setChangeUsernameListener();
        setNotificationsListener();
        setDeleteAccListener();
        setLogOutListener();
    }

    private void setResetPassListener() {
        View resetPassContainer = findViewById(R.id.resetPassSettingsBtn);
        resetPassContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, ResetPasswordSettingsActivity.class));
            }
        });
    }

    private void setChangeUsernameListener() {
        View resetPassContainer = findViewById(R.id.resetPassSettingsBtn);
        resetPassContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, ChangeUsernameSettingsActivity.class));
            }
        });
    }

    private void setNotificationsListener() {
        View resetPassContainer = findViewById(R.id.resetPassSettingsBtn);
        resetPassContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, NotificationSettingsActivity.class));
            }
        });
    }

    private void setDeleteAccListener() {
        View resetPassContainer = findViewById(R.id.resetPassSettingsBtn);
        resetPassContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, DeleteAccountSettingsActivity.class));
            }
        });
    }

    private void setLogOutListener() {
        View resetPassContainer = findViewById(R.id.resetPassSettingsBtn);
        resetPassContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, LogOutSettingsActivity.class));
            }
        });
    }
}
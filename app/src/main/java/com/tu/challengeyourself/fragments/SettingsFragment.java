package com.tu.challengeyourself.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tu.challengeyourself.R;
import com.tu.challengeyourself.settings.dialogs.ChangeUsernameSettingsActivity;
import com.tu.challengeyourself.settings.dialogs.DeleteAccountSettingsActivity;
import com.tu.challengeyourself.settings.dialogs.LogOutSettingsActivity;
import com.tu.challengeyourself.settings.dialogs.NotificationSettingsActivity;
import com.tu.challengeyourself.settings.dialogs.ResetPasswordSettingsActivity;

public class SettingsFragment extends Fragment {

    public SettingsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setResetPassListener(view);
        setChangeUsernameListener(view);
        setNotificationsListener(view);
        setDeleteAccListener(view);
        setLogOutListener(view);
    }

    private void setResetPassListener(View view) {
        View resetPassContainer = view.findViewById(R.id.resetPassSettingsBtn);
        resetPassContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ResetPasswordSettingsActivity.class));
            }
        });
    }

    private void setChangeUsernameListener(View view) {
        View resetPassContainer = view.findViewById(R.id.changeUsernameSettingsBtn);
        resetPassContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ChangeUsernameSettingsActivity.class));
            }
        });
    }

    private void setNotificationsListener(View view) {
        View resetPassContainer = view.findViewById(R.id.notificationSettingsBtn);
        resetPassContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), NotificationSettingsActivity.class));
            }
        });
    }

    private void setDeleteAccListener(View view) {
        View resetPassContainer = view.findViewById(R.id.deleteAccSettingsBtn);
        resetPassContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), DeleteAccountSettingsActivity.class));
            }
        });
    }

    private void setLogOutListener(View view) {
        View resetPassContainer = view.findViewById(R.id.logOutSettingsBtn);
        resetPassContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LogOutSettingsActivity.class));
            }
        });
    }
}
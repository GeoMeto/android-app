package com.tu.challengeyourself;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class ChallengesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges);
        if (!isHostReachable()) {
            showOfflineAlert();
        }
    }

    private boolean isHostReachable() {
        return false;
    }

    private void showOfflineAlert() {
        LayoutInflater inflater = getLayoutInflater();
        View offlineAlert = inflater.inflate(R.layout.offline_alert_box, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(ChallengesActivity.this);
        builder.setView(offlineAlert);
        builder.show();

//        Button dismissAlertBtn = offlineAlert.findViewById(R.id.dismissOfflineAlertBtn);
//        dismissAlertBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }
}
package com.tu.challengeyourself;

import static com.tu.challengeyourself.constants.Keys.INTENT_DATA;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private Button createAccBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        createAccBtn = findViewById(R.id.btnCreateAccount);
        createAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createAccIntent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(createAccIntent);
            }
        });


    }
}

package com.tu.challengeyourself;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tu.challengeyourself.utils.MailingUtils;
import com.tu.challengeyourself.utils.MethodHelper;

public class ResetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        setResetPassListener();
    }

    private void setResetPassListener() {
        Button resetPassBtn = findViewById(R.id.btnResetPassSend);
        resetPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MethodHelper.isEmailCorrupt()) {
                    showToast("This email is not in correct format!");
                } else if (MethodHelper.isEmailAlreadyUsed()) {
                    MailingUtils.sendResetPassMail("user@example.com");
                    showToast("An email with instructions how to reset password was sent to your email!");
                }
                else {
                    showToast("No such user found!");
                }
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(ResetPasswordActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
package com.tu.challengeyourself;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.tu.challengeyourself.utils.MethodHelper;

public class CreateAccountActivity extends AppCompatActivity {

    boolean isTermsAndConditionsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        setTermsListener();
        setCreateAccListener();
    }

    private void setCreateAccListener() {
        Button createAccBtn = findViewById(R.id.btnCACreateAccount);
        createAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MethodHelper.isEmailAlreadyUsed()) {
                    showToast("This email is already used!");
                    return;
                }
                if (MethodHelper.isEmailCorrupt()) {
                    showToast("Invalid email! Please use the pattern john@example.com.");
                    return;
                }
                if (MethodHelper.isPasswordWeak()) {
                    showToast("Password is weak! Please use a stronger password.");
                    return;
                }
                if (!isTermsAndConditionsChecked) {
                    showToast("You have to check the terms and conditions!");
                    return;
                }
                loginWithNewAccount();
            }
        });
    }

    private void loginWithNewAccount() {
        Intent loginIntent = new Intent(CreateAccountActivity.this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(loginIntent);
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
                    getApplicationContext().getResources().getResourceName();
                    termsLayout.setId();
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
                    isTermsAndConditionsChecked = termsCheckBox.isChecked();
                }
            }
        });
    }
}
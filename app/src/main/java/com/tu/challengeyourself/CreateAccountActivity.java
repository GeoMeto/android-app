package com.tu.challengeyourself;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.tu.challengeyourself.utils.MethodHelper;

public class CreateAccountActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getCanonicalName();
    boolean isTermsAndConditionsChecked = false;
    private FirebaseAuth mAuth;
    private EditText passwordEdit, emailEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        mAuth = FirebaseAuth.getInstance();

        emailEdit = findViewById(R.id.editCreateEmail);
        passwordEdit = findViewById(R.id.editCreatePassword);



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
                }
                createAccount();
            }
        });
    }

    private void createAccount() {
        String email = emailEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        try {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                loginWithNewAccount(user);
                            } else {
                                try {
                                    throw task.getException();
                                } catch (FirebaseAuthWeakPasswordException e) {
                                    passwordEdit.setError("This password is weak. Please use a password with at least 6 characters.");
                                    passwordEdit.requestFocus();
                                    showToast("This password is weak. Please use a password with at least 6 characters.");
                                } catch (FirebaseAuthInvalidCredentialsException e) {
                                    emailEdit.setError("Email is not correctly formatted.");
                                    emailEdit.requestFocus();
                                    showToast("Email is not correctly formatted.");
                                } catch (FirebaseAuthUserCollisionException e) {
                                    emailEdit.setError("This user already exists. Please use the login from.");
                                    emailEdit.requestFocus();
                                    showToast("This user already exists. Please use the login from.");
                                } catch (Exception e) {
                                    showToast("Authentication failed." + e.getMessage());
                                }
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            }
                        }
                    });
        } catch (IllegalArgumentException e) {
            Toast.makeText(CreateAccountActivity.this, "Email or password is empty!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void loginWithNewAccount(FirebaseUser user) {
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
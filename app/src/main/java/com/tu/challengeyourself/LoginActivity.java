package com.tu.challengeyourself;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private final String TAG = this.getClass().getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        setLoginListener();
        setCreateAccListener();
        setResetPassListener();
    }

    private void setLoginListener() {
        EditText emailEdit = findViewById(R.id.emailTxt);
        EditText passEdit = findViewById(R.id.passTxt);
        String email = emailEdit.getText().toString();
        String password = passEdit.getText().toString();

        Button loginBtn = findViewById(R.id.btnLogin);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoginSuccessful(email, password)) {
                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(mainIntent);
                    FirebaseUser user = mAuth.getCurrentUser();
                    user.getIdToken(false);
                }
            }
        });
    }

    private boolean isLoginSuccessful(String email, String password) {
        final boolean[] isLoginSuccessful = {false};
        try {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            isLoginSuccessful[0] = task.isSuccessful();
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                            } else {
                                try {
                                    throw task.getException();
                                } catch (FirebaseAuthInvalidUserException e) {
                                    showToast("User not found. Please create a new account.");
                                } catch (FirebaseAuthInvalidCredentialsException e) {
                                    showToast("Invalid password. Please try again or reset password.");
                                } catch (Exception e) {
                                    showToast("Authentication failed. Error message:" + task.getException().getMessage());
                                }
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                            }
                        }
                    });
        } catch (IllegalArgumentException e) {
            Toast.makeText(getApplicationContext(), "Email or password is empty!",
                    Toast.LENGTH_SHORT).show();
        }
        return isLoginSuccessful[0];
    }

    private void showToast(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
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
}

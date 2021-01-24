package com.app.a14days;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class LoginActivity extends AppCompatActivity {
    private Button mlogin;
    private TextView mForgotPassword;
    private EditText mName, mEmail, mPassword;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mlogin = findViewById(R.id.Login);
        mEmail= findViewById(R.id.Email);
        mPassword = findViewById(R.id.Password);
        mForgotPassword = findViewById(R.id.forgotPassword);

        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString();

                if (email.isEmpty()) {
                    mEmail.setError("Email required");
                    mEmail.requestFocus();
                }
                else if (password.isEmpty()) {
                    mPassword.setError("Password required");
                    mPassword.requestFocus();
                }
                else{
                    mlogin.setText("Logging in...");
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                FirebaseAuthException e = (FirebaseAuthException )task.getException();
                                Toast.makeText(LoginActivity.this, "Failed login: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                mlogin.setText("Login");

                            } else {
                                Intent intent = new Intent(getApplication(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                                return;
                            }
                        }
                    });
                }

            }
        });
        
        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {Intent intent = new Intent(getApplication(), ForgotPasswordActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return;
            }
        });
        
    }

    @Override
    protected void onStart() {
        super.onStart();
        //mAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
       // mAuth.removeAuthStateListener(firebaseAuthStateListener);
    }


}
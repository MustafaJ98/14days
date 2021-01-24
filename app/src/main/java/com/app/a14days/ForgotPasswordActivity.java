package com.app.a14days;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText mResetEmail;
    private Button mResetButton;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mResetButton = findViewById(R.id.btn_reset_password);
        mResetEmail = findViewById(R.id.edt_reset_email);
        mAuth = FirebaseAuth.getInstance();

        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mResetEmail.getText().toString().trim();
                if (email.isEmpty()) {
                    mResetEmail.setError("Email required");
                    mResetEmail.requestFocus();
                }
                else {
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(
                            new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        Toast.makeText(getApplication(), "Check email to reset your password!", Toast.LENGTH_SHORT).show();
                                    }else {
                                        FirebaseAuthException e = (FirebaseAuthException )task.getException();
                                        Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                    );
                }
            }
        });
    }
}
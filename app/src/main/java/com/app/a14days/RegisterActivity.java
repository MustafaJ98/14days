package com.app.a14days;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

public class RegisterActivity extends AppCompatActivity {

    private Button mRegister;
    private EditText mName, mEmail, mPassword;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                if (user != null) {
//                    Intent intent = new Intent(getApplication(), MainActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
//                    finish();
//                    return;
//                }
//            }
//        };

        mAuth = FirebaseAuth.getInstance();
        mName = findViewById(R.id.name);
        mRegister = findViewById(R.id.Signup);
        mEmail= findViewById(R.id.Email);
        mPassword = findViewById(R.id.Password);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();
                final String name = mName.getText().toString();

                if (email.isEmpty()) {
                    mEmail.setError("Email required");
                    mEmail.requestFocus();
                }
                if (password.isEmpty()) {
                    mPassword.setError("Password required");
                    mPassword.requestFocus();
                }
                if (name.isEmpty()) {
                    mName.setError("Username required");
                    mName.requestFocus();
                }


                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this , new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            FirebaseAuthException e = (FirebaseAuthException )task.getException();
                            Toast.makeText(RegisterActivity.this, "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            String userID = mAuth.getCurrentUser().getUid();
                            DatabaseReference currentUserDB = FirebaseDatabase.getInstance().getReference().child("users").child(userID);

                            Map userInfo = new HashMap<>();
                            userInfo.put("email", email);
                            userInfo.put("name", name);
                            //userInfo.put("ProfileImage",  "default");

                            currentUserDB.updateChildren(userInfo);

                            Intent intent = new Intent(getApplication(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                            return;
                        }
                    }
                });
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
        //mAuth.removeAuthStateListener(firebaseAuthStateListener);
    }

}
package com.app.a14days;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddContactManuallyActivity extends AppCompatActivity {
    String userID;
    DatabaseReference currentUserDB;
    DatabaseReference contact;
    DatabaseReference users;
    FirebaseAuth mAuth;
    EditText mName;
    Button addContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact_manually);

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        users = FirebaseDatabase.getInstance().getReference().child("users");
        currentUserDB = users.child(userID);
        contact = currentUserDB.child("contact");

       mName = findViewById(R.id.ManualContactName);
       addContact = findViewById(R.id.AddManualContact);

       addContact.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               final String name = mName.getText().toString();
               if (name.isEmpty()) {
                   mName.setError("Name required");
                   mName.requestFocus();
               }
               else{
                   DatabaseReference hostAddContactHere = contact.child(new Date().toString());
                   Map contactInfo = new HashMap<>();
                   contactInfo.put("contactName", name);
                   contactInfo.put("contactDate",  new Date().toString());
                   contactInfo.put("covid_positive", false);
                   hostAddContactHere.updateChildren(contactInfo);

                   startNewActivity();
               }

           }
       });
    }

    private void startNewActivity() {
        Intent intent = new Intent(getApplication(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        return;
    }
}
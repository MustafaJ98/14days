package com.app.a14days;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CovidCheckAndBroadcast {

    public static void checkPositive(){
        String userID;
        DatabaseReference currentUserDB;
        DatabaseReference contact;
        DatabaseReference users;
        FirebaseAuth mAuth;

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        users = FirebaseDatabase.getInstance().getReference().child("users");
        currentUserDB = users.child(userID);
        contact = currentUserDB.child("contact");

        contact.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for ( DataSnapshot KeyNode: snapshot.getChildren() ){
                    String key = KeyNode.getKey();
                    Contact singleContact = KeyNode.getValue(Contact.class);
                    if( singleContact.isCovid_positive()) {
                        alertCurrentUser(singleContact.getContactName());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Contact", error.toString());

            }
        });
    }

    private static void alertCurrentUser(String contactName) {
        Log.i("broadcast", contactName + "tested postive for COVID");
    }

    public static void alertContacts(){
        String userID;
        DatabaseReference currentUserDB;
        DatabaseReference contact;
        DatabaseReference users;
        FirebaseAuth mAuth;

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        users = FirebaseDatabase.getInstance().getReference().child("users");
        currentUserDB = users.child(userID);
        contact = currentUserDB.child("contact");

        contact.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for ( DataSnapshot KeyNode: snapshot.getChildren() ){
                    String key = KeyNode.getKey();
                    Contact singleContact = KeyNode.getValue(Contact.class);
                    DatabaseReference contactSetHostPostive = users.child(key).child("contact").child(userID).child("covid_positive");
                    contactSetHostPostive.setValue(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Contact", error.toString());

            }
        });

    }
}

package com.app.a14days;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class Timer_14days {

    public static String calculateRemainingTime(String contactDate){
        long currentTime = new Date().getTime();
        long contactTime = new Date(contactDate).getTime();
        long diff = currentTime  - contactTime ;
        long Days = (long)14.0 - (diff / (24 * 60 * 60 * 1000));
        String remainingDays = "0";
        if (Days > 0) {
            remainingDays = String.format("%02d", Days);
        }
        Log.i("time", remainingDays);
        return remainingDays;
    }

    public static void deleteOverdueContact(){
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

        contact.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for ( DataSnapshot KeyNode: snapshot.getChildren() ){
                    String key = KeyNode.getKey();
                    Contact singleContact = KeyNode.getValue(Contact.class);
                    String contactDate = singleContact.getContactDate();
                    long currentTime = new Date().getTime();
                    long contactTime = new Date(contactDate).getTime();
                    long diff = currentTime  - contactTime ;
                    long Days = (long)14.0 - (diff / (24 * 60 * 60 * 1000));
                    if (Days <= (long)0) {
                        contact.child(key).removeValue();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}


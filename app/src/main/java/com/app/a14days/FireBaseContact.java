package com.app.a14days;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FireBaseContact {

    private String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    String userID;
    DatabaseReference currentUserDB;
    DatabaseReference contact;
    DatabaseReference users;
    FirebaseAuth mAuth;
    ArrayList<Contact> contactList = new ArrayList<Contact>();

    public FireBaseContact() {
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        users = FirebaseDatabase.getInstance().getReference().child("users");
        currentUserDB = users.child(userID);
        contact = currentUserDB.child("contact");
    }

    public interface DataStatus {
        void DataIsLoaded(List<Contact> contactList, List<String> keys);
        void DataIsInserted();
        void DataIsDeleted();
        void DataIsUpdated();
    }

    public void readContact(final FireBaseContact.DataStatus dataStatus) {
        contact.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                contactList.clear();
                List<String> keys = new ArrayList();

                for ( DataSnapshot KeyNode: snapshot.getChildren() ){
                    keys.add(KeyNode.getKey());
                    contactList.add( KeyNode.getValue(Contact.class) );
                }
                dataStatus.DataIsLoaded(contactList, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Contact", error.toString());
            }
        });
    }

    public void readAndAddSingleContact(String query) {
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for ( DataSnapshot KeyNode: snapshot.getChildren() ){
                    if( KeyNode.getKey().equals(query) ){

                        String singleKey = KeyNode.getKey();
                        HashMap singleSearch = (HashMap) KeyNode.getValue();

                        DatabaseReference hostAddContactHere = contact.child(singleKey);
                        Map contactInfo = new HashMap<>();
                        contactInfo.put("contactName", singleSearch.get("name"));
                        contactInfo.put("contactDate",  new Date().toString());
                        contactInfo.put("covid_positive", false);
                        hostAddContactHere.updateChildren(contactInfo);


                        currentUserDB.child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                DatabaseReference contactAddHostHere = users.child(singleKey).child("contact").child(userID);
                                Map userInfo = new HashMap<>();
                                userInfo.put("contactName", snapshot.getValue(String.class));
                                userInfo.put("contactDate",  new Date().toString());
                                userInfo.put("covid_positive", false);
                                contactAddHostHere.updateChildren(userInfo);

                                Log.i("User Name" , snapshot.getValue(String.class));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Contact", error.toString());
            }
        });

    }

}

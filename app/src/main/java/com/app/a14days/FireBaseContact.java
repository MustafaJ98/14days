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
import java.util.List;

public class FireBaseContact {
    DatabaseReference currentUserDB;
    DatabaseReference contact;
    FirebaseAuth mAuth;
    ArrayList<Contact> contactList = new ArrayList<Contact>();

    public FireBaseContact() {
        mAuth = FirebaseAuth.getInstance();
        String userID = mAuth.getCurrentUser().getUid();
        currentUserDB = FirebaseDatabase.getInstance().getReference().child("users").child(userID);
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

}

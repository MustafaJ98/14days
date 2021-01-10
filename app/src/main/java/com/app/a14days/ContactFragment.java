package com.app.a14days;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ContactFragment extends Fragment {

    private TextView contactName;
    DatabaseReference currentUserDB;
    DatabaseReference contact;
    FirebaseAuth mAuth;
    ArrayList<String> contactList = new ArrayList<String>();;

    private RecyclerView mRecyclerView;

    public static ContactFragment newInstance() {
        ContactFragment fragment = new ContactFragment();
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.RecyclerViewContact);
        new FireBaseContact().readContact(new FireBaseContact.DataStatus() {
            @Override
            public void DataIsLoaded(List<Contact> contactList, List<String> keys) {
                new RecyclerView_Config().setConfig(mRecyclerView, getActivity(), contactList, keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsDeleted() {

            }

            @Override
            public void DataIsUpdated() {

            }
        });
        //Get contact from FireBase
//        mAuth = FirebaseAuth.getInstance();
//        String userID = mAuth.getCurrentUser().getUid();
//        currentUserDB = FirebaseDatabase.getInstance().getReference().child("users").child(userID);
//        contact = currentUserDB.child("contact");

        //readContact();

//        contactName = view.findViewById(R.id.cardName);
//        AddData();


        return view;
    }

    private void AddData() {
        contactName.setText(contactList.toString());
    }
}

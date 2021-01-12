package com.app.a14days;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CameraFragment extends Fragment {

    private CodeScanner mCodeScanner;
    private Button mTextView;
    FirebaseAuth mAuth;


    public static CameraFragment newInstance() {
        CameraFragment fragment = new CameraFragment();
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Activity activity = getActivity();
        View root = inflater.inflate(R.layout.fragment_camera, container, false);
        CodeScannerView scannerView = root.findViewById(R.id.scanner_view);

        mAuth = FirebaseAuth.getInstance();
        mTextView = root.findViewById(R.id.qrResult);
        mCodeScanner = new CodeScanner(activity, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, result.getText(), Toast.LENGTH_SHORT).show();
                        mTextView.setVisibility(View.VISIBLE);

                        mTextView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                addNewContact(result.getText());
                                mTextView.setVisibility(View.INVISIBLE);
                                mCodeScanner.startPreview();
                                Toast.makeText(activity, "Added to contact", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextView.setVisibility(View.INVISIBLE);
                mCodeScanner.startPreview();
            }
        });
        return root;
    }

    private void addNewContact(String text) {
        FireBaseContact fbContact = new FireBaseContact();
        fbContact.readAndAddSingleContact(text);
//        String userID = mAuth.getCurrentUser().getUid();
//        DatabaseReference currentUserDB = FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("contact").child(singleKey);
//
//        Toast.makeText(getActivity(), (singleKey + " Hi"), Toast.LENGTH_SHORT).show();
//        Log.i("test", (singleKey + "HI"));
//        Map userInfo = new HashMap<>();
//        userInfo.put("contactName", contact);
//        userInfo.put("contactDate", contact);
//
//        currentUserDB.updateChildren(userInfo);
//
//        Intent intent = new Intent(getActivity(), MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//        return;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }


}
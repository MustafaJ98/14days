package com.app.a14days;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Map;

public class CameraFragment extends Fragment {

    private CodeScanner mCodeScanner;
    private TextView mTextView;
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
        mTextView = root.findViewById(R.id.qrResult);
        mCodeScanner = new CodeScanner(activity, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, result.getText(), Toast.LENGTH_SHORT).show();
                        mTextView.setText(result.getText());

//                        mAuth = FirebaseAuth.getInstance();
//                        String userID = mAuth.getCurrentUser().getUid();
//                        DatabaseReference currentUserDBcontact = FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("contact");
//                        DatabaseReference detectedUserDB = FirebaseDatabase.getInstance().getReference().child("users").child(result.getText());
//
//                        Map userInfo = new HashMap<>();
//                        userInfo.put("email", email);
//                        userInfo.put("name", name);
//                        userInfo.put("ProfileImage",  "default");
//
//                        currentUserDBcontact.updateChildren(userInfo);
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
        return root;
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
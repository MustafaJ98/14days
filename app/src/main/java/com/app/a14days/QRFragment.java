package com.app.a14days;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.WriterException;

import java.io.File;
import java.io.FileOutputStream;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

import static android.content.Context.WINDOW_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class QRFragment extends Fragment {

    ImageView qr;
    private Button mSignout;

    public static QRFragment newInstance() {
        QRFragment fragment = new QRFragment();
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qr, container, false);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String userID = mAuth.getCurrentUser().getUid();
        DatabaseReference currentUserDB = FirebaseDatabase.getInstance().getReference().child("users").child(userID);

        qr = view.findViewById(R.id.qrImage);
        mSignout = view.findViewById(R.id.SignOut);

        mSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), SplashScreenActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return;
            }
        });

        // If QR is not found in memory, get UserID from Firebase and Make a QR then store in Memory

        Bitmap bitmap = MakeQR(userID);

        qr.setImageBitmap(bitmap);         // Setting Bitmap to ImageView

        //SaveBitMapAsJPG(bitmap);

        return view;
    }


    /**
     * Designed to be generate a QR code from a parameter string.
     */
    private Bitmap MakeQR(String userID) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3 / 4;

        QRGEncoder qrgEncoder = new QRGEncoder(userID, null, QRGContents.Type.TEXT, smallerDimension);
        qrgEncoder.setColorBlack(Color.BLACK);
        qrgEncoder.setColorWhite(Color.WHITE);
        // Getting QR-Code as Bitmap
        Bitmap bitmap = qrgEncoder.getBitmap();
        Log.e("QR", bitmap.toString());
        return bitmap;
    }

    /**
     * Designed to be used to store a bitmap of QR as an jpg image.
     */
    private void SaveBitMapAsJPG(Bitmap bitmap) {
        String savePath = Environment.getExternalStorageDirectory().toString() + "/14Days/QR";
        File myDir = new File(savePath);
        myDir.mkdirs();
        String fname = "Image-QR.jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        Log.i("LOAD", savePath + fname);
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.app.a14days;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {

    FragmentPagerAdapter adapterViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO : FIX Permissions
        requestForCamera();

        // Swipe-able viewPager
        ViewPager viewPager = findViewById(R.id.ViewPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);
        viewPager.setCurrentItem(2);
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    //return fragment contact with timer
                    return ContactFragment.newInstance();
                case 1:
                    //return QR scanner
                    return CameraFragment.newInstance();
                    //break;
                case 2:
                    //return fragment QR with ID
                    return QRFragment.newInstance();
                    //break;

            }

            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    private void requestForCamera() {
        Dexter.withContext(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                return;
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(MainActivity.this , "Camera permission is required", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        });
    }
}
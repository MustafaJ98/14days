package com.app.a14days;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class NotificationActivity extends AppCompatActivity {

    private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        Intent intent = getIntent();
        String contactName = intent.getStringExtra("contactName");
        String contactDate = intent.getStringExtra("contactDate");

        mText = findViewById(R.id.covidContact);
        String message = "One of your contacts, " + contactName + " who you meet on:\n" + contactDate + "\nhas tested positive for COVID."
                + "\nPlease take necessary precautions.";
        mText.setText(message);
    }
}
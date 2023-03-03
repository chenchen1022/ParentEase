package edu.northeastern.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import edu.northeastern.atyourservice.R;

public class SentStickersActivity extends AppCompatActivity {
    private TextView tv_myUserName;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_stickers);
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            userName = extras.getString("userName");
        }

        //set up UI elements
        tv_myUserName = findViewById(R.id.myUsername);
        tv_myUserName.setText("My username: " + userName);

    }
}
package edu.northeastern.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Member;
import java.util.ArrayList;

import edu.northeastern.MainActivity;
import edu.northeastern.atyourservice.R;

/**
 * The class for Send Stickers Activity.
 *
 * @author ShiChang Ye
 * @author Chen Chen
 * @author Lin Han
 */
public class SentStickersActivity extends AppCompatActivity {
    private TextView tv_myUserName;
    private String userName;
    private Spinner userListSpinner;
    private Button btn_sentHistory;

    DatabaseReference myDB = FirebaseDatabase.getInstance().getReference();
    ArrayList<String> spinnerList = new ArrayList<>(); //holds all users available to send stikcer to
    ArrayAdapter<String> adapter;

    /**
     * The onCreate method called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it
     *                           most recently supplied in {@link #onSaveInstanceState}. <b><i>Note:
     *                           Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createNotificationChannel();

        setContentView(R.layout.activity_sent_stickers);
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            userName = extras.getString("userName");
        }

        //set up UI elements
        tv_myUserName = findViewById(R.id.myUsername);
        tv_myUserName.setText("My username: " + userName);
        userListSpinner = findViewById(R.id.spinnerUsers);
        btn_sentHistory = findViewById(R.id.btn_sentHistory);

        //get user list for the spinner View
        spinnerShowData();

        //When click sent history, go to sentHistory Activity
        btn_sentHistory.setOnClickListener(view -> {
            Intent intent = new Intent(SentStickersActivity.this, StickersCollectedHistory.class);
            startActivity(intent);
        });
    }

    /**
     * Get a list of users from DB and show in the spiner view
     */
    private void spinnerShowData() {
        myDB.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                spinnerList.clear();
                for (DataSnapshot item: snapshot.getChildren()) {
                    spinnerList.add(item.child("userName").getValue(String.class));
                    System.out.println(item.child("userName").getValue(String.class));
                }

                adapter = new ArrayAdapter<String>(SentStickersActivity.this, android.R.layout.simple_spinner_dropdown_item, spinnerList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                userListSpinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * Create notification channel.
     */
    public void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            String id = getString(R.string.channel_id);
            int importance = NotificationManager.IMPORTANCE_HIGH;

            // create new channel
            NotificationChannel channel = new NotificationChannel(id, name, importance);

            // Set description.
            channel.setDescription(description);
            channel.enableLights(true);

            // Set color.
            channel.setLightColor(Color.argb(255, 228, 14, 18));

            // Register the channel with the system. You can't change the importance
            // or other notification behaviors after this.
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
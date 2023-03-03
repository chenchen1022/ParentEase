package edu.northeastern.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

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
    DatabaseReference spinnerRef;
    ArrayList<String> spinnerList;
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

        //TODO: drop downlist should connect to the database
        userListSpinner = findViewById(R.id.spinnerUsers);
        spinnerRef = FirebaseDatabase.getInstance().getReference("User");

        spinnerList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(SentStickersActivity.this, android.R.layout.simple_spinner_dropdown_item);




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
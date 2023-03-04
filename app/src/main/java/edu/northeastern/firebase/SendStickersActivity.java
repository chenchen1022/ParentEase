package edu.northeastern.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.northeastern.atyourservice.R;
import edu.northeastern.firebase.utils.MiscellaneousUtil;

/**
 * The class for Send Stickers Activity.
 *
 * @author ShiChang Ye
 * @author Chen Chen
 * @author Lin Han
 */
public class SendStickersActivity extends AppCompatActivity {
    // Declares fields.
    private static String SERVER_KEY;
    private static final int INITIAL_COUNT = 0;

    private TextView userNameTv;
    private Spinner usersSpinner;
    private ImageView image1, image2, image3, image4, image5, image6, image7, image8;
    private TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8;
    private Button submitBtn;
    private Button sentHistoryBtn;

    private String userName;
    private String otherUserName;

    private List<ImageView> imageViewList;
    private List<TextView> textViewList;
    private Map<ImageView, TextView> imageToTextMap;
    private Map<ImageView, Integer> imageToSendCountMap;
    private Map<View, Boolean> clickedImageMap;

    private DatabaseReference myDB = FirebaseDatabase.getInstance().getReference();
    private ArrayList<String> spinnerList = new ArrayList<>(); //holds all users available to send stikcer to
    private ArrayAdapter<String> adapter;

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
        setContentView(R.layout.activity_send_stickers);

        createNotificationChannel();

        // Binds widgets from the layout to the fields.
        userNameTv = findViewById(R.id.userNameTv);
        usersSpinner = findViewById(R.id.usersSpinner);
        submitBtn = findViewById(R.id.submitBtn);
        sentHistoryBtn = findViewById(R.id.sentHistoryBtn);

        imageToTextMap = new HashMap<>();
        imageToSendCountMap = new HashMap<>();
        clickedImageMap = new HashMap<>();
        initializeImageViewsAndTextViews();

        // Gets the current user name from the intent.
        Bundle extras = getIntent().getExtras();
        userName = extras.getString("userName");
        userNameTv.setText("Name: " + userName);

        //get user list for the spinner View
        spinnerShowData();

        //When click sent history, go to sentHistory Activity
        sentHistoryBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, StickersCollectedHistory.class);
            startActivity(intent);
        });

        // Gets the server key
        SERVER_KEY = "key=" + MiscellaneousUtil.getProperties(this).getProperty("SERVER_KEY");
    }

    /**
     * Get a list of users from DB and show in the spiner view
     */
    private void spinnerShowData() {
        myDB.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                spinnerList.clear();
                for (DataSnapshot item: snapshot.getChildren()) {
                    spinnerList.add(item.child("userName").getValue(String.class));
                }

                adapter = new ArrayAdapter<String>(SendStickersActivity.this, android.R.layout.simple_spinner_dropdown_item, spinnerList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                usersSpinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initializeImageViewsAndTextViews() {
        // Binds images and views to the corresponding fields.
        image1 = findViewById(R.id.weather_icon_clear);
        image2 = findViewById(R.id.weather_icon_clouds);
        image3 = findViewById(R.id.weather_icon_rain);
        image4 = findViewById(R.id.weather_icon_drizzle);
        image5 = findViewById(R.id.weather_icon_rainbow);
        image6 = findViewById(R.id.weather_icon_smog);
        image7 = findViewById(R.id.weather_icon_snow);
        image8 = findViewById(R.id.weather_icon_bolt);

        textView1 = findViewById(R.id.weather_icon_clear_sent_times);
        textView2 = findViewById(R.id.weather_icon_clouds_sent_times);
        textView3 = findViewById(R.id.weather_icon_rain_sent_times);
        textView4 = findViewById(R.id.weather_icon_drizzle_sent_times);
        textView5 = findViewById(R.id.weather_icon_rainbow_sent_times);
        textView6 = findViewById(R.id.weather_icon_smog_sent_times);
        textView7 = findViewById(R.id.weather_icon_snow_sent_times);
        textView8 = findViewById(R.id.weather_icon_bolt_sent_times);

        // Initializes image and view maps.
        imageViewList = Arrays.asList(image1, image2, image3, image4, image5, image6, image7, image8);
        textViewList = Arrays.asList(textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8);
        for (int i = 0; i < imageViewList.size(); i++) {
            ImageView curImageView = imageViewList.get(i);
            TextView curTextView = textViewList.get(i);

            curImageView.setClickable(true);
            imageToTextMap.put(curImageView, curTextView);
            curImageView.setOnClickListener(view -> handleImageClick(view));
            imageToSendCountMap.put(curImageView, INITIAL_COUNT);

            curTextView.setText(curTextView.getText().toString() + imageToSendCountMap.get(curImageView));
        }
    }

    /**
     * Handles the image click event.
     *
     * @param view the image view that is clicked
     */
    private void handleImageClick(View view) {
        // If the clicked view is in the clicked status, restore its status.
        if (clickedImageMap.size() != 0 && clickedImageMap.get(view) != null) {
            ((ImageView) view).setColorFilter(null);
            clickedImageMap.remove(view);
            return;
        }

        // Reference: https://developer.android.com/reference/android/widget/ImageView#setColorFilter(int,%20android.graphics.PorterDuff.Mode)
        ((ImageView) view).setColorFilter(Color.GRAY, PorterDuff.Mode.OVERLAY);
        for (View imageView : clickedImageMap.keySet()) {
            ((ImageView) imageView).setColorFilter(null);
        }
        clickedImageMap.clear();
        clickedImageMap.put(view, true);
    }

    private void updateSendCount() {
        for (int i = 0; i < imageViewList.size(); i++) {
            ImageView curImageView = imageViewList.get(i);
            TextView curTextView = textViewList.get(i);
            curTextView.setText("Send count: " + imageToSendCountMap.get(curImageView));
        }
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
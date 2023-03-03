package edu.northeastern.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

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

    private TextView userNameTv;
    private Spinner usersSpinner;
    private ImageView image1, image2, image3, image4, image5, image6, image7, image8;
    private TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8;
    private Button submitBtn;
    private Button sentHistoryBtn;

    private String userName;
    private String otherUserName;

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

        submitBtn = findViewById(R.id.submitBtn);
        sentHistoryBtn = findViewById(R.id.sentHistoryBtn);

        // Gets the current user name from the intent.
        Bundle extras = getIntent().getExtras();
        userName = extras.getString("userName");
        userNameTv.setText("name: " + userName);

        // Gets the server key
        SERVER_KEY = "key=" + MiscellaneousUtil.getProperties(this).getProperty("SERVER_KEY");

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
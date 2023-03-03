package edu.northeastern.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.northeastern.atyourservice.R;
import edu.northeastern.firebase.utils.MiscellaneousUtil;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_stickers);

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

        // Gets the server key
        SERVER_KEY = "key=" + MiscellaneousUtil.getProperties(this).getProperty("SERVER_KEY");

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
}
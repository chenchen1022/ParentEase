package edu.northeastern;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import edu.northeastern.atyourservice.AtYourServiceActivity;
import edu.northeastern.atyourservice.R;
import edu.northeastern.firebase.AboutActivity;
import edu.northeastern.firebase.RegisterActivity;

/**
 * The main activity for the app.
 *
 * @author Chen Chen
 * @author Shichang Ye
 * @author Lin Han
 */
public class MainActivity extends AppCompatActivity {
    // Creates buttons for this activity.
    Button atYourServiceBtn;
    Button aboutBtn;
    Button stickItToEmBtn;

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
        setContentView(R.layout.activity_main);

        // Binds buttons from the layout to the fields of activity class.
        atYourServiceBtn = findViewById(R.id.atYourServiceBtn);
        aboutBtn = findViewById(R.id.aboutBtn);
        stickItToEmBtn = findViewById(R.id.databaseBtn);

        // Sets the click listener for the atYourServiceBtn button.
        atYourServiceBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AtYourServiceActivity.class);
            startActivity(intent);
        });

        // Sets the click listener for the About button
        aboutBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        });

        // Sets the click listener for the About button
        stickItToEmBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
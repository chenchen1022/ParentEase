package edu.northeastern.atyourservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

/**
 * The main activity for the app.
 *
 * @author Chen Chen
 */
public class MainActivity extends AppCompatActivity {
    // Creates buttons for this activity.
    Button atYourServiceBtn;

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

        // Combines buttons from the layout to the activity class.
        atYourServiceBtn = findViewById(R.id.atYourServiceBtn);

        // Sets the click listener for the atYourServiceBtn button.
        atYourServiceBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AtYourServiceActivity.class);
            startActivity(intent);
        });
    }
}
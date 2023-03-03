package edu.northeastern.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import edu.northeastern.atyourservice.R;

/**
 * The About Activity class that helps display group information.
 *
 * @author Lin Han
 */
public class AboutActivity extends AppCompatActivity {

    /**
     * The onCreate method called when the activity starts.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.
     *                           <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
}
package edu.northeastern.atyourservice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * The class for the "At Your Service" activity.
 */
public class AtYourServiceActivity extends AppCompatActivity {


    // There will be several buttons here.
    Button okBtn;
    EditText inputUrl;
    static URL url;

    JSONArray jsonArray;

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
        setContentView(R.layout.activity_at_your_service);

        okBtn = findViewById(R.id.okBtn);
        inputUrl = findViewById(R.id.inputUrl);
        inputUrl.setText("https://jsonplaceholder.typicode.com/posts/");

        // Sets the click listener for the okBtn button.
        okBtn.setOnClickListener(view -> {
            CallWebServiceTask callWebServiceTask = new CallWebServiceTask();

            try {
                url = new URL(NetworkUtil.validInput(inputUrl.getText().toString()));
                callWebServiceTask.start();
            } catch (NetworkUtil.InvalidUrlException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * The class for the thread created when clicking the okBtn button.
     */
    static class CallWebServiceTask extends Thread {
        @Override
        public void run() {
            JSONArray jsonArray;
            try {

                String response = NetworkUtil.httpResponse(url);

                System.out.println(response);

                jsonArray = new JSONArray(response);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

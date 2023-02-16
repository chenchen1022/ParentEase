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
    private static final String MY_API_KEY = "9d4997d1dc6eae36a7dffd8bad876602";
    private static final String WEATHER_BASE_API_URL = "http://api.openweathermap.org/data/2.5/forecast?";
    private static final String CITY_GEO_INFO_BASE_API_URL = "http://api.openweathermap.org/geo/1.0/direct";

    // The button the starts the api request.
    private Button okBtn;

    // The url for api requests.(we may not need this component since we do not want the user to change the website address)
    private EditText inputUrl;

    // The url used to make api requests.
    private static URL url;

    // The text component for inputting the city name.
    private EditText inputCity;

    // The name of the city.
    private static String city;

    // The array stores json objects representing the weather for the day.
    static JSONArray weatherDataArray;

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
        inputCity = findViewById(R.id.inputCity);

        // Sets the click listener for the okBtn button.
        okBtn.setOnClickListener(view -> {
            // The geological info of the city will be located and used in the CallWebServiceTask.
            city = inputCity.getText().toString();

            if (!isValidInput()) {
                Toast.makeText(getApplicationContext(), "Invalid input.", Toast.LENGTH_LONG).show();
                System.out.println("here2");
                return;
            }

            CallWebServiceTask callWebServiceTask = new CallWebServiceTask();
            callWebServiceTask.start();
        });
    }

    /**
     * Checks if the input data (city name, mode[today or future]) from the user is valid.
     *
     * @return
     */
    private boolean isValidInput() {
        if (city == null) {
            return false;
        }
        return true;
    }

    /**
     * The class for the thread created when clicking the okBtn button.
     */
    static class CallWebServiceTask extends Thread {
        @Override
        public void run() {
            try {
                // Makes an api request to get the geological info of the city.
                // doc: https://openweathermap.org/api/geocoding-api
                // http://api.openweathermap.org/geo/1.0/direct?q=London&limit=1&appid=9d4997d1dc6eae36a7dffd8bad876602
                String cityGeoInfoUrlStr = String.format("%s?q=%s&limit=%d&appid=%s", CITY_GEO_INFO_BASE_API_URL, city, 1, MY_API_KEY);
                url = new URL(cityGeoInfoUrlStr);
                String cityGeoInfoResponseStr = NetworkUtil.httpResponse(url);
                JSONArray cityGeoInfoResponseJsonArray = new JSONArray(cityGeoInfoResponseStr);
                JSONObject cityGeoInfo = (JSONObject) cityGeoInfoResponseJsonArray.get(0);
                double lat = Double.parseDouble(cityGeoInfo.getString("lat"));
                double lon = Double.parseDouble(cityGeoInfo.getString("lon"));

                // Makes an api request to get the weather data of the city. The returned data contains
                // 5 day / 3 hour forecast data.

                // doc: https://openweathermap.org/forecast5
                // api.openweathermap.org/data/2.5/forecast?lat=44.34&lon=10.99&appid={API key}
                String weatherDataUrlStr = String.format("%slat=%f&lon=%f&appid=%s", WEATHER_BASE_API_URL, lat, lon, MY_API_KEY);
                url = new URL(weatherDataUrlStr);
                String weatherDataResponse = NetworkUtil.httpResponse(url);

                // Creates a json object based on the stringed json data.
                JSONObject jsonObject = new JSONObject(weatherDataResponse);

                // Parses the json object and gets the corresponding json array.
                // *** This weatherDataArray has all the weather data. Check the console.
                weatherDataArray = jsonObject.getJSONArray("list");
                System.out.println(weatherDataArray);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
/**
 *
 */

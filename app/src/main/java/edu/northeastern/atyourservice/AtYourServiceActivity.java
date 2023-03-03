package edu.northeastern.atyourservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import edu.northeastern.firebase.utils.NetworkUtil;

/**
 * The class for the At Your Service activity.
 *
 * @author Manping Zhao
 * @author Chen Chen
 * @author Lin Han
 * @author Shichang Ye
 */
public class AtYourServiceActivity extends AppCompatActivity {
    // Static final variables for the class.
    private static final String MY_API_KEY = "9d4997d1dc6eae36a7dffd8bad876602";
    private static final String WEATHER_BASE_API_URL = "http://api.openweathermap.org/data/2.5/forecast?";
    private static final String CITY_GEO_INFO_BASE_API_URL = "http://api.openweathermap.org/geo/1.0/direct";
    private static final String NUMBER_OF_ITEMS = "NUMBER_OF_ITEMS";
    private static final String KEY_OF_INSTANCE = "KEY_OF_INSTANCE";
    private static final String CITY_NAME = "CITY_NAME";

    // Variables for the class.
    private Handler handler;
    private static URL url;
    private static String city;
    static JSONArray weatherDataArray;
    private static ArrayList<Weather> weatherList;

    // Components for the activity.
    private EditText inputCityTextView;
    private ImageButton searchBtn;
    private TextView mCityTextView;
    private TextView mdateTimeTextView;
    private ImageView mWeatherIconImageView;
    private TextView mTempTextView;
    private TextView mWeatherConditionTextView;
    private ProgressBar progressBar;
    private RecyclerView weatherForecastRecyclerView;

    // Components for setting recycler view
    private WeatherForecastAdapter weatherForecastAdapter;
    private RecyclerView.LayoutManager weatherForecastLayoutManager;

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
        handler = new Handler();

        // Initializes variables.
        weatherList = new ArrayList<>();

        // Binds widgets to fields.
        inputCityTextView = findViewById(R.id.inputCityTextView);
        searchBtn = findViewById(R.id.searchBtn);
        mCityTextView = findViewById(R.id.mCityTextView);
        mdateTimeTextView = findViewById(R.id.mdateTimeTextView);
        mWeatherIconImageView = findViewById(R.id.mWeatherIconImageView);
        mTempTextView = findViewById(R.id.mTempTextView);
        mWeatherConditionTextView = findViewById(R.id.mWeatherConditionTextView);
        progressBar = findViewById(R.id.progressBar);
        weatherForecastRecyclerView = findViewById(R.id.weatherForecastRecyclerView);

        progressBar.setVisibility(View.GONE);
        init(savedInstanceState);

        // Sets the click listener for the search button.
        searchBtn.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);

            // Sets the city name based on user input.
            city = inputCityTextView.getText().toString().strip();

            if (city.length() <= 0 || containsInvalidChar(city)) {
                Toast.makeText(AtYourServiceActivity.this, "Not a valid city name.", Toast.LENGTH_LONG).show();
                return;
            }

            // If the weather list is not empty, the weather objects need to be cleared.
            if (!weatherList.isEmpty()) {
                weatherList.clear();
            }

            // Calls the new thread to make requests.
            CallWebServiceTask callWebServiceTask = new CallWebServiceTask();
            callWebServiceTask.start();
        });
    }

    /**
     * The class for the thread created when clicking the search button in the AtYourServiceActivity.
     */
    class CallWebServiceTask extends Thread {
        @Override
        public void run() {
            try {
                // Makes an api request to get the geological info of the city. Api documentation
                // available at https://openweathermap.org/api/geocoding-api
                String cityGeoInfoUrlStr = String.format("%s?q=%s&limit=%d&appid=%s",
                        CITY_GEO_INFO_BASE_API_URL, city, 1, MY_API_KEY);
                url = new URL(cityGeoInfoUrlStr);
                String cityGeoInfoResponseStr = NetworkUtil.httpResponse(url);
                JSONArray cityGeoInfoResponseJsonArray = new JSONArray(cityGeoInfoResponseStr);

                // If no such input city can be found, a toast will be shown to the user.
                if (cityGeoInfoResponseJsonArray == null || cityGeoInfoResponseJsonArray.length() == 0) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AtYourServiceActivity.this, "Not a valid city name.", Toast.LENGTH_LONG).show();
                        }
                    });
                }

                JSONObject cityGeoInfo = (JSONObject) cityGeoInfoResponseJsonArray.get(0);
                double lat = Double.parseDouble(cityGeoInfo.getString("lat"));
                double lon = Double.parseDouble(cityGeoInfo.getString("lon"));

                // Makes an api request to get the weather data of the city. The returned data
                // contains 5 day / 3 hour weather forecast data. Api documentation available at:
                // https://openweathermap.org/forecast5
                String weatherDataUrlStr = String.format("%slat=%f&lon=%f&appid=%s", WEATHER_BASE_API_URL, lat, lon, MY_API_KEY);
                url = new URL(weatherDataUrlStr);
                String weatherDataResponse = NetworkUtil.httpResponse(url);

                // Creates a json object based on the stringified json data.
                JSONObject jsonObject = new JSONObject(weatherDataResponse);

                // Parses the json object and gets the corresponding json array.
                weatherDataArray = jsonObject.getJSONArray("list");

                // Gets information from the weatherDataArray, creates weather objects, and stores
                // the objects in the weatherList.
                for (int i = 0; i < weatherDataArray.length(); i++) {
                    JSONObject curObj = weatherDataArray.getJSONObject(i);

                    // Gets the date and time.
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
                    String parsedDateTimeStr = curObj.getString("dt_txt");
                    Date parsedDate = simpleDateFormat.parse(parsedDateTimeStr);
                    simpleDateFormat.setTimeZone(TimeZone.getDefault());
                    simpleDateFormat.applyPattern("yyyy-MMM-dd HH:mm:ss");

                    assert parsedDate != null;
                    String resultDateTime = simpleDateFormat.format(parsedDate).substring(5, 17).replace('-', ' ');

                    // Gets the temperature.
                    JSONObject tempObj = curObj.getJSONObject("main");
                    String tempStr = tempObj.getString("temp");
                    int tempInt = Double.valueOf(Double.valueOf(tempStr) - 273.15).intValue();
                    String temp = Integer.toString(tempInt) + " ºC";

                    // Gets the weather condition.
                    JSONObject weatherObject = (JSONObject) curObj.getJSONArray("weather").get(0);
                    String weatherCondition = weatherObject.getString("main");

                    // Creates and adds weather objects into the weather list.
                    Weather weather = new Weather(resultDateTime, temp, weatherCondition);
                    weatherList.add(weather);

                    // Sets the weather information for components except the recycler view.
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            setCurrentWeather();
                            createRecyclerView();
                        }
                    });
                }
            } catch (IOException | JSONException | ParseException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Saves data in Bundle instance when the screen orientation is changed.
     *
     * @param outState the bundle instance to be saved
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (weatherList == null || weatherList.size() == 0) {
            return;
        }

        int size = weatherList.size();
        outState.putInt(NUMBER_OF_ITEMS, size);
        outState.putString(CITY_NAME, city);

        for (int i = 0; i < size; i++) {
            outState.putString(KEY_OF_INSTANCE + i + "0", weatherList.get(i).getDateTime());
            outState.putString(KEY_OF_INSTANCE + i + "1", weatherList.get(i).getTemp());
            outState.putString(KEY_OF_INSTANCE + i + "2", weatherList.get(i).getWeatherCondition());
        }

        super.onSaveInstanceState(outState);
    }

    /**
     * Initialization of the saved state.
     */
    private void init(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            initialItemData(savedInstanceState);
            setCurrentWeather();
            createRecyclerView();
        }
    }

    /**
     * Initializes the data in the Bundle instance if it is not empty.
     *
     * @param savedInstanceState the Bundle instance
     */
    private void initialItemData(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(NUMBER_OF_ITEMS)) {
            if (weatherList == null || weatherList.size() == 0) {
                int size = savedInstanceState.getInt(NUMBER_OF_ITEMS);

                // Retrieves weather objects stored in the state.
                for (int i = 0; i < size; i++) {
                    String dateTime = savedInstanceState.getString(KEY_OF_INSTANCE + i + "0");
                    String temp = savedInstanceState.getString(KEY_OF_INSTANCE + i + "1");
                    String weatherCondition = savedInstanceState.getString(KEY_OF_INSTANCE + i + "2");
                    Weather weather = new Weather(dateTime, temp, weatherCondition);
                    weatherList.add(weather);
                }
            }
        }
    }

    /**
     * Creates the recycler view.
     */
    private void createRecyclerView() {
        weatherForecastRecyclerView = findViewById(R.id.weatherForecastRecyclerView);
        weatherForecastLayoutManager = new LinearLayoutManager(this);
        weatherForecastAdapter = new WeatherForecastAdapter(weatherList);

        weatherForecastRecyclerView.setHasFixedSize(true);
        weatherForecastRecyclerView.setLayoutManager(weatherForecastLayoutManager);
        weatherForecastRecyclerView.setAdapter(weatherForecastAdapter);
        weatherForecastRecyclerView.setItemAnimator(null);
    }

    /**
     * A helper function that returns if the given string contains any invalid character.
     *
     * @param str the string to be checked
     * @return true if the string contains an invalid character, or false otherwise
     */
    private boolean containsInvalidChar(String str) {
        for (int i = 0; i < str.length(); i++) {
            char cur = str.charAt(i);
            if (!Character.isLetter(cur)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Gets the weather icon based on the weather condition.
     *
     * @param weatherCondition the weather condition
     * @return the icon resource id
     */
    private int getWeatherIcon(String weatherCondition) {
        int resId = R.drawable.weather_icon_clear;

        switch (weatherCondition) {
            case "Clouds":
                resId = R.drawable.weather_icon_clouds;
                break;
            case "Smog":
                resId = R.drawable.weather_icon_smog;
                break;
            case "Drizzle":
                resId = R.drawable.weather_icon_drizzle;
                break;
            case "Rain":
                resId = R.drawable.weather_icon_rain;
                break;
            case "Snow":
                resId = R.drawable.weather_icon_snow;
                break;
            case "Thunderstorm":
                resId = R.drawable.weather_icon_thunderstorm;
                break;
            default:
        }

        return resId;
    }

    /**
     * A helper functions that sets the current weather for components above the recycler view.
     */
    private void setCurrentWeather() {
        if (weatherList == null || weatherList.size() == 0) {
            return;
        }
        Weather latestWeather = weatherList.get(0);
        mCityTextView.setText(city);

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        String currentTimeStr = simpleDateFormat.format(date);
        mdateTimeTextView.setText(currentTimeStr.substring(5, 17).replace('-', ' '));

        mTempTextView.setText(latestWeather.getTemp());
        mWeatherConditionTextView.setText(latestWeather.getWeatherCondition());

        int resId = getWeatherIcon(latestWeather.getWeatherCondition());
        mWeatherIconImageView.setImageResource(resId);
    }
}
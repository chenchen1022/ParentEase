package edu.northeastern.atyourservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * The class for the "At Your Service" activity.
 */
public class AtYourServiceActivity extends AppCompatActivity {
    private static final String MY_API_KEY = "9d4997d1dc6eae36a7dffd8bad876602";
    private static final String WEATHER_BASE_API_URL = "http://api.openweathermap.org/data/2.5/forecast?";
    private static final String CITY_GEO_INFO_BASE_API_URL = "http://api.openweathermap.org/geo/1.0/direct";

    // The button the starts the api request.
    private ImageButton searchBtn;

    // The url for api requests.(we may not need this component since we do not want the user to change the website address)
    // private EditText inputUrl;

    // The url used to make api requests.
    private static URL url;

    // The text component for inputting the city name.
    private EditText inputCity;

    // The name of the city.
    private static String city;

    // The array stores json objects representing the weather for the day.
    static JSONArray weatherDataArray;

    private static ArrayList<Weather> weatherList = new ArrayList<>();

    //result city name
    private TextView mCityTextView;

    //result Date and Time
    private TextView mDtTimeTextView;

    private static TextView id_date_time;

    private RecyclerView forecastRecyclerView;
    private ForecastAdapter forecastAdapter;
    private RecyclerView.LayoutManager forecastLayoutManager;
    private List<WeeklyItems> list;

    private static final String KEY_OF_DAYS = "KEY_OF_DAY";
    private static final String NUMBER_OF_DAYS = "NUMBER_OF_DAYS";

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

        list = new ArrayList<>();
        init(savedInstanceState);

        searchBtn = findViewById(R.id.searchBtn);
        inputCity = findViewById(R.id.inputCity);

        id_date_time = findViewById(R.id.id_date_time);

        // Sets the click listener for the searchBtn button.
        mCityTextView = findViewById(R.id.id_city);

        // Sets the click listener for the okBtn button.
        searchBtn.setOnClickListener(view -> {
            // The geological info of the city will be located and used in the CallWebServiceTask.
            city = inputCity.getText().toString();

            if (!isValidInput()) {
                Toast.makeText(getApplicationContext(), "Invalid input.", Toast.LENGTH_LONG).show();
                System.out.println("here2");
                return;
            }

            CallWebServiceTask callWebServiceTask = new CallWebServiceTask();
            callWebServiceTask.start();
            mCityTextView.setText(city);

            try {
                Thread.currentThread().sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println(weatherList.get(0).getDateTime());
        });
    }

    /**
     * Checks if the input data (city name, mode[today or future]) from the user is valid.
     *
     * @return a boolean
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
                System.out.println(cityGeoInfoResponseStr);
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
//                System.out.println(weatherDataArray);

                String temp;
                int tempInt;
                String description;
                String date;
                String time;
                int timeInt;
                String dateTime;

                // Get the temp, date, description
                // Store in weather class
                // add into arraylist
                for(int i = 0; i < weatherDataArray.length(); i++) {
                    JSONObject innerObj = weatherDataArray.getJSONObject(i);
                    // get temp
                    JSONObject tempObj = innerObj.getJSONObject("main");
                    temp = tempObj.getString("temp");
                    tempInt = Double.valueOf(Double.valueOf(temp) - 273.15).intValue();
                    temp = Integer.toString(tempInt) + "º";
                    // get description
                    JSONArray weatherArray = innerObj.getJSONArray("weather");
                    JSONObject weatherInnerObj = weatherArray.getJSONObject(0);
                    description = weatherInnerObj.getString("description");
                    // get date
                    date = innerObj.getString("dt_txt");
                    time = date.substring(11, 13);
                    date = date.substring(0, 10);
                    // get time + AM or PM
                    timeInt = Integer.parseInt(time);
                    if (timeInt < 12) {
                        time = Integer.toString(timeInt) + "AM";
                    } else {
                        time = Integer.toString(timeInt) + "PM";
                    }
                    // get date + time
                    dateTime = date + " " + time;

                    // get every 3 hour in 5 days weather data and add into weather list
                    Weather weather = new Weather(temp, description, dateTime);
                    weatherList.add(weather);
                    // Test for get info
                    System.out.println(temp);
                    System.out.println(description);
                    System.out.println(dateTime);
                    System.out.println("_________________________________");

                }

                // Test for weather list
                for (Weather w : weatherList) {
                    System.out.println("***************Weather List****************");
                    System.out.println(w.getTemp());
                    System.out.println(w.getDescription());
                    System.out.println(w.getDateTime());
                    System.out.println("************************");
                }

                id_date_time.setText(weatherList.get(0).getTemp());

            } catch (IOException | JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /* Manping Zhao
     */

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        int size = list == null ? 0 : list.size();
        outState.putInt(NUMBER_OF_DAYS, size);
        for (int i = 0; i < size; i++) {
            outState.putString(KEY_OF_DAYS + i + "0", list.get(i).getDay());
            outState.putString(KEY_OF_DAYS + i + "1", list.get(i).getTemp());
            outState.putString(KEY_OF_DAYS + i + "0", list.get(i).getDesc());
        }
        super.onSaveInstanceState(outState);
    }

    /* Manping Zhao
     */

    private void init(Bundle savedInstanceState) {
        initialItemData(savedInstanceState);
        createRecyclerView();
    }

    /*
    Manping Zhao
     */
    private void initialItemData(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(NUMBER_OF_DAYS)) {
            if (list == null || list.size() == 0) {

                int size = savedInstanceState.getInt(NUMBER_OF_DAYS);

                // Retrieve keys we stored in the instance
                for (int i = 0; i < size; i++) {
                    String dayName = savedInstanceState.getString(KEY_OF_DAYS + i + "0");
                    String dayTemp = savedInstanceState.getString(KEY_OF_DAYS + i + "1");
                    String dayDesc = savedInstanceState.getString(KEY_OF_DAYS + i + "2");
                    WeeklyItems weeklyItems = new WeeklyItems(dayName, dayTemp, dayDesc);
                    list.add(weeklyItems);
                }
            }
        }
    }

    /*
    Manping Zhao
     */
    private void createRecyclerView() {
        forecastRecyclerView = findViewById(R.id.recyclerView);
        forecastLayoutManager = new LinearLayoutManager(this);
        forecastAdapter = new ForecastAdapter(list);

        forecastRecyclerView.setHasFixedSize(true);
        forecastRecyclerView.setLayoutManager(forecastLayoutManager);

        forecastRecyclerView.setAdapter(forecastAdapter);
        forecastRecyclerView.setItemAnimator(null);
    }

}


package edu.northeastern.atyourservice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * The adaptor for the weather forecast recycler view in AtYourServiceActivity.
 *
 * @author Manping Zhao
 */
public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastHolder> {
    private List<Weather> weatherList;

    /**
     * Constructor for the class.
     *
     * @param weatherList the weather list
     */
    public WeatherForecastAdapter(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }

    /**
     * Called when the AtYourServiceActivity is started.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return
     */
    @Override
    public WeatherForecastHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_item, parent, false);
        return new WeatherForecastHolder(view);
    }

    /**
     * Binds data from the weather list to the view holders.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(WeatherForecastHolder holder, int position) {
        Weather currentItem = weatherList.get(position);
        holder.weatherItemDateTime.setText(currentItem.getDateTime());
        holder.weatherItemTemperature.setText(currentItem.getTemp());
        holder.weatherItemWeatherCondition.setText(currentItem.getWeatherCondition());

        String weatherCondition = currentItem.getWeatherCondition();
        int resId = getWeatherIcon(weatherCondition);
        holder.weatherItemImgView.setImageResource(resId);
    }

    /**
     * Gets the size of the weather list.
     *
     * @return the size of the weather list
     */
    @Override
    public int getItemCount() {
        return weatherList.size();
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
}

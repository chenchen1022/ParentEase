package edu.northeastern.atyourservice;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * The view holder class for the weather forecast recycler view.
 *
 * @author Manping Zhao
 */
public class WeatherForecastHolder extends RecyclerView.ViewHolder {
    // The components for the weather item.
    public TextView weatherItemDateTime;
    public TextView weatherItemTemperature;
    public TextView weatherItemWeatherCondition;
    public ImageView weatherItemImgView;

    /**
     * Constructor for the class.
     *
     * @param ItemView the item view
     */
    public WeatherForecastHolder(@NonNull View ItemView) {
        super(ItemView);
        weatherItemDateTime = ItemView.findViewById(R.id.weatherItemDateTime);
        weatherItemTemperature = ItemView.findViewById(R.id.weatherItemTemperature);
        weatherItemWeatherCondition = ItemView.findViewById(R.id.weatherItemWeatherCondition);
        weatherItemImgView = ItemView.findViewById(R.id.weatherItemImgView);
    }
}

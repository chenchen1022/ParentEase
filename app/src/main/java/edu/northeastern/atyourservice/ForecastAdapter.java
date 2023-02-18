package edu.northeastern.atyourservice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastHolder>{
    private String[] weatherData;
    private List<WeeklyItems> weatherList;

    public ForecastAdapter(List<WeeklyItems> weatherList) {
        this.weatherList = weatherList;
    }

    @Override
    public ForecastHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weekly_items, parent, false);
        return new ForecastHolder(view);
    }

    @Override
    public void onBindViewHolder(ForecastHolder holder, int position)  {
        WeeklyItems currentItem = weatherList.get(position);
        holder.day.setText(currentItem.getDay());
        holder.temp.setText(currentItem.getTemp());
        holder.desc.setText(currentItem.getDesc());

    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

}

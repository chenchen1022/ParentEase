package edu.northeastern.atyourservice;

import android.content.ClipData;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class ForecastHolder extends RecyclerView.ViewHolder {
    public TextView day;
    public TextView temp;
    public TextView desc;
    public ImageView img;

    public ForecastHolder(View ItemView) {
        super(ItemView);
        day = ItemView.findViewById(R.id.id_day);
        temp = ItemView.findViewById(R.id.id_temp);
        desc = ItemView.findViewById(R.id.id_desc);
        img = ItemView.findViewById(R.id.id_weatherIMG);
    }
}

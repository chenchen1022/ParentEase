package edu.northeastern.firebase;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.atyourservice.R;
import edu.northeastern.firebase.Stickers;
import edu.northeastern.firebase.StickersHolder;

/**
 * The adaptor for the stickers recycler view.
 *
 * @author Manping Zhao
 */
public class StickersAdapter extends RecyclerView.Adapter<StickersHolder> {
    private List<Stickers> stickersList;

    public StickersAdapter(List<Stickers> stickers) {
        this.stickersList = stickers;
    }

    @Override
    public StickersHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return new StickersHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item, parent,false));
    }

    @Override
    public void onBindViewHolder(StickersHolder holder,int position) {
        Stickers currentItem = stickersList.get(position);
        holder.fromUser.setText(currentItem.getFromUser());
        holder.sendTime.setText(currentItem.getSendTime());

        String stickersDesc = currentItem.getStickerDes();
        int resId = getStickerIcon(stickersDesc);
        holder.receivedStickers.setImageResource(resId);
    }

    @Override
    public int getItemCount() {
        return stickersList.size();
    }

    private int getStickerIcon(String stickerDesc) {
        int resId = R.drawable.weather_icon_clear;
        switch (stickerDesc) {
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
            case "Rainbow":
                resId = R.drawable.weather_icon_rainbow;
                break;
            case "Bolt":
                resId = R.drawable.weather_icon_bolt;
            default:
        }

        return resId;
        }
    }


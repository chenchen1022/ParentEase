package edu.northeastern.firebase;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import edu.northeastern.atyourservice.R;
import edu.northeastern.firebase.StickersHolder;
import edu.northeastern.firebase.entity.Sticker;

/**
 * The adaptor for the stickers recycler view.
 *
 * @author Manping Zhao
 */
public class StickersAdapter extends RecyclerView.Adapter<StickersHolder> {
    private List<Sticker> stickersList;

    /**
     * Constructor for the class.
     *
     * @param stickers the sticker list
     */
    public StickersAdapter(List<Sticker> stickers) {
        this.stickersList = stickers;
        stickersList.sort((a, b) -> b.getTimeStamp().compareTo(b.getTimeStamp()));
    }

    /**
     * Called when the submit button is pressed.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return a new stickerHolder
     */
    @Override
    public StickersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StickersHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item, parent, false));
    }

    /**
     * Binds data from the sticker list to the view holders.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(StickersHolder holder, int position) {
        Sticker currentItem = stickersList.get(position);
        holder.fromUser.setText(currentItem.getSender());
        holder.sendTime.setText(currentItem.getTimeStamp());

        String stickersDesc = currentItem.getStickerDes();
        int resId = getStickerIcon(stickersDesc);
        holder.receivedStickers.setImageResource(resId);
    }

    /**
     * Gets the size of the sticker list.
     *
     * @return the size of the sticker list
     */
    @Override
    public int getItemCount() {
        return stickersList.size();
    }

    /**
     * Gets the sticker icon based on the sticker description.
     *
     * @param stickerDesc the sticker description
     * @return the icon resource id
     */
    private int getStickerIcon(String stickerDesc) {
        int resId = R.drawable.weather_icon_clear;
        switch (stickerDesc) {
            case "WEATHER_ICON_CLOUDS":
                resId = R.drawable.weather_icon_clouds;
                break;
            case "WEATHER_ICON_SMOG":
                resId = R.drawable.weather_icon_smog;
                break;
            case "WEATHER_ICON_DRIZZLE":
                resId = R.drawable.weather_icon_drizzle;
                break;
            case "WEATHER_ICON_RAIN":
                resId = R.drawable.weather_icon_rain;
                break;
            case "WEATHER_ICON_SNOW":
                resId = R.drawable.weather_icon_snow;
                break;
            case "WEATHER_ICON_RAINBOW":
                resId = R.drawable.weather_icon_rainbow;
                break;
            case "WEATHER_ICON_BOLT":
                resId = R.drawable.weather_icon_bolt;
            default:
        }

        return resId;
    }
}


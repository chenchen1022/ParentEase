package edu.northeastern.firebase;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.atyourservice.R;

/**
 * The view holder class for the stickers recycler view.
 *
 * @author Manping Zhao
 */
public class StickersHolder extends RecyclerView.ViewHolder {
    public TextView fromUser;
    public TextView sendTime;
    public ImageView receivedStickers;

    /**
     * Constructor for the class.
     *
     * @param itemView the item view
     */
    public StickersHolder(@NonNull View itemView) {
        super(itemView);
        this.fromUser = itemView.findViewById(R.id.fromUserItem);
        this.sendTime = itemView.findViewById(R.id.sendTimesItem);
        this.receivedStickers = itemView.findViewById(R.id.receviedStickersRV);
    }
}

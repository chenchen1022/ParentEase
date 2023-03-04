package edu.northeastern.firebase;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;

import java.sql.DatabaseMetaData;
import java.util.List;

import edu.northeastern.atyourservice.R;

/**
 * The class for Stickers Received activity.
 *
 * @author ShiChang Ye
 * @author Chen Chen
 * @author Lin Han
 * @author Manping Zhao
 */
public class StickersCollectedHistory extends AppCompatActivity {

    private List<Sticker> stickersList;
    private String userName;
    private DatabaseReference myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stickers_received_history);
    }



}

package edu.northeastern.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import edu.northeastern.atyourservice.R;
import edu.northeastern.firebase.entity.Sticker;
import edu.northeastern.firebase.entity.User;

/**
 * The class for Stickers Received activity.
 *
 * @author Chen Chen
 * @author Lin Han
 * @author Manping Zhao
 */
public class StickersCollectedHistory extends AppCompatActivity {

    private List<Sticker> stickersList;
    RecyclerView recyclerView;
    StickersAdapter stickerAdapter;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stickers_received_history);
        currentUser = getIntent().getExtras().getParcelable("currentUser");

        recyclerView = findViewById(R.id.receivedStickersRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        stickersList = new ArrayList<Sticker>();

        if (currentUser.getStickersReceived() == null || currentUser.getStickersReceived().size() == 0) {
            Toast.makeText(this, "No history stickers to show.", Toast.LENGTH_LONG).show();
        } else {
            for (Sticker s : currentUser.getStickersReceived()) {
                stickersList.add(s);
            }
        }

        stickerAdapter = new StickersAdapter(stickersList);
        recyclerView.setAdapter(stickerAdapter);
    }
}
